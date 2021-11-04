package net.umaass_user.app.data.remote.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class GalleryItem implements  Parcelable {

	@SerializedName("url_md")
	private String urlMd;

	@SerializedName("url_sm")
	private String urlSm;

	@SerializedName("id")
	private int id;

	@SerializedName("url_lg")
	private String urlLg;

	@SerializedName("url_xs")
	private String urlXs;

	public void setUrlMd(String urlMd){
		this.urlMd = urlMd;
	}

	public String getUrlMd(){
		return urlMd;
	}

	public void setUrlSm(String urlSm){
		this.urlSm = urlSm;
	}

	public String getUrlSm(){
		return urlSm;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUrlLg(String urlLg){
		this.urlLg = urlLg;
	}

	public String getUrlLg(){
		return urlLg;
	}

	public void setUrlXs(String urlXs){
		this.urlXs = urlXs;
	}

	public String getUrlXs(){
		return urlXs;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.urlMd);
		dest.writeString(this.urlSm);
		dest.writeInt(this.id);
		dest.writeString(this.urlLg);
		dest.writeString(this.urlXs);
	}

	public GalleryItem() {
	}

	protected GalleryItem(Parcel in) {
		this.urlMd = in.readString();
		this.urlSm = in.readString();
		this.id = in.readInt();
		this.urlLg = in.readString();
		this.urlXs = in.readString();
	}

	public static final Parcelable.Creator<GalleryItem> CREATOR = new Parcelable.Creator<GalleryItem>() {
		@Override
		public GalleryItem createFromParcel(Parcel source) {
			return new GalleryItem(source);
		}

		@Override
		public GalleryItem[] newArray(int size) {
			return new GalleryItem[size];
		}
	};
}