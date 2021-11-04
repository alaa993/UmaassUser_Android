package net.umaass_user.app.data.remote.models;


import com.google.gson.annotations.SerializedName;

import net.umaass_user.app.interfac.ListItem;


public class ServicesItem implements ListItem {

    @SerializedName("duration")
    private int duration;

    @SerializedName("industry_id")
    private int industryId;

    @SerializedName("notes_for_the_customer")
    private String notesForTheCustomer;

    @SerializedName("price")
    private int price;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setNotesForTheCustomer(String notesForTheCustomer) {
        this.notesForTheCustomer = notesForTheCustomer;
    }

    public String getNotesForTheCustomer() {
        return notesForTheCustomer;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getItemName() {
        return title;
    }

    @Override
    public String getItemId() {
        return id + "";
    }
}