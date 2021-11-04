package net.umaass_user.app.data.interfaces;


import net.umaass_user.app.data.remote.utils.RequestException;

public interface CallBacks<T> {
    void onSuccess(T t);

    void onFail(RequestException e);
}