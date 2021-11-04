package net.umaass_user.app.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import net.umaass_user.app.data.remote.utils.EmptyStringAsNullTypeAdapter;

public class Applicant implements  Parcelable {

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("avatar")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private Avatar avatar;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setAvatar(Avatar avatar){
		this.avatar = avatar;
	}

	public Avatar getAvatar(){
		return avatar;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
		dest.writeString(this.description);
		dest.writeInt(this.id);
		dest.writeParcelable(this.avatar, flags);
	}

	public Applicant() {
	}

	protected Applicant(Parcel in) {
		this.name = in.readString();
		this.description = in.readString();
		this.id = in.readInt();
		this.avatar = in.readParcelable(Avatar.class.getClassLoader());
	}

	public static final Parcelable.Creator<Applicant> CREATOR = new Parcelable.Creator<Applicant>() {
		@Override
		public Applicant createFromParcel(Parcel source) {
			return new Applicant(source);
		}

		@Override
		public Applicant[] newArray(int size) {
			return new Applicant[size];
		}
	};
}