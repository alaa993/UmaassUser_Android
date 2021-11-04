package net.umaass_user.app.viewModel.persenter;


import net.umaass_user.app.viewModel.impl.LoginView;

public interface LoginPresenter  {
    void setLoginPresenter(LoginView presenter);

    void requestLogin(String userName, String pass);
//    void sendData(ModeleRequestContiner cmd);

   /* void requestVersionApp(RepositoryMethod.CallBacks<VersionApp> callback);
    void requesdownloadNewVersion(VersionApp app, DownloaderListener downloaderListener);*/

}
