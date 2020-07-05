package com.hack.comp.dao.schema;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.hack.comp.model.supplierComposterTransaction.SupplierComposterTransactionSelectModel;

public interface SupplierComposterTransactionDAO
{
	public Integer											addSupplierComposterTransaction(Long supplier_waste_id,Long supplier_id,Long composter_id,Timestamp date_time,Double dryWaste,Double wetWaste)	throws SQLException,ClassNotFoundException;
	public SupplierComposterTransactionSelectModel			selectSupplierComposterTrasnsaction(Long init_id)																								throws SQLException,ClassNotFoundException;		
	public List<SupplierComposterTransactionSelectModel>	selectSupplierComposterTransactionByDate(Long supplierId,Date dateToSearch)																		throws SQLException,ClassNotFoundException;	
	public List<SupplierComposterTransactionSelectModel>	selectSupplierComposterTransactionBySupplierId(Long supplierId)																					throws SQLException,ClassNotFoundException;	
}
