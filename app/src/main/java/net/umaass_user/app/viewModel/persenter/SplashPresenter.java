package net.umaass_user.app.viewModel.persenter;


import net.umaass_user.app.viewModel.impl.SplashView;

public interface SplashPresenter  {

    void getToken(String user, String pass, String deviceCode);

    void setSplashView(SplashView presenter);
}
