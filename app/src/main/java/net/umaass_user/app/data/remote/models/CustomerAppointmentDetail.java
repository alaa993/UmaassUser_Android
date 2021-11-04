package net.umaass_user.app.data.remote.models;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import net.umaass_user.app.data.remote.utils.EmptyStringAsNullTypeAdapter;

public class CustomerAppointmentDetail{

	@SerializedName("client_phone")
	private String clientPhone;

	@SerializedName("from_to")
	private String fromTo;

	@SerializedName("end_time")
	private String endTime;

	@SerializedName("description")
	private String description;

	@SerializedName("book_id")
	private String bookId;

	@SerializedName("applicant")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private Applicant applicant;

	@SerializedName("start_time")
	private String startTime;

	@SerializedName("applicant_id")
	private int applicantId;

	@SerializedName("service")
	@JsonAdapter(EmptyStringAsNullTypeAdapter.class)
	private Service service;

	@SerializedName("service_id")
	private int serviceId;

	@SerializedName("id")
	private int id;

	@SerializedName("client_name")
	private String clientName;

	@SerializedName("status")
	private String status;

	public void setClientPhone(String clientPhone){
		this.clientPhone = clientPhone;
	}

	public String getClientPhone(){
		return clientPhone;
	}

	public void setFromTo(String fromTo){
		this.fromTo = fromTo;
	}

	public String getFromTo(){
		return fromTo;
	}

	public void setEndTime(String endTime){
		this.endTime = endTime;
	}

	public String getEndTime(){
		return endTime;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setBookId(String bookId){
		this.bookId = bookId;
	}

	public String getBookId(){
		return bookId;
	}

	public void setApplicant(Applicant applicant){
		this.applicant = applicant;
	}

	public Applicant getApplicant(){
		return applicant;
	}

	public void setStartTime(String startTime){
		this.startTime = startTime;
	}

	public String getStartTime(){
		return startTime;
	}

	public void setApplicantId(int applicantId){
		this.applicantId = applicantId;
	}

	public int getApplicantId(){
		return applicantId;
	}

	public void setService(Service service){
		this.service = service;
	}

	public Service getService(){
		return service;
	}

	public void setServiceId(int serviceId){
		this.serviceId = serviceId;
	}

	public int getServiceId(){
		return serviceId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setClientName(String clientName){
		this.clientName = clientName;
	}

	public String getClientName(){
		return clientName;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}