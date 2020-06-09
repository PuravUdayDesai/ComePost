package com.hack.comp.model.supplier;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SupplierModelDailyWasteNew
{

    @NotNull(message = "Id cannot be NULL")
    private Integer id;
    @NotNull(message = "Date cannot be NULL")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
    private Timestamp date;
    @NotNull(message = "Dry Waste cannot be NULL")
    private Double dryWaste;
    @NotNull(message = "Wet Waste cannot be NULL")
    private Double wetWaste;
    @NotNull(message = "Add/Substrract cannot be NULL")
    private Boolean addOrSub;


    public SupplierModelDailyWasteNew(@NotNull(message = "Id cannot be NULL") Integer id, @NotNull(message = "Date cannot be NULL") Timestamp date, @NotNull(message = "Dry Waste cannot be NULL") Double dryWaste, @NotNull(message = "Wet Waste cannot be NULL") Double wetWaste, @NotNull(message = "Add/Substrract cannot be NULL") Boolean addOrSub)
    {
        super();
        this.id = id;
        this.date = date;
        this.dryWaste = dryWaste;
        this.wetWaste = wetWaste;
        this.addOrSub = addOrSub;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
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

    public Boolean getAddOrSub()
    {
        return addOrSub;
    }

    public void setAddOrSub(Boolean addOrSub)
    {
        this.addOrSub = addOrSub;
    }


}
