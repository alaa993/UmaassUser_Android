package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class DefualtResponse{

	@SerializedName("message")
	private String message;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}