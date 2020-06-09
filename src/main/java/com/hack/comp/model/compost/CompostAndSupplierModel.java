package com.hack.comp.model.compost;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CompostAndSupplierModel
{

    @NotNull(message = "Supplier id cannot be NULL")
    private long supplier_id;
    @NotNull(message = "Compost id cannot be NULL")
    private long composter_id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date cannot be NULL")
    private Date date;
    @NotNull(message = "Dry waste cannot be NULL")
    private double dry_waste;
    @NotNull(message = "Wet waste cannot be NULL")
    private double wet_waste;

    public CompostAndSupplierModel()
    {
    }

    public CompostAndSupplierModel(long supplier_id, Date date, double dry_waste, double wet_waste)
    {
        this.supplier_id = supplier_id;
        this.date = date;
        this.dry_waste = dry_waste;
        this.wet_waste = wet_waste;
    }

    public long getSupplier_id()
    {
        return supplier_id;
    }

    public void setSupplier_id(long supplier_id)
    {
        this.supplier_id = supplier_id;
    }

    public long getComposter_id()
    {
        return composter_id;
    }

    public void setComposter_id(long composter_id)
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

    public void setDry_waste(double dry_waste)
    {
        this.dry_waste = dry_waste;
    }

    public double getWet_waste()
    {
        return wet_waste;
    }

    public void setWet_waste(double wet_waste)
    {
        this.wet_waste = wet_waste;
    }

}
