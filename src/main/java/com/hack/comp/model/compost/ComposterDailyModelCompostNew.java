package com.hack.comp.model.compost;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ComposterDailyModelCompostNew
{

    @NotNull(message = "Id cannot be NULL")
    private Integer id;
    @NotNull(message = "Date cannot be NULL")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
    private Timestamp date;
    @NotNull(message = "Price cannot be NULL")
    private Double price;
    @NotNull(message = "CompostWeight cannot be NULL")
    private Double compostWeight;
    @NotNull(message = "AddOrSub cannot be NULL")
    private Boolean addOrSub;

    public ComposterDailyModelCompostNew()
    {

    }


    public ComposterDailyModelCompostNew(@NotNull(message = "Id cannot be NULL") Integer id, @NotNull(message = "Date cannot be NULL") Timestamp date, @NotNull(message = "Price cannot be NULL") Double price, @NotNull(message = "CompostWeight cannot be NULL") Double compostWeight, @NotNull(message = "AddOrSub cannot be NULL") Boolean addOrSub)
    {
        super();
        this.id = id;
        this.date = date;
        this.price = price;
        this.compostWeight = compostWeight;
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

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Double getCompostWeight()
    {
        return compostWeight;
    }

    public void setCompostWeight(Double compostWeight)
    {
        this.compostWeight = compostWeight;
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
