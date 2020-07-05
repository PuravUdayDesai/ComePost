package com.hack.comp.model.supplier;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SupplierWasteImagesSelect 
{
	@NotNull(message = "SupplierId cannot be NULL")
	private Long supplierId;
	@NotNull(message = "SupplierWasteImageId cannot be NULL")
	private Long supplierWasteImageId;
	@NotNull(message = "DateAndTime cannot be NULL")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
	private Timestamp dateAndTime;
	@NotNull(message = "ImageURL cannot be NULL")
	private String imageURL;
	@NotNull(message = "DateString cannot be NULL")
	private String dateString;
	
	public SupplierWasteImagesSelect()
	{
		
	}


	public SupplierWasteImagesSelect(@NotNull(message = "SupplierId cannot be NULL") Long supplierId,
			@NotNull(message = "SupplierWasteImageId cannot be NULL") Long supplierWasteImageId,
			@NotNull(message = "DateAndTime cannot be NULL") Timestamp dateAndTime,
			@NotNull(message = "ImageURL cannot be NULL") String imageURL,
			@NotNull(message = "DateString cannot be NULL") String dateString) {
		super();
		this.supplierId = supplierId;
		this.supplierWasteImageId = supplierWasteImageId;
		this.dateAndTime = dateAndTime;
		this.imageURL = imageURL;
		this.dateString = dateString;
	}


	public String getDateString() {
		return dateString;
	}


	public void setDateString(String dateString) {
		this.dateString = dateString;
	}


	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getSupplierWasteImageId() {
		return supplierWasteImageId;
	}

	public void setSupplierWasteImageId(Long supplierWasteImageId) {
		this.supplierWasteImageId = supplierWasteImageId;
	}

	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
}
