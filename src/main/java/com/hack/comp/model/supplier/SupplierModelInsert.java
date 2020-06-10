package com.hack.comp.model.supplier;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class SupplierModelInsert
{

    @NotNull(message = "Name cannot be NULL")
    private String name;
    @NotNull(message = "Contact Number cannot be NULL")
    private String contactNumber;
    @NotNull(message = "EmailId cannot be NULL")
    @Email(message = "Please Enter a Valid Email ID")
    private String emailId;
    @NotNull(message = "Registration Number cannot be NULL")
    private String registrationNumber;
    @NotNull(message = "Latitude cannot be NULL")
    private String latitude;
    @NotNull(message = "Longitude cannot be NULL")
    private String longitude;
    @NotNull(message = "State cannot be NULL")
    private String state;
    @NotNull(message = "City cannot be NULL")
    private String city;
    @NotNull(message = "Area cannot be NULL")
    private String area;
    @NotNull(message = "Street cannot be NULL")
    private String street;
    @NotNull(message = "Password cannot be NULL")
    private String password;

    public SupplierModelInsert()
    {
    }
    
    public SupplierModelInsert(
    		@NotNull(message = "Name cannot be NULL") String name, 
    		@NotNull(message = "Contact Number cannot be NULL") String contactNumber, 
    		@NotNull(message = "EmailId cannot be NULL") @Email(message = "Please Enter a Valid Email ID") String emailId, 
    		@NotNull(message = "Registration Number cannot be NULL") String registrationNumber,
    		@NotNull(message = "Latitude cannot be NULL") String latitude,
    		@NotNull(message = "Longitude cannot be NULL") String longitude,
    		@NotNull(message = "State cannot be NULL") String state,
    		@NotNull(message = "City cannot be NULL") String city, 
    		@NotNull(message = "Area cannot be NULL") String area, 
    		@NotNull(message = "Street cannot be NULL") String street,
    		@NotNull(message = "Password cannot be NULL") String password)
    {
        super();
        this.name = name;
        this.contactNumber = contactNumber;
        this.emailId = emailId;
        this.registrationNumber = registrationNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state = state;
        this.city = city;
        this.area = area;
        this.street = street;
        this.password = password;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getContactNumber()
    {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }

    public String getEmailId()
    {
        return emailId;
    }

    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }

    public String getRegistrationNumber()
    {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber)
    {
        this.registrationNumber = registrationNumber;
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

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getArea()
    {
        return area;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }


}
