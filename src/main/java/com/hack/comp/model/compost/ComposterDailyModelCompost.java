package com.hack.comp.model.compost;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ComposterDailyModelCompost
{


    @NotNull(message = "id cannot be NULL")
    Integer id;
    @NotNull(message = "dateAndTime cannot be NULL")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
    Timestamp dateAndTime;
    @NotNull(message = "price cannot be NULL")
    Double price;
    @NotNull(message = "CompostWeight cannot be NULL")
    Double compostWeight;

    ComposterDailyModelCompost()
    {

    }

    public ComposterDailyModelCompost(@NotNull(message = "id cannot be NULL") Integer id, @NotNull(message = "dateAndTime cannot be NULL") Timestamp dateAndTime, @NotNull(message = "price cannot be NULL") Double price, @NotNull(message = "CompostWeight cannot be NULL") Double compostWeight)
    {
        super();
        this.id = id;
        this.dateAndTime = dateAndTime;
        this.price = price;
        this.compostWeight = compostWeight;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Timestamp getDateAndTime()
    {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime)
    {
        this.dateAndTime = dateAndTime;
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


}
