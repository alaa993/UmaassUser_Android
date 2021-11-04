
package net.umaass_user.app.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;

public class PointMark implements ClusterItem, Serializable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Noekarbari")
    @Expose
    private String noekarbari;
    @SerializedName("Icon")
    @Expose
    private String icon;
    @SerializedName("Lat")
    @Expose
    private Double lat;
    @SerializedName("Long")
    @Expose
    private Double _long;
    @SerializedName("MelkCode")
    @Expose
    private String melkCode;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("TypeNoekarbari")
    @Expose
    private Integer typeNoekarbari;
   // @SerializedName("Title")
  //  @Expose
    private String title;

    @SerializedName("Gheymat")
    @Expose
    private long gheymat;

    @SerializedName("GheymatRahn")
    @Expose
    private long gheymatRahn;

    @SerializedName("GheymatEjare")
    @Expose
    private long gheymatEjare;

    @SerializedName("Image")
    @Expose
    private String image;


    public PointMark() {
    }


  public String getimage() {
    return image;
  }

  public void setimage(String image) {
    this.image = image;
  }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoekarbari() {
        return noekarbari;
    }

    public void setNoekarbari(String noekarbari) {
        this.noekarbari = noekarbari;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLong() {
        return _long;
    }

    public void setLong(Double _long) {
        this._long = _long;
    }

    public String getMelkCode() {
        return melkCode;
    }

    public void setMelkCode(String melkCode) {
        this.melkCode = melkCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTypeNoekarbari() {
        return typeNoekarbari;
    }

    public void setTypeNoekarbari(Integer typeNoekarbari) {
        this.typeNoekarbari = typeNoekarbari;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(lat, _long);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
       /* String s = "";
        long gh = 0;
        if (gheymat != 0) {
            gh = gheymat;
            s = "قیمت فروش";
        } else if (gheymatEjare != 0) {
            gh = gheymatEjare;
            s = "قیمت اجاره";
        } else if (gheymatRahn != 0) {
            gh = gheymatRahn;
            s = "قیمت رهن";
        }*/
        return "";//gh == 0 ? FormatHelper.toPersianNumber(melkCode) : FormatHelper.toPersianNumber(s + " " + Utils.separatorPrice(gh) + " ت ");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double get_long() {
        return _long;
    }

    public void set_long(Double _long) {
        this._long = _long;
    }

    public long getGheymat() {
        return gheymat;
    }

    public void setGheymat(long gheymat) {
        this.gheymat = gheymat;
    }

    public long getGheymatRahn() {
        return gheymatRahn;
    }

    public void setGheymatRahn(long gheymatRahn) {
        this.gheymatRahn = gheymatRahn;
    }

    public long getGheymatEjare() {
        return gheymatEjare;
    }

    public void setGheymatEjare(long gheymatEjare) {
        this.gheymatEjare = gheymatEjare;
    }
}
