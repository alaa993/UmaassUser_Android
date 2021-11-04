package net.umaass_user.app.application;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import net.umaass_user.app.data.Repository;
import net.umaass_user.app.viewModel.ViewModelLogin;
import net.umaass_user.app.viewModel.ViewModelMain;
import net.umaass_user.app.viewModel.ViewModelSplash;


public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private final Repository repository;


    public static ViewModelFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(G.getInstance(), Injection.provideRepository(G.getInstance().getApplicationContext()));
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(Application application, Repository repository) {
        this.mApplication = application;
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViewModelLogin.class)) {
            return (T) new ViewModelLogin(mApplication, repository);
        } else if (modelClass.isAssignableFrom(ViewModelMain.class)) {
            return (T) new ViewModelMain(mApplication, repository);
        } else if (modelClass.isAssignableFrom(ViewModelSplash.class)) {
            return (T) new ViewModelSplash(mApplication, repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}