package com.hack.comp.model.composterFarmerTransaction;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ComposterFarmerTransactionModel 
{
	@NotNull(message = "SupplierId cannot be NULL")
	private Long incrementId;
	@NotNull(message = "SupplierId cannot be NULL")
	private Long composterCompostInitId;
	@NotNull(message = "SupplierId cannot be NULL")
	private Long composterId;
	@NotNull(message = "SupplierId cannot be NULL")
	private Long farmerId;
	@NotNull(message = "SupplierId cannot be NULL")
	private String farmerName;
	@NotNull(message = "SupplierId cannot be NULL")
	private String farmerContact;
	@NotNull(message = "SupplierId cannot be NULL")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
	private Timestamp dateAndTime;
	@NotNull(message = "DateString cannot be NULL")
	private String dateString;
	@NotNull(message = "Category cannot be NULL")
	private String category;
	@NotNull(message = "Grade cannot be NULL")
	private String grade;
	@NotNull(message = "Price cannot be NULL")
	private Double price;
	@NotNull(message = "CompostWeigth cannot be NULL")
	private Double compostWeigth;
	@NotNull(message = "TotalPrice cannot be NULL")
	private Double totalPrice;
	
	public ComposterFarmerTransactionModel()
	{
		
	}

	public ComposterFarmerTransactionModel(@NotNull(message = "SupplierId cannot be NULL") Long incrementId,
			@NotNull(message = "SupplierId cannot be NULL") Long composterCompostInitId,
			@NotNull(message = "SupplierId cannot be NULL") Long composterId,
			@NotNull(message = "SupplierId cannot be NULL") Long farmerId,
			@NotNull(message = "SupplierId cannot be NULL") String farmerName,
			@NotNull(message = "SupplierId cannot be NULL") String farmerContact,
			@NotNull(message = "SupplierId cannot be NULL") Timestamp dateAndTime,
			@NotNull(message = "DateString cannot be NULL") String dateString,
			@NotNull(message = "Category cannot be NULL") String category,
			@NotNull(message = "Grade cannot be NULL") String grade,
			@NotNull(message = "Price cannot be NULL") Double price,
			@NotNull(message = "CompostWeigth cannot be NULL") Double compostWeigth,
			@NotNull(message = "TotalPrice cannot be NULL") Double totalPrice) {
		super();
		this.incrementId = incrementId;
		this.composterCompostInitId = composterCompostInitId;
		this.composterId = composterId;
		this.farmerId = farmerId;
		this.farmerName = farmerName;
		this.farmerContact = farmerContact;
		this.dateAndTime = dateAndTime;
		this.dateString = dateString;
		this.category = category;
		this.grade = grade;
		this.price = price;
		this.compostWeigth = compostWeigth;
		this.totalPrice = totalPrice;
	}


	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}


	public Long getIncrementId() {
		return incrementId;
	}

	public void setIncrementId(Long incrementId) {
		this.incrementId = incrementId;
	}

	public Long getComposterCompostInitId() {
		return composterCompostInitId;
	}

	public void setComposterCompostInitId(Long composterCompostInitId) {
		this.composterCompostInitId = composterCompostInitId;
	}

	public Long getComposterId() {
		return composterId;
	}

	public void setComposterId(Long composterId) {
		this.composterId = composterId;
	}

	public Long getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(Long farmerId) {
		this.farmerId = farmerId;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public String getFarmerContact() {
		return farmerContact;
	}

	public void setFarmerContact(String farmerContact) {
		this.farmerContact = farmerContact;
	}

	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCompostWeigth() {
		return compostWeigth;
	}

	public void setCompostWeigth(Double compostWeigth) {
		this.compostWeigth = compostWeigth;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
