package net.umaass_user.app.models;

import com.google.gson.annotations.SerializedName;

public class UpdateAppointmentTime {

    @SerializedName("status")
    private String status;
    @SerializedName("start")
    private String start;
    @SerializedName("end")
    private String end;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}