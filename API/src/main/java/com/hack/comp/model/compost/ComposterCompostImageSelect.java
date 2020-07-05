package com.hack.comp.model.compost;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ComposterCompostImageSelect 
{
	@NotNull(message = "ComposterCompostImageId cannot be NULL")	
	private Long composterCompostImageId;
	@NotNull(message = "ComposterInit_id cannot be NULL")
	private Long composterInit_id;
	@NotNull(message = "ComposterId cannot be NULL")
	private Long composterId;
    @NotNull(message = "TimeOfEntry cannot be NULL")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
	private Timestamp timeOfEntry;
    @NotNull(message = "ImageURL cannot be NULL")
	private String imageURL;
	@NotNull(message = "DateString cannot be NULL")
	private String dateString;
    
    public ComposterCompostImageSelect()
    {
    	
    }

	public ComposterCompostImageSelect(
			@NotNull(message = "ComposterCompostImageId cannot be NULL") Long composterCompostImageId,
			@NotNull(message = "ComposterInit_id cannot be NULL") Long composterInit_id,
			@NotNull(message = "ComposterId cannot be NULL") Long composterId,
			@NotNull(message = "TimeOfEntry cannot be NULL") Timestamp timeOfEntry,
			@NotNull(message = "ImageURL cannot be NULL") String imageURL,
			@NotNull(message = "DateString cannot be NULL") String dateString) {
		super();
		this.composterCompostImageId = composterCompostImageId;
		this.composterInit_id = composterInit_id;
		this.composterId = composterId;
		this.timeOfEntry = timeOfEntry;
		this.imageURL = imageURL;
		this.dateString = dateString;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Long getComposterCompostImageId() {
		return composterCompostImageId;
	}

	public void setComposterCompostImageId(Long composterCompostImageId) {
		this.composterCompostImageId = composterCompostImageId;
	}

	public Long getComposterInit_id() {
		return composterInit_id;
	}

	public void setComposterInit_id(Long composterInit_id) {
		this.composterInit_id = composterInit_id;
	}

	public Long getComposterId() {
		return composterId;
	}

	public void setComposterId(Long composterId) {
		this.composterId = composterId;
	}

	public Timestamp getTimeOfEntry() {
		return timeOfEntry;
	}

	public void setTimeOfEntry(Timestamp timeOfEntry) {
		this.timeOfEntry = timeOfEntry;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
 
}
