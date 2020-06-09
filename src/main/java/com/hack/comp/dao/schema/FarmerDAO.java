package com.hack.comp.dao.schema;

import java.sql.SQLException;

import com.hack.comp.model.farmer.FarmerInsert;

public interface FarmerDAO 
{
	 public Integer	validateFarmer(String username,String password)	throws SQLException,ClassNotFoundException;
	 public Boolean addFarmer(FarmerInsert fi)						throws SQLException,ClassNotFoundException;
}
