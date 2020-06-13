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
	
	public ComposterFarmerTransactionModel()
	{
		
	}

	public ComposterFarmerTransactionModel(
			@NotNull(message = "SupplierId cannot be NULL") Long incrementId,
			@NotNull(message = "SupplierId cannot be NULL") Long composterCompostInitId,
			@NotNull(message = "SupplierId cannot be NULL") Long composterId,
			@NotNull(message = "SupplierId cannot be NULL") Long farmerId,
			@NotNull(message = "SupplierId cannot be NULL") String farmerName,
			@NotNull(message = "SupplierId cannot be NULL") String farmerContact,
			@NotNull(message = "SupplierId cannot be NULL") Timestamp dateAndTime) {
		super();
		this.incrementId = incrementId;
		this.composterCompostInitId = composterCompostInitId;
		this.composterId = composterId;
		this.farmerId = farmerId;
		this.farmerName = farmerName;
		this.farmerContact = farmerContact;
		this.dateAndTime = dateAndTime;
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
	
}
