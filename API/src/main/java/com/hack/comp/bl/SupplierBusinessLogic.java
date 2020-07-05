package com.hack.comp.bl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hack.comp.dao.impl.SupplierComposterTransactionDAOImpl;
import com.hack.comp.dao.schema.SupplierDAO;
import com.hack.comp.model.supplier.SupplierModelDailyWaste;
import com.hack.comp.model.supplier.SupplierModelDailyWasteNew;
import com.hack.comp.model.supplier.SupplierModelFullSelect;
import com.hack.comp.model.supplier.SupplierModelInsert;
import com.hack.comp.model.supplier.SupplierModelSelect;
import com.hack.comp.model.supplier.SupplierWasteImagesSelect;

@Service
public class SupplierBusinessLogic 
{
	@Autowired
	SupplierDAO sd;
	
	@Autowired
	SupplierBusinessLogic sbl;

	@Autowired
	SupplierComposterTransactionDAOImpl sctdi;
	
	private static final String EXTERNAL_FILE_PATH = "C:\\";

	public static String replaceColonToPeriod(Timestamp t)
	{
		String str=t.toString();
		String newStr=str.replace(':', '.');
		return newStr;
	}
	
	private static String urlCreator(String filePath,String fileName) {
		//http://localhost:8080/fileDownload/view?filePath=member/documents/1/9&fileName=JSP complete reference.pdf
		String protocol="http://";
		String host="13.68.186.134";
		String portNumber="8080";
		String basePath="/supplier/fileView";
		String url=protocol+host+":"+portNumber+basePath+"?filePath="+filePath.replace("\\", "/")+"&fileName="+fileName;
		return url;
	}
	
