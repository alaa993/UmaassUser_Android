package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class ServiceResult {

	@SerializedName("duration")
	private String duration;

	@SerializedName("industry_id")
	private String industryId;

	@SerializedName("notes_for_the_customer")
	private String notesForTheCustomer;

	@SerializedName("price")
	private String price;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public void setDuration(String duration){
		this.duration = duration;
	}

	public String getDuration(){
		return duration;
	}

	public void setIndustryId(String industryId){
		this.industryId = industryId;
	}

	public String getIndustryId(){
		return industryId;
	}

	public void setNotesForTheCustomer(String notesForTheCustomer){
		this.notesForTheCustomer = notesForTheCustomer;
	}

	public String getNotesForTheCustomer(){
		return notesForTheCustomer;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
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
}