package com.hack.comp.model.compost;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ComposterDailyModelCompostSub
{


    @NotNull(message = "id cannot be NULL")
    private Long id;
    @NotNull(message = "dateAndTime cannot be NULL")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
    private Timestamp dateAndTime;
    @NotNull(message = "CompostWeight cannot be NULL")
    private Double compostWeight;
	@NotNull(message = "DateString cannot be NULL")
	private String dateString;
	
    ComposterDailyModelCompostSub()
    {

    }

    

    public ComposterDailyModelCompostSub(@NotNull(message = "id cannot be NULL") Long id,
			@NotNull(message = "dateAndTime cannot be NULL") Timestamp dateAndTime,
			@NotNull(message = "CompostWeight cannot be NULL") Double compostWeight,
			@NotNull(message = "DateString cannot be NULL") String dateString) {
		super();
		this.id = id;
		this.dateAndTime = dateAndTime;
		this.compostWeight = compostWeight;
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

    public Timestamp getDateAndTime()
    {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime)
    {
        this.dateAndTime = dateAndTime;
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
