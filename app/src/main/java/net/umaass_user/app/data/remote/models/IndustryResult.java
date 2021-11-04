package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import net.umaass_user.app.data.remote.utils.EmptyStringAsNullTypeAdapter;

import java.util.List;

public class IndustryResult{

	@SerializedName("image")
	private String image;

	@SerializedName("is_favorited")
	private int isFavorited;

	@SerializedName("address")
	private String address;

	@SerializedName("lng")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private double lng;

	@SerializedName("distance")
	private String distance;

	@SerializedName("terms_and_condition")
	private String termsAndCondition;

	@SerializedName("description")
	private String description;

	@SerializedName("staff")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private List<StaffItem> staff;

	@SerializedName("services")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private List<ServicesItem> services;

	@SerializedName("title")
	private String title;

	@SerializedName("tac_label")
	private String tacLabel;

	@SerializedName("phone")
	private String phone;

	@SerializedName("working_hours")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private List<WorkingHoursItem> workingHours;

	@SerializedName("id")
	private int id;

	@SerializedName("category")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private Category category;

	@SerializedName("lat")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private double lat;

	@SerializedName("gallery")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private List<GalleryItem> gallery;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setIsFavorited(int isFavorited){
		this.isFavorited = isFavorited;
	}

	public int getIsFavorited(){
		return isFavorited;
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

	public void setDistance(String distance){
		this.distance = distance;
	}

	public String getDistance(){
		return distance;
	}

	public void setTermsAndCondition(String termsAndCondition){
		this.termsAndCondition = termsAndCondition;
	}

	public String getTermsAndCondition(){
		return termsAndCondition;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setStaff(List<StaffItem> staff){
		this.staff = staff;
	}

	public List<StaffItem> getStaff(){
		return staff;
	}

	public void setServices(List<ServicesItem> services){
		this.services = services;
	}

	public List<ServicesItem> getServices(){
		return services;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setTacLabel(String tacLabel){
		this.tacLabel = tacLabel;
	}

	public String getTacLabel(){
		return tacLabel;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setWorkingHours(List<WorkingHoursItem> workingHours){
		this.workingHours = workingHours;
	}

	public List<WorkingHoursItem> getWorkingHours(){
		return workingHours;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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

	public void setGallery(List<GalleryItem> gallery){
		this.gallery = gallery;
	}

	public List<GalleryItem> getGallery(){
		return gallery;
	}
}