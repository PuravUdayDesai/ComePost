package com.hack.comp.model.farmer;

import javax.validation.constraints.NotNull;

public class FarmerLoginModel 
{
	@NotNull(message = "Id cannot be NULL")
	private Long id;
	@NotNull(message = "farmerName cannot be NULL")
	private String farmerName;
	@NotNull(message = "farmerContact cannot be NULL")
	private String farmerContact;
	@NotNull(message = "surveyId cannot be NULL")
	private Integer surveyId;
	@NotNull(message = "street cannot be NULL")
	private String street;
	@NotNull(message = "area cannot be NULL")
	private String area;
	@NotNull(message = "city cannot be NULL")
	private String city;
	@NotNull(message = "state cannot be NULL")
	private String state;
	@NotNull(message = "Latitude cannot be NULL")
	private String latitude;
	@NotNull(message = "Longitude cannot be NULL")
	private String longitude;
    @NotNull(message = "Check cannot be NULL")
    private Boolean check;
    
    public FarmerLoginModel()
    {
    	
    }

	public FarmerLoginModel(@NotNull(message = "Id cannot be NULL") Long id,
			@NotNull(message = "farmerName cannot be NULL") String farmerName,
			@NotNull(message = "farmerContact cannot be NULL") String farmerContact,
			@NotNull(message = "surveyId cannot be NULL") Integer surveyId,
			@NotNull(message = "street cannot be NULL") String street,
			@NotNull(message = "area cannot be NULL") String area,
			@NotNull(message = "city cannot be NULL") String city,
			@NotNull(message = "state cannot be NULL") String state,
			@NotNull(message = "Latitude cannot be NULL") String latitude,
			@NotNull(message = "Longitude cannot be NULL") String longitude,
			@NotNull(message = "Check cannot be NULL") Boolean check) {
		super();
		this.id = id;
		this.farmerName = farmerName;
		this.farmerContact = farmerContact;
		this.surveyId = surveyId;
		this.street = street;
		this.area = area;
		this.city = city;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
		this.check = check;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public String getFarmerContact() {
		return farmerContact;
	}

	public void setFarmerContact(String farmerContact) {
		this.farmerContact = farmerContact;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
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

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

}
