package net.umaass_user.app.data.remote.models;

public class SuggestModel {

    private String fullname;
    private String mobile;
    private String country;
    private String province;
    private String speciality;
    private String introduceCode;


    public SuggestModel(String fullname, String mobile, String country, String province, String speciality, String introduceCode) {
        this.fullname = fullname;
        this.mobile = mobile;
        this.country = country;
        this.province = province;
        this.speciality = speciality;
        this.introduceCode = introduceCode;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getIntroduceCode() {
        return introduceCode;
    }

    public void setIntroduceCode(String introduceCode) {
        this.introduceCode = introduceCode;
    }
}
