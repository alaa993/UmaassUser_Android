package net.umaass_user.app.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import net.umaass_user.app.interfac.ListItem;

import java.util.List;

public class Country implements Parcelable, ListItem {

    @Expose
    public int id;
    @Expose
    public String name;
    @Expose
    public List<Country> children = null;

    public Country() {
    }

    private Country(Parcel in) {
        id = in.readInt();
        name = in.readString();
        children = in.createTypedArrayList(Country.CREATOR);
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

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getChildren() {
        return children;
    }

    public void setChildren(List<Country> children) {
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