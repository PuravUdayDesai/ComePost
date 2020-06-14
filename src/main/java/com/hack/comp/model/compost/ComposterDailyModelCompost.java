package com.hack.comp.model.compost;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ComposterDailyModelCompost
{


    @NotNull(message = "id cannot be NULL")
    private Long id;
    @NotNull(message = "dateAndTime cannot be NULL")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
    private Timestamp dateAndTime;
    @NotNull(message = "price cannot be NULL")
    private Double price;
    @NotNull(message = "CompostWeight cannot be NULL")
    private Double compostWeight;
    @NotNull(message = "Category cannot be NULL")
    private String category;
    @NotNull(message = "Grade cannot be NULL")
    private String grade;
    private String description;
	@NotNull(message = "DateString cannot be NULL")
	private String dateString;

    ComposterDailyModelCompost()
    {

    }

	public ComposterDailyModelCompost(@NotNull(message = "id cannot be NULL") Long id,
			@NotNull(message = "dateAndTime cannot be NULL") Timestamp dateAndTime,
			@NotNull(message = "price cannot be NULL") Double price,
			@NotNull(message = "CompostWeight cannot be NULL") Double compostWeight,
			@NotNull(message = "Category cannot be NULL") String category,
			@NotNull(message = "Grade cannot be NULL") String grade, String description,
			@NotNull(message = "DateString cannot be NULL") String dateString) {
		super();
		this.id = id;
		this.dateAndTime = dateAndTime;
		this.price = price;
		this.compostWeight = compostWeight;
		this.category = category;
		this.grade = grade;
		this.description = description;
		this.dateString = dateString;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Timestamp getDateAndTime() {
		return dateAndTime;
	}


	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Double getCompostWeight() {
		return compostWeight;
	}


	public void setCompostWeight(Double compostWeight) {
		this.compostWeight = compostWeight;
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
