package net.umaass_user.app.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import net.umaass_user.app.interfac.ListItem;

import java.util.List;

public class City implements Parcelable , ListItem {

    @Expose
    public int id;
    @Expose
    public String name;
    @Expose
    public List<City> children = null;

    public City() {
    }

    private City(Parcel in) {
        id = in.readInt();
        name = in.readString();
        children = in.createTypedArrayList(City.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(children);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getChildren() {
        return children;
    }

    public void setChildren(List<City> children) {
        this.children = children;
    }


    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public String getItemId() {
        return String.valueOf(id);
    }
}