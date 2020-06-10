package com.hack.comp.bl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hack.comp.controller.GovernmentController;
import com.hack.comp.dao.schema.CompostDAO;
import com.hack.comp.model.compost.CompostAndSupplierModel;
import com.hack.comp.model.compost.CompostModelInsert;
import com.hack.comp.model.compost.ComposterDailyModelCompost;
import com.hack.comp.model.compost.ComposterDailyModelCompostNew;
import com.hack.comp.model.compost.ComposterFullSelect;
import com.hack.comp.model.compost.ComposterLoginModel;


@Service
public class CompostBusinessLogic
{
	@Autowired
	CompostDAO cdi;
	
	@Autowired
	CompostBusinessLogic cbl;
	
	public ResponseEntity<ComposterLoginModel> validateComposter(String username,String password)
	{
		ComposterLoginModel lm=new ComposterLoginModel();
		if(username==null||password==null)
		{
			return new ResponseEntity<ComposterLoginModel>(lm,HttpStatus.BAD_REQUEST);
		}
		try {
			lm=cdi.validateComposter(username, password);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<ComposterLoginModel>(lm,HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<ComposterLoginModel>(lm,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(!lm.getCheck())
		{
			return new ResponseEntity<ComposterLoginModel>(lm,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<ComposterLoginModel>(lm,HttpStatus.OK);
	}
	
	public ResponseEntity<Void> addCompost(CompostModelInsert cmi)
	{
		Boolean rsMain=false;
		if(cmi==null)
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		try {
			rsMain=cdi.addCompost(cmi);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(!rsMain)
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	 public ResponseEntity<Void> compAndSuppTran(Long composter_id, CompostAndSupplierModel casm)
	 {
		 Integer rs=null;
		 if(composter_id==null||casm==null)
		 {
			 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		 }
		 
		 try {
			rs=cdi.compAndSuppTran(composter_id, casm);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	      if (rs == 0)
	      {
	    	  return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	      }
	      return new ResponseEntity<Void>(HttpStatus.CREATED);
	 }
	 
	 public ResponseEntity<Void> addCompostProduct(ComposterDailyModelCompost data)
	 {
		 Integer rs=null;
		 if(data==null)
		 {
			 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		 }
		 try {
			rs=cdi.addCompostProduct(data);
			} catch (ClassNotFoundException e) {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			} catch (SQLException e) {
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	      if (rs == 0)
	      {
	    	  return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	      }
	      return new ResponseEntity<Void>(HttpStatus.CREATED);
	 }
	 
	    private ComposterDailyModelCompost refreshCompostSubProduct(ComposterDailyModelCompost data, Long init_id) throws ClassNotFoundException, SQLException
	    {
	    	Double compostWeight=null;
				compostWeight=cdi.refreshCompostSubProduct(data, init_id);
	    	data.setCompostWeight( compostWeight - data.getCompostWeight() );
	        if (compostWeight == 0)
	        {
	            data.setCompostWeight( -1.0 );
	        }
	        return data;
	    }
	    
	 public ResponseEntity<Double> subCompostProduct(ComposterDailyModelCompost data, Long init_id)
	 {
		if (data == null)
	    {
	       return new ResponseEntity<Double>( 0.0, HttpStatus.BAD_REQUEST );
	    }
		Double prevCompostWeight=data.getCompostWeight();
		try {
		data = cbl.refreshCompostSubProduct( data, init_id );

	    if (data.getCompostWeight() < 0)
	    {
	        return new ResponseEntity<Double>( 0.0, HttpStatus.BAD_REQUEST );
	    }
	    
	    Integer result=cdi.subCompostProduct(data, init_id);
	    if (result != 0)
        {
            Boolean resRS = GovernmentController.addFunds( (data.getPrice() * prevCompostWeight) * 0.05, result );
            if (!resRS)
            {
            	return new ResponseEntity<Double>( 0.0, HttpStatus.BAD_REQUEST );
            }
        }
        else
        {
            return new ResponseEntity<Double>( 0.0, HttpStatus.BAD_REQUEST );
        }
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Double>( 0.0, HttpStatus.NOT_FOUND );
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<Double>( 0.0, HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
		 return new ResponseEntity<Double>( (data.getPrice() * prevCompostWeight), HttpStatus.OK );
	 }
	 
	 public ResponseEntity<List<ComposterDailyModelCompostNew>> displaySuppliers(Long id)
	 {
		 List<ComposterDailyModelCompostNew> sms = new ArrayList<ComposterDailyModelCompostNew>();
		 if(id==null)
		 {
			 return new ResponseEntity<List<ComposterDailyModelCompostNew>>( sms, HttpStatus.BAD_REQUEST );
		 }
		 
		 try {
			sms=cdi.displaySuppliers(id);
		} catch (ClassNotFoundException e) {
			 return new ResponseEntity<List<ComposterDailyModelCompostNew>>( sms, HttpStatus.NOT_FOUND );
		} catch (SQLException e) {
			 return new ResponseEntity<List<ComposterDailyModelCompostNew>>( sms, HttpStatus.INTERNAL_SERVER_ERROR );
		}
		 
		 if(sms.isEmpty())
		 {
			 return new ResponseEntity<List<ComposterDailyModelCompostNew>>( sms, HttpStatus.NO_CONTENT );
		 }
		 return new ResponseEntity<List<ComposterDailyModelCompostNew>>( sms, HttpStatus.OK );
	 }
	 
	 public ResponseEntity<List<ComposterFullSelect>> getSupplierByDate(Date date)
	 {
		List<ComposterFullSelect> lc=new ArrayList<ComposterFullSelect>();
		if(date==null)
		{
			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.BAD_REQUEST );
		}
		try {
			lc=cdi.getSupplierByDate(date);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.NOT_FOUND );
		} catch (SQLException e) {
			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.INTERNAL_SERVER_ERROR );
		}
		if(lc.isEmpty())
		{
			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.NO_CONTENT );
		}
		return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.OK );
	 }
}
=======
package com.hack.comp.bl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hack.comp.controller.GovernmentController;
import com.hack.comp.dao.schema.CompostDAO;
import com.hack.comp.model.LoginModel;
import com.hack.comp.model.compost.CompostAndSupplierModel;
import com.hack.comp.model.compost.CompostModelInsert;
import com.hack.comp.model.compost.ComposterDailyModelCompost;
import com.hack.comp.model.compost.ComposterDailyModelCompostNew;
import com.hack.comp.model.compost.ComposterFullSelect;


@Service
public class CompostBusinessLogic
{
	@Autowired
	CompostDAO cdi;
	
	@Autowired
	CompostBusinessLogic cbl;
	
	public ResponseEntity<LoginModel> validateComposter(String username,String password)
	{
		LoginModel lm=new LoginModel();
		if(username==null||password==null)
		{
			return new ResponseEntity<LoginModel>(lm,HttpStatus.BAD_REQUEST);
		}
		try {
			lm=cdi.validateComposter(username, password);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<LoginModel>(lm,HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<LoginModel>(lm,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(lm==null)
		{
			LoginModel lm2=new LoginModel();
			lm2.setCheck(false);
			return new ResponseEntity<LoginModel>(lm2,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<LoginModel>(lm,HttpStatus.OK);
	}
	
	public ResponseEntity<Void> addCompost(CompostModelInsert cmi)
	{
		Boolean rsMain=false;
		if(cmi==null)
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		try {
			rsMain=cdi.addCompost(cmi);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(!rsMain)
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	 public ResponseEntity<Void> compAndSuppTran(Long composter_id, CompostAndSupplierModel casm)
	 {
		 Integer rs=null;
		 if(composter_id==null||casm==null)
		 {
			 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		 }
		 
		 try {
			rs=cdi.compAndSuppTran(composter_id, casm);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	      if (rs == 0)
	      {
	    	  return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	      }
	      return new ResponseEntity<Void>(HttpStatus.CREATED);
	 }
	 
	 public ResponseEntity<Void> addCompostProduct(ComposterDailyModelCompost data)
	 {
		 Integer rs=null;
		 if(data==null)
		 {
			 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		 }
		 try {
			rs=cdi.addCompostProduct(data);
			} catch (ClassNotFoundException e) {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			} catch (SQLException e) {
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	      if (rs == 0)
	      {
	    	  return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	      }
	      return new ResponseEntity<Void>(HttpStatus.CREATED);
	 }
	 
	    private ComposterDailyModelCompost refreshCompostSubProduct(ComposterDailyModelCompost data, Long init_id) throws ClassNotFoundException, SQLException
	    {
	    	Double compostWeight=null;
				compostWeight=cdi.refreshCompostSubProduct(data, init_id);
	    	data.setCompostWeight( compostWeight - data.getCompostWeight() );
	        if (compostWeight == 0)
	        {
	            data.setCompostWeight( -1.0 );
	        }
	        return data;
	    }
	    
	 public ResponseEntity<Double> subCompostProduct(ComposterDailyModelCompost data, Long init_id)
	 {
		if (data == null)
	    {
	       return new ResponseEntity<Double>( 0.0, HttpStatus.BAD_REQUEST );
	    }
		Double prevCompostWeight=data.getCompostWeight();
		try {
		data = cbl.refreshCompostSubProduct( data, init_id );

	    if (data.getCompostWeight() < 0)
	    {
	        return new ResponseEntity<Double>( 0.0, HttpStatus.BAD_REQUEST );
	    }
	    
	    Integer result=cdi.subCompostProduct(data, init_id);
	    if (result != 0)
        {
            Boolean resRS = GovernmentController.addFunds( (data.getPrice() * prevCompostWeight) * 0.05, result );
            if (!resRS)
            {
            	return new ResponseEntity<Double>( 0.0, HttpStatus.BAD_REQUEST );
            }
        }
        else
        {
            return new ResponseEntity<Double>( 0.0, HttpStatus.BAD_REQUEST );
        }
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Double>( 0.0, HttpStatus.NOT_FOUND );
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<Double>( 0.0, HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
		 return new ResponseEntity<Double>( (data.getPrice() * prevCompostWeight), HttpStatus.OK );
	 }
	 
	 public ResponseEntity<List<ComposterDailyModelCompostNew>> displaySuppliers(Long id)
	 {
		 List<ComposterDailyModelCompostNew> sms = new ArrayList<ComposterDailyModelCompostNew>();
		 if(id==null)
		 {
			 return new ResponseEntity<List<ComposterDailyModelCompostNew>>( sms, HttpStatus.BAD_REQUEST );
		 }
		 
		 try {
			sms=cdi.displaySuppliers(id);
		} catch (ClassNotFoundException e) {
			 return new ResponseEntity<List<ComposterDailyModelCompostNew>>( sms, HttpStatus.NOT_FOUND );
		} catch (SQLException e) {
			 return new ResponseEntity<List<ComposterDailyModelCompostNew>>( sms, HttpStatus.INTERNAL_SERVER_ERROR );
		}
		 
		 if(sms.isEmpty())
		 {
			 return new ResponseEntity<List<ComposterDailyModelCompostNew>>( sms, HttpStatus.NO_CONTENT );
		 }
		 return new ResponseEntity<List<ComposterDailyModelCompostNew>>( sms, HttpStatus.OK );
	 }
	 
	 public ResponseEntity<List<ComposterFullSelect>> getSupplierByDate(Date date)
	 {
		List<ComposterFullSelect> lc=new ArrayList<ComposterFullSelect>();
		if(date==null)
		{
			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.BAD_REQUEST );
		}
		try {
			lc=cdi.getSupplierByDate(date);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.NOT_FOUND );
		} catch (SQLException e) {
			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.INTERNAL_SERVER_ERROR );
		}
		if(lc.isEmpty())
		{
			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.NO_CONTENT );
		}
		return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.OK );
	 }
}
