package com.hack.comp.model.supplierComposterTransaction;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SupplierComposterTransactionSelectModel 
{
	@NotNull(message = "IncrementId cannot be NULL")
	private Long incrementId;
	@NotNull(message = "SupplierWasteId cannot be NULL")
	private Long supplierWasteId;
	@NotNull(message = "SupplierId cannot be NULL")
	private Long supplierId;
	@NotNull(message = "ComposterId cannot be NULL")
	private Long composterId;
	@NotNull(message = "ComposterName cannot be NULL")
	private String composterName;
	@NotNull(message = "ComposterEmailId cannot be NULL")
	private String composterEmailId;
	@NotNull(message = "ComposterContactNumber cannot be NULL")
	private String composterContactNumber;
	@NotNull(message = "DateAndTime cannot be NULL")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
	private Timestamp dateAndTime;
	@NotNull(message = "DateString cannot be NULL")
	private String dateString;
	@NotNull(message = "DryWaste cannot be NULL")
	private Double dryWaste;
	@NotNull(message = "WetWaste cannot be NULL")
	private Double wetWaste;
	
	public SupplierComposterTransactionSelectModel()
	{
		
	}

	public SupplierComposterTransactionSelectModel(@NotNull(message = "IncrementId cannot be NULL") Long incrementId,
			@NotNull(message = "SupplierWasteId cannot be NULL") Long supplierWasteId,
			@NotNull(message = "SupplierId cannot be NULL") Long supplierId,
			@NotNull(message = "ComposterId cannot be NULL") Long composterId,
			@NotNull(message = "ComposterName cannot be NULL") String composterName,
			@NotNull(message = "ComposterEmailId cannot be NULL") String composterEmailId,
			@NotNull(message = "ComposterContactNumber cannot be NULL") String composterContactNumber,
			@NotNull(message = "DateAndTime cannot be NULL") Timestamp dateAndTime,
			@NotNull(message = "DateString cannot be NULL") String dateString,
			@NotNull(message = "DryWaste cannot be NULL") Double dryWaste,
			@NotNull(message = "WetWaste cannot be NULL") Double wetWaste) {
		super();
		this.incrementId = incrementId;
		this.supplierWasteId = supplierWasteId;
		this.supplierId = supplierId;
		this.composterId = composterId;
		this.composterName = composterName;
		this.composterEmailId = composterEmailId;
		this.composterContactNumber = composterContactNumber;
		this.dateAndTime = dateAndTime;
		this.dateString = dateString;
		this.dryWaste = dryWaste;
		this.wetWaste = wetWaste;
	}


	public Long getIncrementId() {
		return incrementId;
	}

	public void setIncrementId(Long incrementId) {
		this.incrementId = incrementId;
	}

	public Long getSupplierWasteId() {
		return supplierWasteId;
	}

	public void setSupplierWasteId(Long supplierWasteId) {
		this.supplierWasteId = supplierWasteId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getComposterId() {
		return composterId;
	}

	public void setComposterId(Long composterId) {
		this.composterId = composterId;
	}

	public String getComposterName() {
		return composterName;
	}

	public void setComposterName(String composterName) {
		this.composterName = composterName;
	}

	public String getComposterEmailId() {
		return composterEmailId;
	}

	public void setComposterEmailId(String composterEmailId) {
		this.composterEmailId = composterEmailId;
	}

	public String getComposterContactNumber() {
		return composterContactNumber;
	}

	public void setComposterContactNumber(String composterContactNumber) {
		this.composterContactNumber = composterContactNumber;
	}

	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Double getDryWaste() {
		return dryWaste;
	}

	public void setDryWaste(Double dryWaste) {
		this.dryWaste = dryWaste;
	}

	public Double getWetWaste() {
		return wetWaste;
	}

	public void setWetWaste(Double wetWaste) {
		this.wetWaste = wetWaste;
	}

}
