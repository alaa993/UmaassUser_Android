package net.umaass_user.app.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import net.umaass_user.app.data.remote.utils.EmptyStringAsNullTypeAdapter;

public class AppointmentDetail implements  Parcelable {

    @SerializedName("client_phone")
    private String clientPhone;

    @SerializedName("from_to")
    private String fromTo;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("description")
    private String description;

    @SerializedName("staff")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private Staff staff;

    @SerializedName("industry")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private Industry industry;

    @SerializedName("book_id")
    private String bookId;

    @SerializedName("applicant")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private Applicant applicant;

    @SerializedName("client_age")
    private int clientAge;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("service")
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private Service service;

    @SerializedName("id")
    private int id;

    @SerializedName("client_name")
    private String clientName;

    @SerializedName("client_gender")
    private int clientGender;

    @SerializedName("status")
    private String status;

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setFromTo(String fromTo) {
        this.fromTo = fromTo;
    }

    public String getFromTo() {
        return fromTo;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setClientAge(int clientAge) {
        this.clientAge = clientAge;
    }

    public int getClientAge() {
        return clientAge;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientGender(int clientGender) {
        this.clientGender = clientGender;
    }

    public int getClientGender() {
        return clientGender;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    public AppointmentDetail() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.clientPhone);
        dest.writeString(this.fromTo);
        dest.writeString(this.endTime);
        dest.writeString(this.description);
        dest.writeParcelable(this.staff, flags);
        dest.writeParcelable(this.industry, flags);
        dest.writeString(this.bookId);
        dest.writeParcelable(this.applicant, flags);
        dest.writeInt(this.clientAge);
        dest.writeString(this.startTime);
        dest.writeParcelable(this.service, flags);
        dest.writeInt(this.id);
        dest.writeString(this.clientName);
        dest.writeInt(this.clientGender);
        dest.writeString(this.status);
    }

    protected AppointmentDetail(Parcel in) {
        this.clientPhone = in.readString();
        this.fromTo = in.readString();
        this.endTime = in.readString();
        this.description = in.readString();
        this.staff = in.readParcelable(Staff.class.getClassLoader());
        this.industry = in.readParcelable(Industry.class.getClassLoader());
        this.bookId = in.readString();
        this.applicant = in.readParcelable(Applicant.class.getClassLoader());
        this.clientAge = in.readInt();
        this.startTime = in.readString();
        this.service = in.readParcelable(Service.class.getClassLoader());
        this.id = in.readInt();
        this.clientName = in.readString();
        this.clientGender = in.readInt();
        this.status = in.readString();
    }

    public static final Creator<AppointmentDetail> CREATOR = new Creator<AppointmentDetail>() {
        @Override
        public AppointmentDetail createFromParcel(Parcel source) {
            return new AppointmentDetail(source);
        }

        @Override
        public AppointmentDetail[] newArray(int size) {
            return new AppointmentDetail[size];
        }
    };
}