package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class Default {

    @SerializedName("Describ")
    private String describ;
    @SerializedName("Result")
    private int    result;

    public boolean isOk() {
        return result == 1;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }

    public String getDescrib() {
        return describ;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    @Override
    public String toString() {
        return
                "Default{" +
                "describ = '" + describ + '\'' +
                ",result = '" + result + '\'' +
                "}";
    }
}
