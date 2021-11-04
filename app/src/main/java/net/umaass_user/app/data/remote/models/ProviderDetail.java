package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import net.umaass_user.app.data.remote.utils.EmptyStringAsNullTypeAdapter;

import java.util.List;

public class ProviderDetail {

    @SerializedName("desciption")
    private String desciption;

    @SerializedName("visits")
    private int visits;
    @SerializedName("rate")
    private float rate;

    @SerializedName("industry_id")
    private int industryId;

    @SerializedName("is_favorited")
    private int isFavorited;

    @SerializedName("category_name")
    private String categoryName;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("id")
    private int id;

    @SerializedName("first_exists_appt")
    private String firstExistsAppt;

    @SerializedName("name")
    private String name;


    @SerializedName("industry")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private Industry industry;

    @SerializedName("avatar")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private Avatar avatar;

    @SerializedName("services")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private List<ServicesItem> services;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public int getVisits() {
        return visits;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIsFavorited(int isFavorited) {
        this.isFavorited = isFavorited;
    }

    public int getIsFavorited() {
        return isFavorited;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFirstExistsAppt(String firstExistsAppt) {
        this.firstExistsAppt = firstExistsAppt;
    }

    public String getFirstExistsAppt() {
        return firstExistsAppt;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setServices(List<ServicesItem> services) {
        this.services = services;
    }

    public List<ServicesItem> getServices() {
        return services;
    }
}