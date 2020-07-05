package com.hack.comp.dao.schema;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import com.hack.comp.model.supplier.SupplierModelDailyWaste;
import com.hack.comp.model.supplier.SupplierModelDailyWasteNew;
import com.hack.comp.model.supplier.SupplierModelFullSelect;
import com.hack.comp.model.supplier.SupplierModelInsert;
import com.hack.comp.model.supplier.SupplierModelSelect;
import com.hack.comp.model.supplier.SupplierWasteImagesSelect;

public interface SupplierDAO
{
	public Boolean							addSupplier(SupplierModelInsert smi)												throws SQLException, ClassNotFoundException, MessagingException; 
	public SupplierModelDailyWaste 			refreshSupplierAddProduct(SupplierModelDailyWaste data)								throws SQLException,ClassNotFoundException;
	public Integer 							addSupplierProduct(SupplierModelDailyWaste data)									throws SQLException,ClassNotFoundException;
	public SupplierModelDailyWaste 			refreshSupplierSubProduct(SupplierModelDailyWaste data,Date searchDate)				throws SQLException,ClassNotFoundException;
	public Long 							subSupplierProduct(SupplierModelDailyWaste data,Date entryDate)						throws SQLException,ClassNotFoundException;
	public List<SupplierModelDailyWasteNew>	displaySuppliers(Long id)															throws SQLException,ClassNotFoundException;
	public SupplierModelSelect 				getSupplier(String username, String password)										throws SQLException,ClassNotFoundException;
	public List<SupplierModelFullSelect> 	getUniqueSupplierByDate(Date date_t)												throws SQLException,ClassNotFoundException;
	public Integer							deleteSupplierWaste(Long supplierWasteId,Date dateToSearch)							throws SQLException,ClassNotFoundException;
	public Integer							updateDescriptionInWaste(Long supplierWasteId,Date dateToSearch,String description)	throws SQLException,ClassNotFoundException;
	public String[]							storeFile(MultipartFile[] file,Long supplierId,Timestamp timeOfEntry)				throws IOException;
	public Integer							addSupplierWasteImage(Long supplierId,Timestamp timeOfEntry,String imageURL)		throws SQLException,ClassNotFoundException;
	public List<SupplierWasteImagesSelect>	selectSupplierWasteImages(Long supplierId,Date dateForSearch)						throws SQLException,ClassNotFoundException;
	public List<SupplierModelFullSelect>	getUniqueSupplierByDateAndState(Date date_t,String state)							throws SQLException,ClassNotFoundException;
	public List<SupplierModelFullSelect>	getUniqueSupplierByDateAndCity(Date date_t,String city)								throws SQLException,ClassNotFoundException;
	public Integer							deleteSupplierWasteImage(Long wasteImageId)											throws SQLException,ClassNotFoundException;
}