	public ResponseEntity<Void> addSupplier(SupplierModelInsert smi)
	{
		if(smi==null)
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		Boolean rsMain=false;
		try {
			try {
				rsMain=sd.addSupplier(smi);
		} catch (MessagingException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
	
	private SupplierModelDailyWaste refreshSupplierSubProduct(SupplierModelDailyWaste data,Date searchDate)throws SQLException, ClassNotFoundException 
	{
		return sd.refreshSupplierSubProduct(data,searchDate);
	}
	
	public ResponseEntity<Void> subSupplierProduct(SupplierModelDailyWaste data,Long compsoterId,Date searchDate)
	{
		System.out.println("Here In the beginning");
		 if (data == null)
	        {
	            return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
	        }

	        ResponseEntity<Void> res = null;
	        Long result=null;
	        try {
	        	Double dryWaste=data.getDryWaste();
	        	Double wetWaste=data.getWetWaste();
	        	
	        	DateTime currentDate=new DateTime(System.currentTimeMillis());
	   		 	Timestamp time=data.getDate();
	   		 	DateTime responseDate=new DateTime(time.getTime());
	   		 if(currentDate.getYear()!=responseDate.getYear()&&currentDate.getMonthOfYear()!=responseDate.getMonthOfYear())
	   		 {
	   			 System.out.println("Here");
	   			 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	   		 }
	   		 else
	   		 {
	   			 if(currentDate.getDayOfMonth()-5>responseDate.getDayOfMonth())
	   			 {
	   				System.out.println("Here2");
	   				 return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	   			 }
	   		 }

	        data = sbl.refreshSupplierSubProduct( data , searchDate );
	        if(data==null)
	        {
	        	System.out.println("Here3");
	        	return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	        }
	        System.out.println("My values have been refreshed");
	        System.out.println("DryWaste: "+data.getDryWaste());
	        System.out.println("WetWaste: "+data.getWetWaste());
			result = sd.subSupplierProduct(data,searchDate);
			//HERE
			
			Integer rsMain=sctdi.addSupplierComposterTransaction(
					result, 
					data.getId(),
					compsoterId, 
					data.getDate(),
					dryWaste,
					wetWaste);
			if(rsMain==0)
			{
				System.out.println("Here4");
				return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
			}
	        
	        } catch (ClassNotFoundException e) {
				return new ResponseEntity<Void>( HttpStatus.NOT_FOUND );
			} catch (SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<Void>( HttpStatus.INTERNAL_SERVER_ERROR );
			}
	        
	        if (result != 0)
	        {
	            res = new ResponseEntity<Void>( HttpStatus.OK );
	        }
	        else
	        {
	        	System.out.println("Here5");
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
		System.out.println(username+" "+password);
		if(username==null||password==null)
		{
			return new ResponseEntity<SupplierModelSelect>(sms,HttpStatus.BAD_REQUEST);
		}
		try {
			sms=sd.getSupplier(username, password);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<SupplierModelSelect>(sms,HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	 public ResponseEntity<Void> deleteSupplierWaste(Long supplierWasteId,Date dateToSearch,Timestamp currentTime)
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
	 
	 public ResponseEntity<Void> addSupplierWasteImage(MultipartFile[] file,Long supplierWasteId,Timestamp timeOfEntry)
	 {
		 String[] qualifiedPaths=null;
		 if(file.length==0||supplierWasteId==null||timeOfEntry==null)
		 {
			 return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
		 }
		 try {
			qualifiedPaths=sd.storeFile(file, supplierWasteId, timeOfEntry);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		if(qualifiedPaths.length==0||qualifiedPaths.length!=file.length)
		{
			return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
		}
		 for(int i=0;i<qualifiedPaths.length;i++)
		 {
			 try {
				Integer rs=sd.addSupplierWasteImage(supplierWasteId, timeOfEntry,SupplierBusinessLogic.urlCreator("supplierWasteImages/"+supplierWasteId+"/"+SupplierBusinessLogic.replaceColonToPeriod(timeOfEntry),StringUtils.cleanPath(file[i].getOriginalFilename()) ));
				if(rs==0)
				{
					return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
				}
			} catch (ClassNotFoundException e) {
				return new ResponseEntity<Void>( HttpStatus.NOT_FOUND );
			} catch (SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<Void>( HttpStatus.INTERNAL_SERVER_ERROR );
			}
		 }
		 return new ResponseEntity<Void>( HttpStatus.CREATED );
	 }
	 
		public void viewFile(HttpServletRequest request, 
				HttpServletResponse response,
				String filePath, 
				String fileName){
				File file = new File(EXTERNAL_FILE_PATH + filePath + "\\" + fileName);
				
				if (file.exists()) {
					try {
					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}
					
					response.setContentType(mimeType);

					response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						
					}
				}
		}
		
		public void downloadFile(HttpServletRequest request, 
				HttpServletResponse response,
				String filePath, 
				String fileName){
			try {
				System.out.println(EXTERNAL_FILE_PATH + filePath + "\\" + fileName);
				File file = new File(EXTERNAL_FILE_PATH + filePath + "\\" + fileName);
				if (file.exists()) {
					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}

					response.setContentType(mimeType);

					response.setHeader("Content-Disposition", String.format("attachment;filename=\"" + file.getName() + "\""));

					response.setContentLength((int) file.length());

					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					FileCopyUtils.copy(inputStream, response.getOutputStream());
				}
				
			}
			catch(IOException e) {
				
			}
		}
		
		public ResponseEntity<List<SupplierWasteImagesSelect>> selectSupplierWasteImages(Long supplierId, Date dateForSearch)
		{
			List<SupplierWasteImagesSelect> ll=new ArrayList<SupplierWasteImagesSelect>();
			if(supplierId==null||dateForSearch==null)
			{
				return new ResponseEntity<List<SupplierWasteImagesSelect>>(ll,HttpStatus.BAD_REQUEST);
			}
			try {
				ll=sd.selectSupplierWasteImages(supplierId, dateForSearch);
			} catch (ClassNotFoundException e) {
				return new ResponseEntity<List<SupplierWasteImagesSelect>>(ll,HttpStatus.NOT_FOUND);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<List<SupplierWasteImagesSelect>>(ll,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if(ll.isEmpty())
			{
				return new ResponseEntity<List<SupplierWasteImagesSelect>>(ll,HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<SupplierWasteImagesSelect>>(ll,HttpStatus.OK);
		}
		
		public ResponseEntity<List<SupplierModelFullSelect>> getUniqueSupplierByDateAndState(Date date_t, String state)
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
				sms=sd.getUniqueSupplierByDateAndState(date_t, state);
			} catch (ClassNotFoundException e) {
				return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.NOT_FOUND);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if(sms.isEmpty())
			{
				return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.OK);
		 }
		
		public ResponseEntity<List<SupplierModelFullSelect>> getUniqueSupplierByDateAndCity(Date date_t, String city)
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
				sms=sd.getUniqueSupplierByDateAndCity(date_t, city);
			} catch (ClassNotFoundException e) {
				return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.NOT_FOUND);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if(sms.isEmpty())
			{
				return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<SupplierModelFullSelect>>(sms,HttpStatus.OK);
		 }
		
		public ResponseEntity<Void> deleteSupplierWasteImage(Long wasteImageId)
		{
			if(wasteImageId==null)
			{
				return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
			}
			Integer rsMain=null;
			try {
				rsMain=sd.deleteSupplierWasteImage(wasteImageId);
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
