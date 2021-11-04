package net.umaass_user.app.ui.base;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.umaass_user.app.R;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.interfac.IBackPress;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.utils.permission.ActivityUtils;

import java.util.Locale;


public abstract class BaseFragment extends Fragment implements IBackPress {
    public int baseViewId = getViewLayout();
    public View baseView;
    public FragmentNavigation mfragmentNavigation;

    public abstract int getViewLayout();

    public void readView() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mfragmentNavigation = (FragmentNavigation) context;
        }
    }


    public void functionView() {
        if (getActivity() != null && baseView != null) {
            ImageView btnBack = baseView.findViewById(R.id.btnBack);
            if (btnBack != null) {
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (getActivity() != null) {
                            getActivity().onBackPressed();
                        }
                    }
                });
            }
        }
    }

    public void initViewModel() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Locale locale = new Locale(Preference.getLanguage());
        Locale.setDefault(locale);
        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(locale);
        if (Preference.getLanguage().equals("ku")) {
            configuration.setLayoutDirection(new Locale("ar"));
        } else {
            configuration.setLayoutDirection(locale);
        }
        baseView = inflater.inflate(baseViewId, container, false);
        return baseView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        readView();
        functionView();
        initViewModel();
    }

    @Override
    public boolean backPress() {
        return true;
    }


    public interface FragmentNavigation {
        void pushFragment(Fragment fragment);
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
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

    public void onBackPress() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    public void onPop() {

    }

    public void onPush() {

    }

}
