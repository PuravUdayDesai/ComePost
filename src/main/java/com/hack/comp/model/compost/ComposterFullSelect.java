package com.hack.comp.model.compost;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ComposterFullSelect
{
    @NotNull(message = "Id cannot be NULL")
    private Long id;
    @NotNull(message = "InitId cannot be NULL")
    private Long initId;
    @NotNull(message = "name cannot be NULL")
    private String name;
    @NotNull(message = "contact cannot be NULL")
    private String contact;
    @NotNull(message = "email cannot be NULL")
    private String email;
    @NotNull(message = "reg_no cannot be NULL")
    private String reg_no;
    @NotNull(message = "compost_add_sub cannot be NULL")
    private Boolean compost_add_sub;
    @NotNull(message = "price cannot be NULL")
    private Double price;
    @NotNull(message = "weight cannot be NULL")
    private Double weight;
    @NotNull(message = "date_time cannot be NULL")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
    private Timestamp date_time;
    @NotNull(message = "latitude cannot be NULL")
    private String latitude;
    @NotNull(message = "longitude cannot be NULL")
    private String longitude;
    @NotNull(message = "street cannot be NULL")
    private String street;
    @NotNull(message = "area cannot be NULL")
    private String area;
    @NotNull(message = "city cannot be NULL")
    private String city;
    @NotNull(message = "state cannot be NULL")
    private String state;
	@NotNull(message = "DateString cannot be NULL")
	private String dateString;
    
    public ComposterFullSelect()
    {

    }

	public ComposterFullSelect(@NotNull(message = "Id cannot be NULL") Long id,
			@NotNull(message = "InitId cannot be NULL") Long initId,
			@NotNull(message = "name cannot be NULL") String name,
			@NotNull(message = "contact cannot be NULL") String contact,
			@NotNull(message = "email cannot be NULL") String email,
			@NotNull(message = "reg_no cannot be NULL") String reg_no,
			@NotNull(message = "compost_add_sub cannot be NULL") Boolean compost_add_sub,
			@NotNull(message = "price cannot be NULL") Double price,
			@NotNull(message = "weight cannot be NULL") Double weight,
			@NotNull(message = "date_time cannot be NULL") Timestamp date_time,
			@NotNull(message = "latitude cannot be NULL") String latitude,
			@NotNull(message = "longitude cannot be NULL") String longitude,
			@NotNull(message = "street cannot be NULL") String street,
			@NotNull(message = "area cannot be NULL") String area,
			@NotNull(message = "city cannot be NULL") String city,
			@NotNull(message = "state cannot be NULL") String state,
			@NotNull(message = "DateString cannot be NULL") String dateString) {
		super();
		this.id = id;
		this.initId = initId;
		this.name = name;
		this.contact = contact;
		this.email = email;
		this.reg_no = reg_no;
		this.compost_add_sub = compost_add_sub;
		this.price = price;
		this.weight = weight;
		this.date_time = date_time;
		this.latitude = latitude;
		this.longitude = longitude;
		this.street = street;
		this.area = area;
		this.city = city;
		this.state = state;
		this.dateString = dateString;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInitId() {
		return initId;
	}

	public void setInitId(Long initId) {
		this.initId = initId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReg_no() {
		return reg_no;
	}

	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}

	public Boolean getCompost_add_sub() {
		return compost_add_sub;
	}

	public void setCompost_add_sub(Boolean compost_add_sub) {
		this.compost_add_sub = compost_add_sub;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Timestamp getDate_time() {
		return date_time;
	}

	public void setDate_time(Timestamp date_time) {
		this.date_time = date_time;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
