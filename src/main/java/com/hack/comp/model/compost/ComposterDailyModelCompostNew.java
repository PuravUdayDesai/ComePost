package com.hack.comp.model.compost;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ComposterDailyModelCompostNew
{

    @NotNull(message = "Id cannot be NULL")
    private Long id;
    @NotNull(message = "Date cannot be NULL")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
    private Timestamp date;
    @NotNull(message = "Price cannot be NULL")
    private Double price;
    @NotNull(message = "CompostWeight cannot be NULL")
    private Double compostWeight;
    @NotNull(message = "AddOrSub cannot be NULL")
    private Boolean addOrSub;
    @NotNull(message = "Category cannot be NULL")
    private String category;
    @NotNull(message = "Grade cannot be NULL")
    private String grade;
    @NotNull(message = "Description cannot be NULL")
    private String description;
    
    
    public ComposterDailyModelCompostNew()
    {

    }

    public ComposterDailyModelCompostNew(@NotNull(message = "Id cannot be NULL") Long id,
			@NotNull(message = "Date cannot be NULL") Timestamp date,
			@NotNull(message = "Price cannot be NULL") Double price,
			@NotNull(message = "CompostWeight cannot be NULL") Double compostWeight,
			@NotNull(message = "AddOrSub cannot be NULL") Boolean addOrSub,
			@NotNull(message = "Category cannot be NULL") String category,
			@NotNull(message = "Grade cannot be NULL") String grade,
			@NotNull(message = "Description cannot be NULL") String description) {
		super();
		this.id = id;
		this.date = date;
		this.price = price;
		this.compostWeight = compostWeight;
		this.addOrSub = addOrSub;
		this.category = category;
		this.grade = grade;
		this.description = description;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
