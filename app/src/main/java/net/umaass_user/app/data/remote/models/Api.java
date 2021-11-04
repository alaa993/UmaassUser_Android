package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class Api<T> {
    @SerializedName("data")
    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
