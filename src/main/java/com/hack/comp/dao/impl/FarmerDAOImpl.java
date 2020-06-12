package com.hack.comp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.hack.comp.connection.Connections;
import com.hack.comp.dao.schema.FarmerDAO;
import com.hack.comp.model.farmer.FarmerInsert;
import com.hack.comp.model.farmer.FarmerLoginModel;

@Service
public class FarmerDAOImpl implements FarmerDAO
{

	@Override
	public FarmerLoginModel validateFarmer(String username, String password) throws SQLException, ClassNotFoundException 
	{
		 Connection c = Connections.setConnection();
	        PreparedStatement stmt = c.prepareStatement( "\r\n" + 
	        		"SELECT\r\n" + 
	        		"	farmer.farmer_info.id,\r\n" + 
	        		"	farmer.farmer_info.farmer_name,\r\n" + 
	        		"	farmer.farmer_info.farmer_contact_number,\r\n" + 
	        		"	farmer.farmer_info.survey_id,\r\n" + 
	        		"	farmer.farmer_location.state,\r\n" + 
	        		"	farmer.farmer_location.city,\r\n" + 
	        		"	farmer.farmer_location.area,\r\n" + 
	        		"	farmer.farmer_location.street,\r\n" + 
	        		"	farmer.farmer_location.latitude,\r\n" + 
	        		"	farmer.farmer_location.longitude\r\n" + 
	        		"FROM farmer.farmer_info\r\n" + 
	        		"JOIN farmer.farmer_location\r\n" + 
	        		"ON farmer.farmer_location.farmer_id=farmer.farmer_info.id\r\n" + 
	        		"JOIN farmer.farmer_login\r\n" + 
	        		"ON farmer.farmer_login.id=farmer.farmer_login.id\r\n" + 
	        		"WHERE farmer.farmer_login.username=?\r\n" + 
	        		"AND farmer.farmer_login.password=?\r\n" + 
	        		"AND farmer.farmer_login.\"deleteIndex\"=FALSE" );
	        stmt.setString( 1, username );
	        stmt.setString( 2, password );
	        ResultSet rs = stmt.executeQuery();
	        FarmerLoginModel flm=new FarmerLoginModel();
	        if (rs.next())
	        {
	            flm.setId(rs.getLong("id"));
	            flm.setFarmerName(rs.getString("farmer_name"));
	            flm.setFarmerContact(rs.getString("farmer_contact_number"));
	            flm.setSurveyId(rs.getInt("survey_id"));
	            flm.setState(rs.getString("state"));
	            flm.setCity(rs.getString("city"));
	            flm.setArea(rs.getString("area"));
	            flm.setStreet(rs.getString("street"));
	            flm.setLatitude(rs.getString("latitude"));
	            flm.setLongitude(rs.getString("longitude"));
	            flm.setCheck(true);
	        }
	        else
	        {
	        	 flm.setCheck(false);
	        }
	     rs.close();
	     stmt.close();
	     c.close();
		return flm;
	}

	@Override
	public Boolean addFarmer(FarmerInsert fi) throws SQLException, ClassNotFoundException
	{
		Connection c = Connections.setConnection();
        PreparedStatement stmt = c.prepareStatement( "SELECT farmer.\"fn_addFarmer\"(?,?,?,?,?,?,?,?,?,?)" );
        stmt.setString( 1, fi.getFarmerName() );
        stmt.setString( 2, fi.getFarmerContact() );
        stmt.setInt( 3, fi.getSurveyId() );
        stmt.setString( 4, fi.getPassword() );
        stmt.setString( 5, fi.getLatitude() );
        stmt.setString( 6, fi.getLongitude() );
        stmt.setString( 7, fi.getStreet() );
        stmt.setString( 8, fi.getArea() );
        stmt.setString( 9, fi.getCity() );
        stmt.setString( 10, fi.getState() );
        Boolean rs = stmt.execute();
        c.commit();
        stmt.close();
        c.close();
        return rs;
	}

}
