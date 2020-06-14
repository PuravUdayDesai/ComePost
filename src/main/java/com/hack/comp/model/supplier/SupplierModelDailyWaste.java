package com.hack.comp.model.supplier;

import java.sql.Timestamp;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SupplierModelDailyWaste
{

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
    private String description;
	@NotNull(message = "DateString cannot be NULL")
	private String dateString;
	
    public SupplierModelDailyWaste()
    {

    }



	public SupplierModelDailyWaste(@NotNull(message = "Id cannot be NULL") Long id,
			@NotNull(message = "Date cannot be NULL") Timestamp date,
			@NotNull(message = "Dry Waste cannot be NULL") @Max(25) Double dryWaste,
			@NotNull(message = "Wet Waste cannot be NULL") @Max(25) Double wetWaste, String description,
			@NotNull(message = "DateString cannot be NULL") String dateString) {
		super();
		this.id = id;
		this.date = date;
		this.dryWaste = dryWaste;
		this.wetWaste = wetWaste;
		this.description = description;
		this.dateString = dateString;
	}



	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Timestamp getDate()
    {
        return date;
    }

    public void setDate(Timestamp date)
    {
        this.date = date;
    }

    public Double getDryWaste()
    {
        return dryWaste;
    }

    public void setDryWaste(Double dryWaste)
    {
        this.dryWaste = dryWaste;
    }

    public Double getWetWaste()
    {
        return wetWaste;
    }

    public void setWetWaste(Double wetWaste)
    {
        this.wetWaste = wetWaste;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
