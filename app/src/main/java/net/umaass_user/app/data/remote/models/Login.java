package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class Login{

	@SerializedName("token")
	private String token;

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}
}