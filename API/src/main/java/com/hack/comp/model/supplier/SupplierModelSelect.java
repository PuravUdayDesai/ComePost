package com.hack.comp.model.supplier;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class SupplierModelSelect
{
    @NotNull(message = "Id cannot be NULL")
    private Long id;
    @NotNull(message = "Name cannot be NULL")
    private String name;
    @NotNull(message = "Contact Number cannot be NULL")
    private String contactNumber;
    @NotNull(message = "EmailId cannot be NULL")
    @Email(message = "Please Enter a Valid Email ID")
    private String emailId;
    @NotNull(message = "Registration Number cannot be NULL")
    private String registrationNumber;
    @NotNull(message = "No Validation")
    private Boolean valid;

    public SupplierModelSelect()
    {
    	
    }

	public SupplierModelSelect(@NotNull(message = "Id cannot be NULL") Long id,
			@NotNull(message = "Name cannot be NULL") String name,
			@NotNull(message = "Contact Number cannot be NULL") String contactNumber,
			@NotNull(message = "EmailId cannot be NULL") @Email(message = "Please Enter a Valid Email ID") String emailId,
			@NotNull(message = "Registration Number cannot be NULL") String registrationNumber,
			@NotNull(message = "No Validation") Boolean valid) {
		super();
		this.id = id;
		this.name = name;
		this.contactNumber = contactNumber;
		this.emailId = emailId;
		this.registrationNumber = registrationNumber;
		this.valid = valid;
	}


	public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    public Boolean getValid()
    {
        return valid;
    }

    public void setValid(Boolean valid)
    {
        this.valid = valid;
    }

}