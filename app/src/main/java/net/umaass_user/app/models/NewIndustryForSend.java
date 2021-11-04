package net.umaass_user.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewIndustryForSend {

    @SerializedName("category_id")
    public String category_id;
    @SerializedName("title")
    public String title;
    @SerializedName("address")
    public String address;
    @SerializedName("phone")
    public String phone;
    @SerializedName("admin_password")
    public String admin_password;
    @SerializedName("coworker_password")
    public String coworker_password;
    @SerializedName("assistant_password")
    public String assistant_password;
    @SerializedName("working_hours")
    public List<WorkingHour> working_hours = new ArrayList<>();
    @SerializedName("description")
    public String description;

}




