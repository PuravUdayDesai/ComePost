package com.hack.comp.dao.schema;

import java.sql.SQLException;
import java.sql.Timestamp;

import com.hack.comp.model.supplierComposterTransaction.SupplierComposterTransactionSelectModel;

public interface SupplierComposterTransactionDAO
{
	public Integer									addSupplierComposterTransaction(Long supplier_waste_id,Long supplier_id,Long composter_id,Timestamp date_time)	throws SQLException,ClassNotFoundException;
	public SupplierComposterTransactionSelectModel	selectSupplierComposterTrasnsaction(Long init_id)																throws SQLException,ClassNotFoundException;		
}
