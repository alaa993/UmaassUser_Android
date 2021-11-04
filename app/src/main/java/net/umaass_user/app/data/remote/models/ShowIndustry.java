package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import net.umaass_user.app.data.remote.utils.EmptyStringAsNullTypeAdapter;

import java.util.List;

public class ShowIndustry {

    @SerializedName("image")
    private String image;

    @SerializedName("address")
    private String address;

    @SerializedName("phone")
    private String phone;

    @SerializedName("working_hours")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private List<WorkingHoursItem> workingHours;

    @SerializedName("description")
    private String description;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("staff")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private List<StaffItem> staff;

    @SerializedName("id")
    private int id;

    @SerializedName("services")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private List<ServicesItem> services;

    @SerializedName("title")
    private String title;

    @SerializedName("category")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private Category category;

    @SerializedName("gallery")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private List<GalleryItem> gallery;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setWorkingHours(List<WorkingHoursItem> workingHours) {
        this.workingHours = workingHours;
    }

    public List<WorkingHoursItem> getWorkingHours() {
        return workingHours;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setStaff(List<StaffItem> staff) {
        this.staff = staff;
    }

    public List<StaffItem> getStaff() {
        return staff;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setServices(List<ServicesItem> services) {
        this.services = services;
    }

    public List<ServicesItem> getServices() {
        return services;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setGallery(List<GalleryItem> gallery) {
        this.gallery = gallery;
    }

    public List<GalleryItem> getGallery() {
        return gallery;
    }
}