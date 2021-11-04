package net.umaass_user.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public  class UpdateWorkingHours {
    @SerializedName("working_hours")
    private List<WorkingHour> working_hours = new ArrayList<>();

    public List<WorkingHour> getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(List<WorkingHour> working_hours) {
        this.working_hours = working_hours;
    }
}