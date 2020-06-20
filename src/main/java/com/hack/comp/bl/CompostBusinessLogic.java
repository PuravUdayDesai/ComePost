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

import com.hack.comp.controller.GovernmentController;
import com.hack.comp.dao.impl.ComposterFarmerTransactionDAOImpl;
import com.hack.comp.dao.schema.CompostDAO;
import com.hack.comp.model.compost.CompostModelInsert;
import com.hack.comp.model.compost.ComposterCompostImageSelect;
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

	@Autowired
	ComposterFarmerTransactionDAOImpl cftdi;

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
		String host="13.68.186.134";//"52.188.114.136";
		String portNumber="8080";
		String basePath="/compost/fileView";
		String url=protocol+host+":"+portNumber+basePath+"?filePath="+filePath.replace("\\", "/")+"&fileName="+fileName;
		return url;
	}
	
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
	
	//Add Composter
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
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(!rsMain)
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	 //Add Compost
	 public ResponseEntity<Long> addCompostProduct(ComposterDailyModelCompost data)
	 {
		 Long init_id=null;
		 if(data==null)
		 {
			 return new ResponseEntity<Long>((long)-1,HttpStatus.BAD_REQUEST);
		 }
		 try {
			init_id=cdi.addCompostProduct(data);
			} catch (ClassNotFoundException e) {
				return new ResponseEntity<Long>((long)-1,HttpStatus.NOT_FOUND);
			} catch (SQLException e) {
				return new ResponseEntity<Long>((long)-1,HttpStatus.INTERNAL_SERVER_ERROR);
			}
	      if (init_id==null)
	      {
	    	  return new ResponseEntity<Long>((long)-1,HttpStatus.BAD_REQUEST);
	      }
	      return new ResponseEntity<Long>(init_id,HttpStatus.CREATED);
	 }
	 
	 public ResponseEntity<List<ComposterDailyModelCompostNew>> displayComposters(Long id)
	 {
		 List<ComposterDailyModelCompostNew> sms = new ArrayList<ComposterDailyModelCompostNew>();
		 if(id==null)
		 {
			 return new ResponseEntity<List<ComposterDailyModelCompostNew>>( sms, HttpStatus.BAD_REQUEST );
		 }
		 
		 try {
			sms=cdi.displayComposters(id);
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
	    
	 public ResponseEntity<Double> subCompostProduct(ComposterDailyModelCompost data, Long init_id,Long farmerId,Date searchDate)
	 {
		if (data == null)
	    {
	       return new ResponseEntity<Double>( 0.0, HttpStatus.BAD_REQUEST );
	    }
		Double prevCompostWeight=data.getCompostWeight();
		try {
			Double compostWeight=data.getCompostWeight();
		System.out.println("Weight Before Refresh: "+data.getCompostWeight());
		if(data.getCompostWeight()<0)
		{
			  return new ResponseEntity<Double>( 0.0, HttpStatus.BAD_REQUEST );
		}
		data = cbl.refreshCompostSubProduct( data, init_id );
		System.out.println("Weight After Refresh: "+data.getCompostWeight());
	    if (data.getCompostWeight() < 0)
	    {
	    	System.out.println("Here is BAD_REQUEST");
	        return new ResponseEntity<Double>( 0.0, HttpStatus.BAD_REQUEST );
	    }
	    
	    Integer result=cdi.subCompostProduct(data, init_id,searchDate);
	    Integer rs=cftdi.addComposterFarmerTransaction(
	    												init_id, 
	    												data.getId(),
	    												farmerId, 
	    												data.getDateAndTime(),
	    												data.getCategory(),
	    												data.getGrade(),
	    												data.getPrice(),
	    												compostWeight);
	    if(rs==0)
	    {
	    	return new ResponseEntity<Double>( 0.0, HttpStatus.BAD_REQUEST );
	    }
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
     	 
	 public ResponseEntity<List<ComposterFullSelect>> getComposterByDate(Date date)
	 {
		List<ComposterFullSelect> lc=new ArrayList<ComposterFullSelect>();
		if(date==null)
		{
			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.BAD_REQUEST );
		}
		try {
			DateTime currentDate=new DateTime(System.currentTimeMillis());
   		 	DateTime responseDate=new DateTime(date.getTime());
   		 if(currentDate.getYear()!=responseDate.getYear()&&currentDate.getMonthOfYear()!=responseDate.getMonthOfYear())
   		 {
   			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.BAD_REQUEST );
   		 }

			lc=cdi.getComposterByDate(date);
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
	 
	public ResponseEntity<Void> addComposterCompostImages(MultipartFile[] file,Long composterId, Long init_id, Timestamp date_time)
	{
		 String[] qualifiedPaths=null;
		 if(file.length==0||composterId==null||init_id==null||date_time==null)
		 {
			 return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
		 }
		 try {
			qualifiedPaths=cdi.storeFile(file, composterId, init_id, date_time);
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
				Integer rs=cdi.addComposterCompostImages(
														composterId,
														init_id, 
														date_time,
														CompostBusinessLogic.urlCreator("composterCompostImages/"+composterId+"/"+init_id+"/"+CompostBusinessLogic.replaceColonToPeriod(date_time),StringUtils.cleanPath(file[i].getOriginalFilename()) ));
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
	
	public ResponseEntity<List<ComposterCompostImageSelect>> selectComposterCompostImage(Long composterId, Long initId,Date dateToSearch)
	{
		List<ComposterCompostImageSelect> ll=new ArrayList<ComposterCompostImageSelect>();
		if(composterId==null||initId==null||dateToSearch==null)
		{
			return new ResponseEntity<List<ComposterCompostImageSelect>>(ll,HttpStatus.NOT_FOUND);
		}
		try {
			ll=cdi.selectComposterCompostImage(composterId, initId, dateToSearch);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<List<ComposterCompostImageSelect>>(ll,HttpStatus.NOT_FOUND);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<List<ComposterCompostImageSelect>>(ll,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(ll.isEmpty())
		{
			return new ResponseEntity<List<ComposterCompostImageSelect>>(ll,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ComposterCompostImageSelect>>(ll,HttpStatus.OK);
	
	}
	 
	public ResponseEntity<Void> deleteComposterCompostWasteImage(Long composterCompostWasteImage)
	{
		Integer rs=null;
		if(composterCompostWasteImage==null)
		{
			return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
		}
		try {
			rs=cdi.deleteComposterCompostWasteImage(composterCompostWasteImage);
		} catch (ClassNotFoundException e) {
			return new ResponseEntity<Void>( HttpStatus.NOT_FOUND );
		} catch (SQLException e) {
			return new ResponseEntity<Void>( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		if(rs==0)
		{
			return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
		}
		return new ResponseEntity<Void>( HttpStatus.OK );
	}
	
	 public ResponseEntity<List<ComposterFullSelect>> getComposterByDateAndState(Date date,String state)
	 {
		List<ComposterFullSelect> lc=new ArrayList<ComposterFullSelect>();
		if(date==null)
		{
			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.BAD_REQUEST );
		}
		try {
			DateTime currentDate=new DateTime(System.currentTimeMillis());
  		 	DateTime responseDate=new DateTime(date.getTime());
  		 if(currentDate.getYear()!=responseDate.getYear()&&currentDate.getMonthOfYear()!=responseDate.getMonthOfYear())
  		 {
  			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.BAD_REQUEST );
  		 }

			lc=cdi.getComposterByDateByState(date, state);
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
	 
	 public ResponseEntity<List<ComposterFullSelect>> getComposterByDateByCity(Date date,String city)
	 {
		List<ComposterFullSelect> lc=new ArrayList<ComposterFullSelect>();
		if(date==null)
		{
			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.BAD_REQUEST );
		}
		try {
			DateTime currentDate=new DateTime(System.currentTimeMillis());
   		 	DateTime responseDate=new DateTime(date.getTime());
   		 if(currentDate.getYear()!=responseDate.getYear()&&currentDate.getMonthOfYear()!=responseDate.getMonthOfYear())
   		 {
   			return new ResponseEntity<List<ComposterFullSelect>>( lc, HttpStatus.BAD_REQUEST );
   		 }

			lc=cdi.getComposterByDateByCity(date, city);
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