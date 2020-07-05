package com.hack.comp.dao.schema;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.hack.comp.model.composterFarmerTransaction.ComposterFarmerTransactionModel;

public interface ComposterFarmerTransactionDAO 
{
	public Integer 									addComposterFarmerTransaction(Long composter_init_id,Long composter_id,Long farmer_id,Timestamp date_time,String category,String grade,Double price,Double compostWeight)	throws SQLException,ClassNotFoundException;
	public List<ComposterFarmerTransactionModel> 	selectComposterFarmerTransaction(Long init_id)																																throws SQLException,ClassNotFoundException;
	public List<ComposterFarmerTransactionModel>	selectComposterFarmerTransactionByComposterId(Long composterId)																												throws SQLException,ClassNotFoundException;
	public List<ComposterFarmerTransactionModel>	selectComposterFarmerTransactionByDate(Long composterId,Date dateToSearch)																									throws SQLException,ClassNotFoundException;
}
