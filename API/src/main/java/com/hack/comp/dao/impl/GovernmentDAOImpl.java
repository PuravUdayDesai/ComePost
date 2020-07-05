package com.hack.comp.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

import com.hack.comp.connection.Connections;
import com.hack.comp.dao.schema.GovernmentDAO;
import com.hack.comp.model.government.GovernmentDisplay;

@Service
public class GovernmentDAOImpl implements GovernmentDAO
{

	@Override
	public GovernmentDisplay seeFunds() throws SQLException, ClassNotFoundException
	{
		Connection c = Connections.setConnection();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT gov.funds.funds,\r\n" + "composter.composter_info.name,\r\n" + "composter.composter_info.contact,\r\n" + "composter.composter_info.email,\r\n" + "composter.composter_info.reg_no FROM gov.funds \r\n" + "JOIN composter.composter_info \r\n" + "ON gov.funds.composter_id=composter.composter_info.id \r\n" + "ORDER BY gov.funds.id DESC;" );
        GovernmentDisplay gd=null;
        if (rs.next())
        {
           gd=new GovernmentDisplay(
        		   rs.getDouble( "funds" ),
        		   rs.getString( "name" ), 
        		   rs.getString( "contact" ), 
        		   rs.getString( "email" ), 
        		   rs.getString( "reg_no" ));
        }
        rs.close();
        stmt.close();
        c.close();
		return gd;
	}

}
