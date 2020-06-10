package com.hack.comp.bl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hack.comp.dao.schema.FarmerDAO;
import com.hack.comp.model.farmer.FarmerInsert;

@Service
public class FarmerBusinessLogic
{
	@Autowired
	FarmerDAO fd;
	
	public ResponseEntity<Integer> validateFarmer(String username,String password)
	{
		System.out.println("Username: "+username+" Passoword:"+password);
		Integer rsMain=null;
		if(username==null||password==null)
		{
			return new ResponseEntity<Integer>(rsMain,HttpStatus.BAD_REQUEST);
		}
		try {
			rsMain=fd.validateFarmer(username, password);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Integer>(rsMain,HttpStatus.NO_CONTENT);
		} catch (SQLException e) {
			return new ResponseEntity<Integer>(rsMain,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		System.out.println("FARMER: "+rsMain);
		if(rsMain==null)
		{
			return new ResponseEntity<Integer>(-1,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Integer>(rsMain,HttpStatus.OK);
	}
	
	 public ResponseEntity<Void> addFarmer(FarmerInsert fi)
	 {
		 if(fi==null)
		 {
			 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		 }
		 Boolean rsMain=false;
		 try {
			rsMain=fd.addFarmer(fi);
		 } catch (ClassNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (SQLException e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
		if(!rsMain)
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	 }
	
	
}
