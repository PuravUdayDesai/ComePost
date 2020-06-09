package com.hack.comp.dao.schema;

import java.sql.SQLException;

import com.hack.comp.model.government.GovernmentDisplay;

public interface GovernmentDAO 
{
	 public GovernmentDisplay seeFunds() throws SQLException,ClassNotFoundException;
}
