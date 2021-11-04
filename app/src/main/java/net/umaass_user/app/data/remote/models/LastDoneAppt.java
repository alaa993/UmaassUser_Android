package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class LastDoneAppt{

	@SerializedName("id")
	private int id;

	@SerializedName("user_commenting_status")
	private String userCommentingStatus;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUserCommentingStatus(String userCommentingStatus){
		this.userCommentingStatus = userCommentingStatus;
	}

	public String getUserCommentingStatus(){
		return userCommentingStatus;
	}
}