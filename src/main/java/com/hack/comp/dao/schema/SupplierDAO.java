package com.hack.comp.dao.schema;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.hack.comp.model.supplier.SupplierModelDailyWaste;
import com.hack.comp.model.supplier.SupplierModelDailyWasteNew;
import com.hack.comp.model.supplier.SupplierModelFullSelect;
import com.hack.comp.model.supplier.SupplierModelInsert;
import com.hack.comp.model.supplier.SupplierModelSelect;

public interface SupplierDAO
{
	public Boolean							addSupplier(SupplierModelInsert smi)					throws SQLException,ClassNotFoundException;
	public SupplierModelDailyWaste 			refreshSupplierAddProduct(SupplierModelDailyWaste data)	throws SQLException,ClassNotFoundException;
	public Integer 							addSupplierProduct(SupplierModelDailyWaste data)		throws SQLException,ClassNotFoundException;
	public SupplierModelDailyWaste 			refreshSupplierSubProduct(SupplierModelDailyWaste data)	throws SQLException,ClassNotFoundException;
	public Integer 							subSupplierProduct(SupplierModelDailyWaste data)		throws SQLException,ClassNotFoundException;
	public List<SupplierModelDailyWasteNew>	displaySuppliers(Integer id)							throws SQLException,ClassNotFoundException;
	public SupplierModelSelect 				getSupplier(String username, String password)			throws SQLException,ClassNotFoundException;
	public List<SupplierModelFullSelect> 	getSupplierByDate(Date date_t)							throws SQLException,ClassNotFoundException;
	public List<SupplierModelFullSelect> 	getUniqueSupplierByDate(Date date_t)					throws SQLException,ClassNotFoundException;
}
