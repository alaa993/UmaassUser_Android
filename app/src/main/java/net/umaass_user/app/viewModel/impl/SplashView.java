package net.umaass_user.app.viewModel.impl;


public interface SplashView extends BaseView {
    void onNewVersion(String url);

    void onErrorNetwork(String message);

    void onNextStep();
}
