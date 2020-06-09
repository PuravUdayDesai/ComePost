package com.hack.comp.dao.schema;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.hack.comp.model.LoginModel;
import com.hack.comp.model.compost.CompostAndSupplierModel;
import com.hack.comp.model.compost.CompostModelInsert;
import com.hack.comp.model.compost.ComposterDailyModelCompost;
import com.hack.comp.model.compost.ComposterDailyModelCompostNew;
import com.hack.comp.model.compost.ComposterFullSelect;

public interface CompostDAO 
{
	 public LoginModel 							validateComposter(String username,String password)						throws SQLException,ClassNotFoundException;
	 public Boolean 							addCompost(CompostModelInsert cmi)										throws SQLException,ClassNotFoundException;
	 public Integer 							compAndSuppTran(Long composter_id,CompostAndSupplierModel casm)			throws SQLException,ClassNotFoundException;
	 public Integer 							addCompostProduct(ComposterDailyModelCompost data)						throws SQLException,ClassNotFoundException;
	 public Double 								refreshCompostSubProduct(ComposterDailyModelCompost data, Long init_id)	throws SQLException,ClassNotFoundException;
	 public Integer 							subCompostProduct(ComposterDailyModelCompost data,Long init_id)			throws SQLException,ClassNotFoundException;
	 public List<ComposterDailyModelCompostNew>	displaySuppliers(Long id) 												throws SQLException,ClassNotFoundException;
	 public List<ComposterFullSelect> 			getSupplierByDate(Date date) 											throws SQLException,ClassNotFoundException;
}
