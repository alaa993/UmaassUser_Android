package net.umaass_user.app.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.models.Login;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.ui.base.BaseActivity;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.permission.ActivityUtils;


public class ActivityLogin extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        readView();
        functionView();
        initViewModel();
    }

    @Override
    public void functionView() {
        ///  String no = "917905560" + new Random().nextInt(9);
        //checkLogin("98", no);
        phoneLogin();
    }


    public static int APP_REQUEST_CODE = 99;

    public void phoneLogin() {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == APP_REQUEST_CODE) {
                AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
                if (loginResult.getError() != null) {
                    G.toast(Utils.getString(R.string.something_wrong));
                } else if (loginResult.wasCancelled()) {
                    G.toast(Utils.getString(R.string.login_cancelled));

                } else {
                    if (loginResult.getAccessToken() != null) {
                        String token = loginResult.getAccessToken().getToken();
                        G.log("AccountKitError", "token " + token);
                     //   checkLogin(token);
                    }
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            PhoneNumber phoneNumber = account.getPhoneNumber();
                            G.log("AccountKitError", "phoneNumber " + phoneNumber);
                            if (phoneNumber != null) {
                                String phoneNumberString = phoneNumber.getPhoneNumber();
                                String country_code = phoneNumber.getCountryCode();
                                // checkLogin(country_code, phoneNumberString);
                            }
                        }

                        @Override
                        public void onError(final AccountKitError error) {
                            G.toast(Utils.getString(R.string.something_wrong));
                            G.log("AccountKitError", "error " + error.getUserFacingMessage());
                        }
                    });
                }
            }
        } else {
            finish();
        }
    }

//    private void checkLogin(final String access_token) {
//        Repository.getInstance().getLogin(access_token, new CallBack<Api<Login>>() {
//            @Override
//            public void onSuccess(Api<Login> s) {
//                super.onSuccess(s);
//                Preference.setToken(s.getData().getToken());
//                checkNeedInit();
//                intentToMain();
//            }
//
//            @Override
//            public void onFail(RequestException e) {
//                super.onFail(e);
//                if (e.getCode() == 412) {
//                    intentToRegister(access_token);
//                } else {
//                    G.toast(Utils.getString(R.string.try_again));
//                    finish();
//                }
//
//            }
//        });
//    }

    private void checkNeedInit() {
        /*Repository.getInstance().getAllIndustries(true, new CallBack<Api<List<Industry>>>() {
            @Override
            public void onSuccess(Api<List<Industry>> listApi) {
                super.onSuccess(listApi);
                if (listApi == null || listApi.getData() == null || listApi.getData().size() == 0) {
                    intentToInit();
                } else {
                    Preference.setActiveIndustryId(listApi.getData().get(0).getId() + "");
                    intentToMain();
                }
            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                G.toast(Utils.getString(R.string.try_again));
                finish();
            }
        });*/
    }

    private void intentToMain() {
        ActivityUtils.finishAllActivitiesExceptNewest();
        Intent intent = new Intent(ActivityLogin.this, ActivityCategory.class);
        startActivity(intent);
        finish();
    }

    private void intentToInit() {
        ActivityUtils.finishAllActivitiesExceptNewest();
        Intent intent = new Intent(ActivityLogin.this, ActivityBook.class);
        startActivity(intent);
        finish();
    }

    private void intentToRegister(String access_token,String phoneNumber) {
        ActivityUtils.finishAllActivitiesExceptNewest();
        Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
        intent.putExtra("access_token", access_token);
        intent.putExtra("phone", phoneNumber);
        startActivity(intent);
        finish();
    }

}
