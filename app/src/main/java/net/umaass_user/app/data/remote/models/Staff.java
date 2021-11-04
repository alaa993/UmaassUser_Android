package net.umaass_user.app.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import net.umaass_user.app.data.remote.utils.EmptyStringAsNullTypeAdapter;

public class Staff implements  Parcelable {

	@SerializedName("industry_id")
	private int industryId;

	@SerializedName("role")
	private String role;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("avatar")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private Avatar avatar;

	public void setIndustryId(int industryId){
		this.industryId = industryId;
	}

	public int getIndustryId(){
		return industryId;
	}

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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
		dest.writeInt(this.industryId);
		dest.writeString(this.role);
		dest.writeInt(this.userId);
		dest.writeString(this.name);
		dest.writeInt(this.id);
		dest.writeSerializable(this.avatar);
	}

	public Staff() {
	}

	protected Staff(Parcel in) {
		this.industryId = in.readInt();
		this.role = in.readString();
		this.userId = in.readInt();
		this.name = in.readString();
		this.id = in.readInt();
		this.avatar = (Avatar) in.readSerializable();
	}

	public static final Parcelable.Creator<Staff> CREATOR = new Parcelable.Creator<Staff>() {
		@Override
		public Staff createFromParcel(Parcel source) {
			return new Staff(source);
		}

		@Override
		public Staff[] newArray(int size) {
			return new Staff[size];
		}
	};
}