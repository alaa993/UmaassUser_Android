package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class NotificationsModel {

	@SerializedName("app")
	private String app;

	@SerializedName("read")
	private boolean read;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("message")
	private String message;

	public void setApp(String app){
		this.app = app;
	}

	public String getApp(){
		return app;
	}

	public void setRead(boolean read){
		this.read = read;
	}

	public boolean isRead(){
		return read;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}