package net.umaass_user.app.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Service implements  Parcelable {

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.duration);
        dest.writeInt(this.industryId);
        dest.writeString(this.notesForTheCustomer);
        dest.writeInt(this.price);
        dest.writeInt(this.id);
        dest.writeString(this.title);
    }

    public Service() {
    }

    protected Service(Parcel in) {
        this.duration = in.readInt();
        this.industryId = in.readInt();
        this.notesForTheCustomer = in.readString();
        this.price = in.readInt();
        this.id = in.readInt();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Service> CREATOR = new Parcelable.Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel source) {
            return new Service(source);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };
}