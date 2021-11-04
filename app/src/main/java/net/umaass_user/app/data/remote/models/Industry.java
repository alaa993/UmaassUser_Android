package net.umaass_user.app.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import net.umaass_user.app.data.remote.utils.EmptyStringAsNullTypeAdapter;

import java.util.List;

public class Industry implements  Parcelable {

	@SerializedName("image")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private Image image;

	@SerializedName("address")
	private String address;

	@SerializedName("lng")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private double lng;

	@SerializedName("phone")
	private String phone;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("category")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private Category category;

	@SerializedName("gallery")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private List<GalleryItem> gallery;

	@SerializedName("lat")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private double lat;

	public List<GalleryItem> getGallery() {
		return gallery;
	}

	public void setGallery(List<GalleryItem> gallery) {
		this.gallery = gallery;
	}

	public void setImage(Image image){
		this.image = image;
	}

	public Image getImage(){
		return image;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setLng(double lng){
		this.lng = lng;
	}

	public double getLng(){
		return lng;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
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

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setCategory(Category category){
		this.category = category;
	}

	public Category getCategory(){
		return category;
	}

	public void setLat(double lat){
		this.lat = lat;
	}

	public double getLat(){
		return lat;
	}



	public Industry() {
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(this.image, flags);
		dest.writeString(this.address);
		dest.writeDouble(this.lng);
		dest.writeString(this.phone);
		dest.writeString(this.description);
		dest.writeInt(this.id);
		dest.writeString(this.title);
		dest.writeParcelable(this.category, flags);
		dest.writeTypedList(this.gallery);
		dest.writeDouble(this.lat);
	}

	protected Industry(Parcel in) {
		this.image = in.readParcelable(Image.class.getClassLoader());
		this.address = in.readString();
		this.lng = in.readDouble();
		this.phone = in.readString();
		this.description = in.readString();
		this.id = in.readInt();
		this.title = in.readString();
		this.category = in.readParcelable(Category.class.getClassLoader());
		this.gallery = in.createTypedArrayList(GalleryItem.CREATOR);
		this.lat = in.readDouble();
	}

	public static final Creator<Industry> CREATOR = new Creator<Industry>() {
		@Override
		public Industry createFromParcel(Parcel source) {
			return new Industry(source);
		}

		@Override
		public Industry[] newArray(int size) {
			return new Industry[size];
		}
	};
}