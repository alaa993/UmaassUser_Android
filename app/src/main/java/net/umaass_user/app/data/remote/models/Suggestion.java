package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.SerializedName;

import net.umaass_user.app.interfac.ListItem;

public class Suggestion implements ListItem {

    @SerializedName("start")
    private String start;

    @SerializedName("end")
    private String end;

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart() {
        return start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEnd() {
        return end;
    }

    @Override
    public String getItemName() {
        return "Start " + start + " - End " + end;
    }

    @Override
    public String getItemId() {
        return end;
    }
}