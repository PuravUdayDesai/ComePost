package com.hack.comp.bl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hack.comp.dao.impl.ComposterFarmerTransactionDAOImpl;
import com.hack.comp.model.composterFarmerTransaction.ComposterFarmerTransactionModel;

@Service
public class ComposterFarmerTransactionBusinessLogic 
{
	
	@Autowired
	ComposterFarmerTransactionDAOImpl cftbl;
	
	
	public ResponseEntity<List<ComposterFarmerTransactionModel>> selectComposterFarmerTransaction(Long init_id)
	{
		List<ComposterFarmerTransactionModel> cf=new ArrayList<ComposterFarmerTransactionModel>();
		if(init_id==null)
		{
			return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.BAD_REQUEST);
		}
		try {
			cf=cftbl.selectComposterFarmerTransaction(init_id);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cf.isEmpty())
		{
			return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.OK);
	}
	
	public ResponseEntity<List<ComposterFarmerTransactionModel>> selectComposterFarmerTransactionByComposterId(Long composterId)
	{
		List<ComposterFarmerTransactionModel> cf=new ArrayList<ComposterFarmerTransactionModel>();
		if(composterId==null)
		{
			return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.BAD_REQUEST);
		}
		try {
			cf=cftbl.selectComposterFarmerTransactionByComposterId(composterId);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cf.isEmpty())
		{
			return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.OK);
	}
	
	public ResponseEntity<List<ComposterFarmerTransactionModel>> selectComposterFarmerTransactionByDate(Long composterId,Date dateToSearch)
	{
		List<ComposterFarmerTransactionModel> cf=new ArrayList<ComposterFarmerTransactionModel>();
		if(composterId==null||dateToSearch==null)
		{
			return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.BAD_REQUEST);
		}
		try {
			cf=cftbl.selectComposterFarmerTransactionByDate(composterId, dateToSearch);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cf.isEmpty())
		{
			return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ComposterFarmerTransactionModel>>(cf,HttpStatus.OK);
	}
}
