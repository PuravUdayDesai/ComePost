package com.hack.comp.dao.schema;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.hack.comp.model.composterFarmerTransaction.ComposterFarmerTransactionModel;

public interface ComposterFarmerTransactionDAO 
{
	public Integer 									addComposterFarmerTransaction(Long composter_init_id,Long composter_id,Long farmer_id,Timestamp date_time)	throws SQLException,ClassNotFoundException;
	public List<ComposterFarmerTransactionModel> 	selectComposterFarmerTransaction(Long init_id)																throws SQLException,ClassNotFoundException;
}
