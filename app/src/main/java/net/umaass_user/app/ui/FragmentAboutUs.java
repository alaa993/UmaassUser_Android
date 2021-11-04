package net.umaass_user.app.ui;

import android.graphics.Typeface;
import android.text.Html;
import android.widget.TextView;

import net.umaass_user.app.R;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.Repository;
import net.umaass_user.app.data.interfaces.CallBack;
import net.umaass_user.app.data.remote.models.Api;
import net.umaass_user.app.data.remote.utils.RequestException;
import net.umaass_user.app.ui.base.BaseFragment;

public class FragmentAboutUs extends BaseFragment {

    private TextView txtAboutUs;

    @Override
    public int getViewLayout() {
        return R.layout.fragment_about_us;
    }

    @Override
    public void readView() {
        super.readView();
        txtAboutUs = baseView.findViewById(R.id.txtAboutUs);

    }


    @Override
    public void functionView() {
        super.functionView();

        getData();
    }


    private void getData() {
        showLoading();
        Repository.getInstance().aboutUs(Preference.getLanguage(), new CallBack<Api<String>>() {
            @Override
            public void onSuccess(Api<String> stringApi) {
                super.onSuccess(stringApi);
                hideLoading();
                if (stringApi.getData() != null) {
                    txtAboutUs.setText(Html.fromHtml(stringApi.getData()));
                    txtAboutUs.setTypeface(txtAboutUs.getTypeface(), Typeface.BOLD);
                }

            }

            @Override
            public void onFail(RequestException e) {
                super.onFail(e);
                hideLoading();
            }
        });

    }

}
