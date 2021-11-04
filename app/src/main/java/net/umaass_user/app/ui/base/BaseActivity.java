package net.umaass_user.app.ui.base;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import net.umaass_user.app.R;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.utils.LocaleUtils;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.permission.ActivityUtils;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        Context x = LocaleUtils.setLocale(newBase, Preference.getLanguage());
        super.attachBaseContext(ViewPumpContextWrapper.wrap(x));
    }

    //=====================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enhance);
        //  anim(getWindow().getDecorView().getRootView());

    }

    private void anim(View view) {
        YoYo.with(Techniques.FadeInRight)
            .duration(300)
            .delay(200)
            .playOn(view);
        /*for (int i = 0; i < view.getChildCount(); i++) {
            View v = view.getChildAt(i);
            if (v instanceof ViewGroup) {
                anim((ViewGroup) v);
            } else {
                YoYo.with(Techniques.Landing)
                    .duration(500)
                    .playOn(v);
            }

        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void readView() {

    }

    public void functionView() {
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    public void initViewModel() {

    }

    private ProgressDialog progressDialog;

    public void showLoading() {
        hideLoading();
        progressDialog = new ProgressDialog(ActivityUtils.getTopActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Utils.getString(R.string.pls_wait));
        progressDialog.show();
    }


    public void hideLoading() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }


}//END
