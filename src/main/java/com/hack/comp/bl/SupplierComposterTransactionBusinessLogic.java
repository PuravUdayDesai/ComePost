package com.hack.comp.bl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hack.comp.dao.impl.SupplierComposterTransactionDAOImpl;
import com.hack.comp.model.supplierComposterTransaction.SupplierComposterTransactionSelectModel;

@Service
public class SupplierComposterTransactionBusinessLogic 
{
	@Autowired
	SupplierComposterTransactionDAOImpl sctdi;
	
	public ResponseEntity<SupplierComposterTransactionSelectModel> selectSupplierComposterTrasnsaction(Long init_id)
	{
		SupplierComposterTransactionSelectModel s=null;
		if(init_id==null)
		{
			return new ResponseEntity<SupplierComposterTransactionSelectModel>(s,HttpStatus.BAD_REQUEST);
		}
		try {
			s=sctdi.selectSupplierComposterTrasnsaction(init_id);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<SupplierComposterTransactionSelectModel>(s,HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<SupplierComposterTransactionSelectModel>(s,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(s==null)
		{
			return new ResponseEntity<SupplierComposterTransactionSelectModel>(s,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<SupplierComposterTransactionSelectModel>(s,HttpStatus.OK);
	}
	
	public ResponseEntity<List<SupplierComposterTransactionSelectModel>> selectSupplierComposterTransactionByDate(Long supplierId,Date dateToSearch)
	{
		List<SupplierComposterTransactionSelectModel> s=new ArrayList<SupplierComposterTransactionSelectModel>();
		if(supplierId==null||dateToSearch==null)
		{
			return new ResponseEntity<List<SupplierComposterTransactionSelectModel>>(s,HttpStatus.BAD_REQUEST);
		}
		try {
			s=sctdi.selectSupplierComposterTransactionByDate(supplierId, dateToSearch);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<List<SupplierComposterTransactionSelectModel>>(s,HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<List<SupplierComposterTransactionSelectModel>>(s,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(s.isEmpty())
		{
			return new ResponseEntity<List<SupplierComposterTransactionSelectModel>>(s,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<SupplierComposterTransactionSelectModel>>(s,HttpStatus.OK);
	}
	
	public ResponseEntity<List<SupplierComposterTransactionSelectModel>> selectSupplierComposterTransactionBySupplierId(Long supplierId)
	{
		List<SupplierComposterTransactionSelectModel> s=new ArrayList<SupplierComposterTransactionSelectModel>();
		if(supplierId==null)
		{
			return new ResponseEntity<List<SupplierComposterTransactionSelectModel>>(s,HttpStatus.BAD_REQUEST);
		}
		try {
			s=sctdi.selectSupplierComposterTransactionBySupplierId(supplierId);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<List<SupplierComposterTransactionSelectModel>>(s,HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<List<SupplierComposterTransactionSelectModel>>(s,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(s.isEmpty())
		{
			return new ResponseEntity<List<SupplierComposterTransactionSelectModel>>(s,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<SupplierComposterTransactionSelectModel>>(s,HttpStatus.OK);
	}
	
}
