package com.hack.comp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hack.comp.bl.GovernmentBusinessLogic;
import com.hack.comp.connection.Connections;
import com.hack.comp.model.government.GovernmentDisplay;

@RestController
@RequestMapping("/gov")
@CrossOrigin(origins = "*")
public class GovernmentController
{

	@Autowired
	GovernmentBusinessLogic gbl;

	/*
	 * This method is used by
	 * Government to see funds they
	 * have got
	 */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<GovernmentDisplay> seeFunds()
    {
        return gbl.seeFunds();
    }

    
    /*
     * This method is used to
     * add funds to Government account
     */
    public static Boolean addFunds(Double funds, Integer composter_id) throws SQLException
    {
        if (funds == null || composter_id == null)
        {
            return false;
        }
        Connection c = Connections.setConnection();
        PreparedStatement stmt = c.prepareStatement( "INSERT INTO gov.funds (funds,composter_id)VALUES(?,?)" );
        stmt.setDouble( 1, funds );
        stmt.setInt( 2, composter_id );
        int rs = stmt.executeUpdate();
        if (rs != 0)
        {
            return true;
        }

        return false;
    }

}
