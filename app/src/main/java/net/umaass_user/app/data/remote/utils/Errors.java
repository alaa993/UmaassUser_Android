package net.umaass_user.app.data.remote.utils;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Errors {

    @SerializedName("client_phone")
    private List<String> clientPhone;

    @SerializedName("from_to")
    private List<String> fromTo;

    @SerializedName("birthdate")
    private List<String> birthdate;
    @SerializedName("gender")
    private List<String> gender;
    @SerializedName("email")
    private List<String> email;

    public void setClientPhone(List<String> clientPhone) {
        this.clientPhone = clientPhone;
    }

    public List<String> getClientPhone() {
        return clientPhone;
    }

    public void setFromTo(List<String> fromTo) {
        this.fromTo = fromTo;
    }

    public List<String> getFromTo() {
        return fromTo;
    }

    @NonNull
    @Override
    public String toString() {
        String cl = clientPhone != null && clientPhone.size() > 0 ? clientPhone.get(0) : "";
        String ft = fromTo != null && fromTo.size() > 0 ? fromTo.get(0) : "";
        String b = birthdate != null && birthdate.size() > 0 ? birthdate.get(0) : "";
        String g = gender != null && gender.size() > 0 ? gender.get(0) : "";
        String e = email != null && email.size() > 0 ? email.get(0) : "";
        return ft + " " + cl + " " + b + " " + g + " " + e;
    }
}