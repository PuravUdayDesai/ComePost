package com.hack.comp.bl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hack.comp.dao.schema.SupplierDAO;
import com.hack.comp.model.supplier.SupplierModelDailyWaste;
import com.hack.comp.model.supplier.SupplierModelDailyWasteNew;
import com.hack.comp.model.supplier.SupplierModelFullSelect;
import com.hack.comp.model.supplier.SupplierModelInsert;
import com.hack.comp.model.supplier.SupplierModelSelect;

@Service
public class SupplierBusinessLogic 
{
	@Autowired
	SupplierDAO sd;
	
	@Autowired
	SupplierBusinessLogic sbl;
	
	public ResponseEntity<Void> addSupplier(SupplierModelInsert smi)
	{
		if(smi==null)
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		Boolean rsMain=false;
		try {
			rsMain=sd.addSupplier(smi);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(!rsMain)
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		//TODO Mail all the Composters in same city about a new supplier
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	private SupplierModelDailyWaste refreshSupplierAddProduct(SupplierModelDailyWaste data)throws SQLException, ClassNotFoundException 
	{
		return sd.refreshSupplierAddProduct(data);
	}
	
	public ResponseEntity<Void> addSupplierProduct(SupplierModelDailyWaste data)
	{
		Integer rsMain=null;
		if(data==null)
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		 if (data.getDryWaste() > 25 || data.getWetWaste() > 25)
	     {
			 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	     }
		 DateTime currentDate=new DateTime(System.currentTimeMillis());
		 Timestamp time=data.getDate();
		 DateTime responseDate=new DateTime(time.getTime());
		 if(currentDate.getYear()!=responseDate.getYear()&&currentDate.getMonthOfYear()!=responseDate.getMonthOfYear())
		 {
			 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		 }
		 else
		 {
			 if(currentDate.getDayOfMonth()-5>responseDate.getDayOfMonth())
			 {
				 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			 }
		 }
		try {
				data = sbl.refreshSupplierAddProduct( data );
				if (data.getDryWaste() > 25 || data.getWetWaste() > 25)
			     {
					 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			     }
				rsMain=sd.addSupplierProduct(data);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(rsMain==null)
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			//TODO mail all composters which are in same city
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	private SupplierModelDailyWaste refreshSupplierSubProduct(SupplierModelDailyWaste data)throws SQLException, ClassNotFoundException 
	{
		return sd.refreshSupplierSubProduct(data);
	}
	
	public ResponseEntity<Void> subSupplierProduct(SupplierModelDailyWaste data)
	{
		 if (data == null)
	        {
	            return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
	        }

	        ResponseEntity<Void> res = null;
	        Integer result=null;
	        try {
	        	DateTime currentDate=new DateTime(System.currentTimeMillis());
	   		 	Timestamp time=data.getDate();
	   		 	DateTime responseDate=new DateTime(time.getTime());
	   		 if(currentDate.getYear()!=responseDate.getYear()&&currentDate.getMonthOfYear()!=responseDate.getMonthOfYear())
	   		 {
	   			 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	   		 }
	   		 else
	   		 {
	   			 if(currentDate.getDayOfMonth()-5>responseDate.getDayOfMonth())
	   			 {
	   				 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	   			 }
	   		 }
	        data = sbl.refreshSupplierSubProduct( data );
	        if(data==null)
	        {
	        	return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	        }
			result = sd.subSupplierProduct(data);
			} catch (ClassNotFoundException e) {
				return new ResponseEntity<Void>( HttpStatus.NOT_FOUND );
			} catch (SQLException e) {
				return new ResponseEntity<Void>( HttpStatus.INTERNAL_SERVER_ERROR );
			}
	        
	        if (result != 0)
	        {
	            res = new ResponseEntity<Void>( HttpStatus.OK );
	        }
	        else
	        {
	            res = new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
	        }
	        return res;
	}
	
	public ResponseEntity<List<SupplierModelDailyWasteNew>> displaySuppliers(Long id)
	{
		List<SupplierModelDailyWasteNew> sms = new ArrayList<SupplierModelDailyWasteNew>();
		if(id==null)
		{
			return new ResponseEntity<List<SupplierModelDailyWasteNew>>( sms, HttpStatus.BAD_REQUEST );
		}
		try {
			sms=sd.displaySuppliers(id);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<List<SupplierModelDailyWasteNew>>( sms, HttpStatus.NOT_FOUND );
		} catch (SQLException e) {
			return new ResponseEntity<List<SupplierModelDailyWasteNew>>( sms, HttpStatus.INTERNAL_SERVER_ERROR );
		}
		if(sms.isEmpty())
		{
			return new ResponseEntity<List<SupplierModelDailyWasteNew>>( sms, HttpStatus.NO_CONTENT );
		}
		return new ResponseEntity<List<SupplierModelDailyWasteNew>>( sms, HttpStatus.OK );
	}
	
	public ResponseEntity<SupplierModelSelect> getSupplier(String username, String password)
	{
		SupplierModelSelect sms=new SupplierModelSelect();
		if(username==null||password==null)
		{
			return new ResponseEntity<SupplierModelSelect>(sms,HttpStatus.BAD_REQUEST);
		}
		try {
			sms=sd.getSupplier(username, password);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<SupplierModelSelect>(sms,HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<SupplierModelSelect>(sms,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(sms==null)
		{
			SupplierModelSelect smsInvalid=new SupplierModelSelect();
			smsInvalid.setValid(false);
			return new ResponseEntity<SupplierModelSelect>(smsInvalid,HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<SupplierModelSelect>(sms,HttpStatus.OK);
	}

	 public ResponseEntity<List<SupplierModelFullSelect>> getUniqueSupplierByDate(Date date_t)
	 {
		List<SupplierModelFullSelect> sms = new ArrayList<SupplierModelFullSelect>();
		if(date_t==null)
		{
			return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.BAD_REQUEST);
		}
    	DateTime currentDate=new DateTime(System.currentTimeMillis());
		DateTime responseDate=new DateTime(date_t.getTime());
		 if(currentDate.getYear()!=responseDate.getYear()&&currentDate.getMonthOfYear()!=responseDate.getMonthOfYear())
		 {
			 return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.BAD_REQUEST);
		 }
		 else
		 {
			 if(currentDate.getDayOfMonth()-5>responseDate.getDayOfMonth())
			 {
				 return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.BAD_REQUEST);
			 }
		 }
		try {
			sms=sd.getUniqueSupplierByDate(date_t);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(sms.isEmpty())
		{
			return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.OK);
	 }
	
	 public ResponseEntity<Void> deleteSupplierWaste(Long supplierWasteId,Date dateToSearch)
	 {
		 Integer rsMain=null;
		 if(supplierWasteId==null)
		 {
			 return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
		 }
		 try {
			rsMain=sd.deleteSupplierWaste(supplierWasteId,dateToSearch);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Void>( HttpStatus.NOT_FOUND );
		} catch (SQLException e) {
			return new ResponseEntity<Void>( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		 if(rsMain==0)
		 {
			 return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
		 }
		 return new ResponseEntity<Void>( HttpStatus.OK );
	 }
	 
	 public ResponseEntity<Void> updateDescriptionInWaste(Long supplierWasteId, Date dateToSearch,String description)
	 {
		 Integer rsMain=null;
		 if(supplierWasteId==null)
		 {
			 return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
		 }
		 try {
			rsMain=sd.updateDescriptionInWaste(supplierWasteId, dateToSearch, description);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Void>( HttpStatus.NOT_FOUND );
		} catch (SQLException e) {
			return new ResponseEntity<Void>( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		 if(rsMain==0)
		 {
			 return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
		 }
		 return new ResponseEntity<Void>( HttpStatus.OK );
	 }
}
