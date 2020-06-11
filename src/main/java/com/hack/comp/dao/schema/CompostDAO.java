package com.hack.comp.dao.schema;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.hack.comp.model.compost.CompostModelInsert;
import com.hack.comp.model.compost.ComposterDailyModelCompost;
import com.hack.comp.model.compost.ComposterDailyModelCompostNew;
import com.hack.comp.model.compost.ComposterFullSelect;
import com.hack.comp.model.compost.ComposterLoginModel;

public interface CompostDAO 
{
	 public ComposterLoginModel 				validateComposter(String username,String password)						throws SQLException,ClassNotFoundException;
	 public Boolean 							addCompost(CompostModelInsert cmi)										throws SQLException,ClassNotFoundException;
	 public Integer 							addCompostProduct(ComposterDailyModelCompost data)						throws SQLException,ClassNotFoundException;
	 public List<ComposterDailyModelCompostNew>	displayComposters(Long id) 												throws SQLException,ClassNotFoundException;
	 public Integer 							subCompostProduct(ComposterDailyModelCompost data,Long init_id)			throws SQLException,ClassNotFoundException;
	 public List<ComposterFullSelect> 			getComposterByDate(Date date) 											throws SQLException,ClassNotFoundException;
	 public Double 								refreshCompostSubProduct(ComposterDailyModelCompost data, Long init_id)	throws SQLException,ClassNotFoundException;

}
