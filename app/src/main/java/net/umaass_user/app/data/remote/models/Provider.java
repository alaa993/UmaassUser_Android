package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import net.umaass_user.app.data.remote.utils.EmptyStringAsNullTypeAdapter;

public class Provider{

	@SerializedName("industry_title")
	private String industryTitle;

	@SerializedName("visits")
	private int visits;

	@SerializedName("industry_id")
	private int industryId;

	@SerializedName("is_favorited")
	private int isFavorited;

	@SerializedName("category_name")
	private String categoryName;

	@SerializedName("distance")
	private String distance;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("name")
	private String name;

	@SerializedName("first_exists_appt")
	private String firstExistsAppt;

	@SerializedName("id")
	private int id;

	@SerializedName("avatar")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private Avatar avatar;

	public void setIndustryTitle(String industryTitle){
		this.industryTitle = industryTitle;
	}

	public String getIndustryTitle(){
		return industryTitle;
	}

	public void setVisits(int visits){
		this.visits = visits;
	}

	public int getVisits(){
		return visits;
	}

	public void setIndustryId(int industryId){
		this.industryId = industryId;
	}

	public int getIndustryId(){
		return industryId;
	}

	public void setIsFavorited(int isFavorited){
		this.isFavorited = isFavorited;
	}

	public int getIsFavorited(){
		return isFavorited;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setDistance(String distance){
		this.distance = distance;
	}

	public String getDistance(){
		return distance;
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

	public void setFirstExistsAppt(String firstExistsAppt){
		this.firstExistsAppt = firstExistsAppt;
	}

	public String getFirstExistsAppt(){
		return firstExistsAppt;
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
}