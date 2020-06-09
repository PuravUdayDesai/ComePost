package com.hack.comp.bl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hack.comp.dao.impl.GovernmentDAOImpl;
import com.hack.comp.model.government.GovernmentDisplay;

@Service
public class GovernmentBusinessLogic 
{
	@Autowired
	GovernmentDAOImpl gdi;
	
	public ResponseEntity<GovernmentDisplay> seeFunds()
	{
		GovernmentDisplay gd=null;
		try {
			gd=gdi.seeFunds();
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<GovernmentDisplay>( new GovernmentDisplay(), HttpStatus.NOT_FOUND );
		} catch (SQLException e) {
			return new ResponseEntity<GovernmentDisplay>( new GovernmentDisplay(), HttpStatus.INTERNAL_SERVER_ERROR );
		}
		return new ResponseEntity<GovernmentDisplay>( gd, HttpStatus.OK );
	}
}
