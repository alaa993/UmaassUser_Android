package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class WorkingHoursItem{

	@SerializedName("start")
	private String start;

	@SerializedName("end")
	private String end;

	@SerializedName("day")
	private int day;

	public void setStart(String start){
		this.start = start;
	}

	public String getStart(){
		return start;
	}

	public void setEnd(String end){
		this.end = end;
	}

	public String getEnd(){
		return end;
	}

	public void setDay(int day){
		this.day = day;
	}

	public int getDay(){
		return day;
	}
}