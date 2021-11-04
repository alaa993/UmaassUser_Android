package net.umaass_user.app.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.Login;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.ui.base.BaseActivity;
import net.umaass_user.app.ui.components.RoundCornerButton;
import net.umaass_user.app.ui.login.Authentication;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.permission.ActivityUtils;

import java.util.Arrays;


public class ActivityWelcome extends BaseActivity {
    RoundCornerButton btnRegister;
    RoundCornerButton btnLogin;

    private final int REQUESR_LOG = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        readView();
        functionView();
        initViewModel();
    }

    @Override
    public void readView() {
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void functionView() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(ActivityWelcome.this, Authentication.class));
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(
                                        Arrays.asList(
                                                new AuthUI.IdpConfig.PhoneBuilder().build()))
                                .setTheme(R.style.AppTheme).build(), REQUESR_LOG);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(ActivityWelcome.this, Authentication.class));
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(
                                        Arrays.asList(
                                                new AuthUI.IdpConfig.PhoneBuilder().build()))
                                .setTheme(R.style.AppTheme).build(), REQUESR_LOG);
            }
        });
    }

    private void checkLogin(final String access_token,String phoneNumber) {
        showLoading();
        Repository.getInstance().getLogin(access_token,phoneNumber, new CallBack<Api<Login>>() {
            @Override
            public void onSuccess(Api<Login> s) {
                super.onSuccess(s);
             //   hideLoading();
                Preference.setToken(s.getData().getToken());
                intentToMain();
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
                if (e.getCode() == 412) {
                    intentToRegister(access_token,phoneNumber);
                } else {
                    G.toast(Utils.getString(R.string.try_again));
                    finish();
                }

            }
        });
    }

    private void intentToMain() {
        ActivityUtils.finishAllActivitiesExceptNewest();
        Intent intent = new Intent(ActivityWelcome.this, ActivityCategory.class);
        startActivity(intent);
        finish();
    }

    private void intentToInit() {
        ActivityUtils.finishAllActivitiesExceptNewest();
        Intent intent = new Intent(ActivityWelcome.this, ActivityBook.class);
        startActivity(intent);
        finish();
    }

    private void intentToRegister(String access_token,String phoneNumber) {
        ActivityUtils.finishAllActivitiesExceptNewest();
        Intent intent = new Intent(ActivityWelcome.this, ActivityRegister.class);
        intent.putExtra("access_token", access_token);
        intent.putExtra("phone",phoneNumber);
        startActivity(intent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESR_LOG) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                if (!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()) {

                    checkLogin(FirebaseAuth.getInstance().getCurrentUser().getUid(),FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                    return;
                } else {
                    if (response == null) {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                        Toast.makeText(this, "NO internet", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                        Toast.makeText(this, "Unkonw erorrs", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    moveToRegisterActivity();
                }
            }
        }
    }

}
