package com.hack.comp.model.compost;

import javax.validation.constraints.NotNull;

public class ComposterLoginModel 
{
	@NotNull(message = "Id cannot be NULL")
	Long id;
	@NotNull(message = "name cannot be NULL")
    String name;
    @NotNull(message = "contact cannot be NULL")
    String contact;
    @NotNull(message = "email cannot be NULL")
    String email;
    @NotNull(message = "reg_no cannot be NULL")
    String reg_no;
    @NotNull(message = "latitude cannot be NULL")
    String latitude;
    @NotNull(message = "longitude cannot be NULL")
    String longitude;
    @NotNull(message = "street cannot be NULL")
    String street;
    @NotNull(message = "area cannot be NULL")
    String area;
    @NotNull(message = "city cannot be NULL")
    String city;
    @NotNull(message = "state cannot be NULL")
    String state;
    @NotNull(message = "Check cannot be NULL")
    Boolean check;
    public ComposterLoginModel()
    {
    	
    }
	public ComposterLoginModel(@NotNull(message = "Id cannot be NULL") Long id,
			@NotNull(message = "name cannot be NULL") String name,
			@NotNull(message = "contact cannot be NULL") String contact,
			@NotNull(message = "email cannot be NULL") String email,
			@NotNull(message = "reg_no cannot be NULL") String reg_no,
			@NotNull(message = "latitude cannot be NULL") String latitude,
			@NotNull(message = "longitude cannot be NULL") String longitude,
			@NotNull(message = "street cannot be NULL") String street,
			@NotNull(message = "area cannot be NULL") String area,
			@NotNull(message = "city cannot be NULL") String city,
			@NotNull(message = "state cannot be NULL") String state,
			@NotNull(message = "Check cannot be NULL") Boolean check) {
		super();
		this.id = id;
		this.name = name;
		this.contact = contact;
		this.email = email;
		this.reg_no = reg_no;
		this.latitude = latitude;
		this.longitude = longitude;
		this.street = street;
		this.area = area;
		this.city = city;
		this.state = state;
		this.check = check;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Boolean getCheck() {
		return check;
	}
	public void setCheck(Boolean check) {
		this.check = check;
	}    
}
