package com.hack.comp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.hack.comp.connection.Connections;
import com.hack.comp.dao.schema.FarmerDAO;
import com.hack.comp.model.farmer.FarmerInsert;

@Service
public class FarmerDAOImpl implements FarmerDAO
{

	@Override
	public Integer validateFarmer(String username, String password) throws SQLException, ClassNotFoundException 
	{
		 Connection c = Connections.setConnection();
	        PreparedStatement stmt = c.prepareStatement( "SELECT * FROM farmer.farmer_login WHERE username=? AND password=?" );
	        stmt.setString( 1, username );
	        stmt.setString( 2, password );
	        ResultSet rs = stmt.executeQuery();
	        Integer id=null;
	        if (rs.next())
	        {
	            id= rs.getInt( "id" );
	        }
	     rs.close();
	     stmt.close();
	     c.close();
		return id;
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
