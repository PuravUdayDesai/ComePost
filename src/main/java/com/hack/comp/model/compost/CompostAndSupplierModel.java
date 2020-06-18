package com.hack.comp.model.compost;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CompostAndSupplierModel
{

    @NotNull(message = "Supplier id cannot be NULL")
    private Long supplier_id;
    @NotNull(message = "Compost id cannot be NULL")
    private Long composter_id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "IST")
    @NotNull(message = "Date cannot be NULL")
    private Date date;
    @NotNull(message = "Dry waste cannot be NULL")
    private Double dry_waste;
    @NotNull(message = "Wet waste cannot be NULL")
    private Double wet_waste;
	@NotNull(message = "DateString cannot be NULL")
	private String dateString;

    public CompostAndSupplierModel()
    {
    }

   

    public CompostAndSupplierModel(@NotNull(message = "Supplier id cannot be NULL") Long supplier_id,
			@NotNull(message = "Compost id cannot be NULL") Long composter_id,
			@NotNull(message = "Date cannot be NULL") Date date,
			@NotNull(message = "Dry waste cannot be NULL") Double dry_waste,
			@NotNull(message = "Wet waste cannot be NULL") Double wet_waste,
			@NotNull(message = "DateString cannot be NULL") String dateString) {
		super();
		this.supplier_id = supplier_id;
		this.composter_id = composter_id;
		this.date = date;
		this.dry_waste = dry_waste;
		this.wet_waste = wet_waste;
		this.dateString = dateString;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Long getSupplier_id()
    {
        return supplier_id;
    }

    public void setSupplier_id(Long supplier_id)
    {
        this.supplier_id = supplier_id;
    }

    public Long getComposter_id()
    {
        return composter_id;
    }

    public void setComposter_id(Long composter_id)
    {
        this.composter_id = composter_id;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public double getDry_waste()
    {
        return dry_waste;
    }

    public void setDry_waste(Double dry_waste)
    {
        this.dry_waste = dry_waste;
    }

    public Double getWet_waste()
    {
        return wet_waste;
    }

    public void setWet_waste(Double wet_waste)
    {
        this.wet_waste = wet_waste;
    }

}
