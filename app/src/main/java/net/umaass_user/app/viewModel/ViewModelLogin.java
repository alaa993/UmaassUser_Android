package net.umaass_user.app.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import net.umaass_user.app.data.Repository;
import net.umaass_user.app.viewModel.impl.LoginView;
import net.umaass_user.app.viewModel.persenter.LoginPresenter;


public class ViewModelLogin extends AndroidViewModel implements LoginPresenter {
    private Repository repository;
    private LoginView  view;


    public ViewModelLogin(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    @Override
    public void setLoginPresenter(LoginView presenter) {
        this.view = presenter;
    }

    @Override
    public void requestLogin(String userName, String pass) {
        if (view != null) {
            view.openLoading();
        }
    }
}
