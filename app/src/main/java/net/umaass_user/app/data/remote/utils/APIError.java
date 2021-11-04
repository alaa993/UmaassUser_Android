package net.umaass_user.app.data.remote.utils;

import com.google.gson.annotations.SerializedName;

public class APIError {
    int statusCode;
    @SerializedName("message")
    private String message;

    @SerializedName("errors")
    private Errors errors;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return errors == null ? message : errors.toString();
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setStatusCode(int code) {
        statusCode = code;
    }
}