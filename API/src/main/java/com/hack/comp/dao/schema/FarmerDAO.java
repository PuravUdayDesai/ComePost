package com.hack.comp.dao.schema;

import java.sql.SQLException;

import com.hack.comp.model.farmer.FarmerInsert;
import com.hack.comp.model.farmer.FarmerLoginModel;

public interface FarmerDAO 
{
	 public FarmerLoginModel	validateFarmer(String username,String password)	throws SQLException,ClassNotFoundException;
	 public Boolean 			addFarmer(FarmerInsert fi)						throws SQLException,ClassNotFoundException;
}
