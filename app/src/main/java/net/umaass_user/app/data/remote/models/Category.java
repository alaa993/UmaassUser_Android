package net.umaass_user.app.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import net.umaass_user.app.data.remote.utils.EmptyStringAsNullTypeAdapter;
import net.umaass_user.app.interfac.ListItem;

public class Category implements ListItem ,  Parcelable {

    @SerializedName("name")
    private String name;


    @SerializedName("id")
    private String id;


    @SerializedName("image")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private Image image;

    private boolean selected = false;

    public Category() {
    }

    public Category(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Category(String name, String id, boolean selected) {
        this.name = name;
        this.id = id;
        this.selected = selected;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public String getItemId() {
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeParcelable(this.image, flags);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    protected Category(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.image = in.readParcelable(Image.class.getClassLoader());
        this.selected = in.readByte() != 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}