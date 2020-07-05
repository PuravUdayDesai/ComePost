package com.hack.comp.model.farmer;

import javax.validation.constraints.NotNull;

public class FarmerInsert
{

    @NotNull(message = "farmerName cannot be NULL")
    private String farmerName;
    @NotNull(message = "farmerContact cannot be NULL")
    private String farmerContact;
    @NotNull(message = "surveyId cannot be NULL")
    private Integer surveyId;
    @NotNull(message = "password cannot be NULL")
    private String password;
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

    FarmerInsert()
    {

    }

    public FarmerInsert(@NotNull(message = "farmerName cannot be NULL") String farmerName, @NotNull(message = "farmerContact cannot be NULL") String farmerContact, @NotNull(message = "surveyId cannot be NULL") Integer surveyId, @NotNull(message = "password cannot be NULL") String password, @NotNull(message = "latitude cannot be NULL") String latitude, @NotNull(message = "longitude cannot be NULL") String longitude, @NotNull(message = "street cannot be NULL") String street, @NotNull(message = "area cannot be NULL") String area, @NotNull(message = "city cannot be NULL") String city, @NotNull(message = "state cannot be NULL") String state)
    {
        super();
        this.farmerName = farmerName;
        this.farmerContact = farmerContact;
        this.surveyId = surveyId;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.area = area;
        this.city = city;
        this.state = state;
    }

    public String getFarmerName()
    {
        return farmerName;
    }

    public void setFarmerName(String farmerName)
    {
        this.farmerName = farmerName;
    }

    public String getFarmerContact()
    {
        return farmerContact;
    }

    public void setFarmerContact(String farmerContact)
    {
        this.farmerContact = farmerContact;
    }

    public Integer getSurveyId()
    {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId)
    {
        this.surveyId = surveyId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getArea()
    {
        return area;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }


}
