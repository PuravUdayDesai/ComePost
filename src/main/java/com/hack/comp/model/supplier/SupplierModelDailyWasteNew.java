package com.hack.comp.model.supplier;

import java.sql.Timestamp;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SupplierModelDailyWasteNew
{

	@NotNull(message = "InitId cannot be NULL")
	private Long initId;
    @NotNull(message = "Id cannot be NULL")
    private Long id;
    @NotNull(message = "Date cannot be NULL")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
    private Timestamp date;
    @NotNull(message = "Dry Waste cannot be NULL")
    @Max(value=25)
    private Double dryWaste;
    @NotNull(message = "Wet Waste cannot be NULL")
    @Max(value=25)
    private Double wetWaste;
    @NotNull(message = "Add/Substrract cannot be NULL")
    private Boolean addOrSub;
    private String description;
	@NotNull(message = "DateString cannot be NULL")
	private String dateString;
    
    public SupplierModelDailyWasteNew()
    {
    	
    }
	
	public SupplierModelDailyWasteNew(@NotNull(message = "InitId cannot be NULL") Long initId,
			@NotNull(message = "Id cannot be NULL") Long id, @NotNull(message = "Date cannot be NULL") Timestamp date,
			@NotNull(message = "Dry Waste cannot be NULL") @Max(25) Double dryWaste,
			@NotNull(message = "Wet Waste cannot be NULL") @Max(25) Double wetWaste,
			@NotNull(message = "Add/Substrract cannot be NULL") Boolean addOrSub, String description,
			@NotNull(message = "DateString cannot be NULL") String dateString) {
		super();
		this.initId = initId;
		this.id = id;
		this.date = date;
		this.dryWaste = dryWaste;
		this.wetWaste = wetWaste;
		this.addOrSub = addOrSub;
		this.description = description;
		this.dateString = dateString;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Long getInitId() {
		return initId;
	}
	public void setInitId(Long initId) {
		this.initId = initId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public Double getDryWaste() {
		return dryWaste;
	}
	public void setDryWaste(Double dryWaste) {
		this.dryWaste = dryWaste;
	}
	public Double getWetWaste() {
		return wetWaste;
	}
	public void setWetWaste(Double wetWaste) {
		this.wetWaste = wetWaste;
	}
	public Boolean getAddOrSub() {
		return addOrSub;
	}
	public void setAddOrSub(Boolean addOrSub) {
		this.addOrSub = addOrSub;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
