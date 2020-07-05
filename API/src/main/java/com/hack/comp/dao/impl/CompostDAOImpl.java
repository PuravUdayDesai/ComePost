package com.hack.comp.dao.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hack.comp.aspect.SMSAspect;
import com.hack.comp.bl.CompostBusinessLogic;
import com.hack.comp.connection.Connections;
import com.hack.comp.dao.schema.CompostDAO;
import com.hack.comp.model.compost.CompostModelInsert;
import com.hack.comp.model.compost.ComposterCompostImageSelect;
import com.hack.comp.model.compost.ComposterDailyModelCompost;
import com.hack.comp.model.compost.ComposterDailyModelCompostNew;
import com.hack.comp.model.compost.ComposterFullSelect;
import com.hack.comp.model.compost.ComposterLoginModel;
import com.hack.comp.model.exception.FileStorageException;

@Service
public class CompostDAOImpl implements CompostDAO
{
	private final static String month[] = {
			"January", 
			"February", 
			"March", 
			"April", 
			"May", 
			"June", 
			"July", 
			"August", 
			"September", 
			"October", 
			"November",
			"December" 
		  }; 

	@Override
	public ComposterLoginModel validateComposter(String username, String password) throws SQLException, ClassNotFoundException 
	{
		Connection c = Connections.setConnection();
        String sql = "SELECT \r\n" + 
        		"		composter.composter_info.id,\r\n" + 
        		"		composter.composter_info.name,\r\n" + 
        		"		composter.composter_info.contact,\r\n" + 
        		"		composter.composter_info.email,\r\n" + 
        		"		composter.composter_info.reg_no,\r\n" + 
        		"		composter.composter_loc.latitude,\r\n" + 
        		"		composter.composter_loc.longitude,\r\n" + 
        		"		composter.composter_loc.state,\r\n" + 
        		"		composter.composter_loc.city,\r\n" + 
        		"		composter.composter_loc.area,\r\n" + 
        		"		composter.composter_loc.street\r\n" + 
        		"FROM composter.composter_info\r\n" + 
        		"JOIN composter.composter_loc\r\n" + 
        		"ON composter.composter_loc.id=composter.composter_info.id\r\n" + 
        		"JOIN composter.composter_login\r\n" + 
        		"ON composter.composter_login.id=composter.composter_info.id\r\n" + 
        		"WHERE composter.composter_login.username=?\r\n" + 
        		"AND composter.composter_login.password=?\r\n" + 
        		"AND composter.composter_login.delete_index=FALSE";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery( );
        ComposterLoginModel l = new ComposterLoginModel();
     
            if (rs.next())
            {
            	l.setId(rs.getLong("id"));
                l.setName(rs.getString("name"));
                l.setContact(rs.getString("contact"));
                l.setEmail(rs.getString("email"));
                l.setReg_no(rs.getString("reg_no"));
                l.setState(rs.getString("state"));
                l.setCity(rs.getString("city"));
                l.setArea(rs.getString("area"));
                l.setStreet(rs.getString("street"));
                l.setLatitude(rs.getString("latitude"));
                l.setLongitude(rs.getString("longitude"));
                l.setCheck(true);
            }
            else
            {
            	l.setCheck(false);
            }
        
        rs.close();
        stmt.close();
        c.close();
        return l;
	}

	@Override
	public Boolean addCompost(CompostModelInsert cmi) throws SQLException, ClassNotFoundException 
	{
		 Connection c=Connections.setConnection();
		 String sql="SELECT * FROM composter.\"fn_insertComposter\"(?,?,?,?,?,?,?,?,?,?,?);";
		 CallableStatement stmt=c.prepareCall(sql);
		 stmt.setString( 1, cmi.getName() );
	     stmt.setString( 2, cmi.getContactNumber() );
	     stmt.setString( 3, cmi.getEmailId() );
	     stmt.setString( 4, cmi.getRegistrationNumber() );
	     stmt.setString( 5, cmi.getLatitude() );
	     stmt.setString( 6, cmi.getLongitude() );
	     stmt.setString( 7, cmi.getState() );
	     stmt.setString( 8, cmi.getCity() );
	     stmt.setString( 9, cmi.getArea() );
	     stmt.setString( 10, cmi.getStreet() );
	     stmt.setString( 11, cmi.getPassword() );
	     ResultSet rs=stmt.executeQuery();
	     System.out.println(stmt);
	     c.commit();
	     Boolean rsMain=false;
	     if(rs.next())
	     {
	    	 rsMain=rs.getBoolean("fn_insertComposter");
	    	 if(rsMain)
	    	 {
	    		 Connection c2=Connections.setConnection();
	    		 PreparedStatement stmt2=c2.prepareStatement("SELECT\r\n" + 
	    		 		"	farmer.farmer_info.farmer_contact_number\r\n" + 
	    		 		"FROM\r\n" + 
	    		 		"	farmer.farmer_info\r\n" + 
	    		 		"JOIN\r\n" + 
	    		 		"	farmer.farmer_location\r\n" + 
	    		 		"ON\r\n" + 
	    		 		"	farmer.farmer_location.farmer_id=farmer.farmer_info.id\r\n" + 
	    		 		"WHERE\r\n" + 
	    		 		"	LOWER(farmer.farmer_location.state)=LOWER(?)");
	    		 stmt2.setString(1, cmi.getState());
	    		 System.out.println(stmt2);
	    		 ResultSet rs2=stmt2.executeQuery();
	    		 while(rs2.next())
	    		 {
	    			 System.out.println("FARMER CONTACT NUMBER: "+rs2.getString("farmer_contact_number"));
	    			 SMSAspect.sendSMS("A new composter has been added, you can contact him: "+cmi.getContactNumber()+" name: "+cmi.getName(),rs2.getString("farmer_contact_number"));
	    		 }
	    		 rs2.close();
	    		 stmt2.close();
	    		 c2.close();
	    		 
	    	 }
	     }
	     rs.close();
	     stmt.close();
	     c.close();
		return rsMain;
	}

	@Override
	public Long addCompostProduct(ComposterDailyModelCompost data) throws SQLException, ClassNotFoundException 
	{
		String query = "INSERT INTO composter.composter_compost(\r\n" + 
				"	id,\r\n" + 
				"	date_time,\r\n" + 
				"	compost_weight,\r\n" + 
				"	price, \r\n" + 
				"	add_or_sub,\r\n" + 
				"	category,\r\n" + 
				"	grade,\r\n" + 
				"	description, \r\n" + 
				"	entry_date) \r\n" + 
				"VALUES (?,?,?,?,?,?,?,?,?)RETURNING init_id;";
        Connection c = Connections.setConnection();
        CallableStatement stmt = c.prepareCall( query );
        stmt.setLong( 1, data.getId() );
        stmt.setTimestamp( 2, data.getDateAndTime() );
        stmt.setDouble( 3, data.getCompostWeight() );
        stmt.setDouble( 4, data.getPrice() );
        stmt.setBoolean( 5, true );
        stmt.setString(6, data.getCategory());
        stmt.setString(7, data.getGrade());
        stmt.setString(8, data.getDescription()==null?"":data.getDescription());
        Date date=new Date(data.getDateAndTime().getTime());
        stmt.setDate(9, date);
        ResultSet rs=stmt.executeQuery();
        c.commit();
        Long init_id=null;
        if(rs.next())
        {
        	init_id=rs.getLong("init_id");
        }
        rs.close();
        stmt.close();
        c.close();
        return init_id;
	}
	
	@Override
	public List<ComposterDailyModelCompostNew> displayComposters(Long id)throws SQLException, ClassNotFoundException
	{
		List<ComposterDailyModelCompostNew> sms = new ArrayList<ComposterDailyModelCompostNew>();
        String query = "SELECT * FROM composter.composter_compost WHERE id=? ORDER BY date_time DESC;";
        Connection c = Connections.setConnection();
        CallableStatement stmt = c.prepareCall( query );
        stmt.setLong( 1, id );
        ResultSet rs = stmt.executeQuery();
        while (rs.next())
        {
        	DateTime dt = new DateTime(rs.getTimestamp("date_time").getTime());
			SimpleDateFormat formatDate=new SimpleDateFormat("EEEE");
			Calendar gCal=new GregorianCalendar(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(),dt.getHourOfDay(),dt.getMinuteOfHour(),dt.getSecondOfMinute());
			String dateString=formatDate.format(
					rs.getTimestamp("date_time").getTime())+
					" "+
					dt.getDayOfMonth()+
					" "+
					month[gCal.get(Calendar.MONTH)-1]+
					" "+
					dt.getYear()+
					" "+
					dt.getHourOfDay()+
					":"+
					dt.getMinuteOfHour()+
					":"+
					dt.getSecondOfMinute();
            sms.add(
            		new ComposterDailyModelCompostNew( 
            				rs.getLong("inc_id"),
            				rs.getLong( "init_id" ),
            				rs.getTimestamp( "date_time" ),
            				rs.getDouble( "price" ),
            				rs.getDouble( "compost_weight" ), 
            				rs.getBoolean( "add_or_sub" ) ,
            				rs.getString("category"),
            				rs.getString("grade"),
            				rs.getString("description"),
            				dateString) 
            		);
        }
        rs.close();
        stmt.close();
        c.close();
		return sms;
	}

	@Override
	public Double refreshCompostSubProduct(ComposterDailyModelCompost data, Long init_id)throws SQLException, ClassNotFoundException 
	{
		String query = "SELECT compost_weight FROM composter.composter_compost WHERE id=? AND init_id=?  ORDER BY inc_id DESC LIMIT 1";
        Connection c = Connections.setConnection();
        CallableStatement stmt = c.prepareCall( query );
        stmt.setLong( 1, data.getId() );
        stmt.setLong( 2, init_id );
        Double compostWeight = 0.0;
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
        	compostWeight = rs.getDouble( "compost_weight" );
        }
        System.out.println("Last Weight Entry: "+compostWeight);
		return compostWeight;
	}

	@Override
	public Integer subCompostProduct(ComposterDailyModelCompost data, Long init_id,Date searchDate)throws SQLException, ClassNotFoundException 
	{
		String query = "INSERT INTO composter.composter_compost(\r\n" + 
				"	init_id,\r\n"+
				"	id,\r\n" + 
				"	date_time,\r\n" + 
				"	compost_weight,\r\n" + 
				"	price, \r\n" + 
				"	add_or_sub,\r\n" + 
				"	category,\r\n" + 
				"	grade,\r\n" + 
				"	description, \r\n" + 
				"	entry_date) \r\n" + 
				"VALUES (?,?,?,?,?,?,?,?,?,?)";
        Connection c = Connections.setConnection();
        CallableStatement stmt = c.prepareCall( query );
        stmt.setLong(1, init_id);
        stmt.setLong( 2, data.getId() );
        stmt.setTimestamp( 3, data.getDateAndTime() );
        stmt.setDouble( 4, data.getCompostWeight() );
        stmt.setDouble( 5, data.getPrice() );
        stmt.setBoolean( 6, false );
        stmt.setString(7, data.getCategory());
        stmt.setString(8, data.getGrade());
        stmt.setString(9, data.getDescription()==null?"":data.getDescription());
        stmt.setDate(10, searchDate);
        Integer result = stmt.executeUpdate();
        c.commit();
        stmt.close();
        c.close();
        return result;
	}

	@Override
	public List<ComposterFullSelect> getComposterByDate(Date date) throws SQLException, ClassNotFoundException 
	{
		Connection c = Connections.setConnection();
        PreparedStatement stmt = c.prepareStatement("SELECT composter.composter_info.name,\n" + 
        											"composter.composter_compost.init_id,\n" + 
        											"composter.composter_info.id,\n" + 
        											"composter.composter_info.contact,\n" + 
        											"composter.composter_info.email,\n" + 
        											"composter.composter_info.reg_no,\n" + 
        											"composter.composter_compost.add_or_sub,\n" +
        											"composter.composter_compost.price,\n" + 
        											"composter.composter_compost.compost_weight,\n" +
        											"composter.composter_compost.date_time,\n" + 
        											"composter.composter_loc.latitude,\n" + 
        											"composter.composter_loc.longitude,\n" +
        											"composter.composter_loc.street,\n" +
        											"composter.composter_loc.area,\n" + 
        											"composter.composter_loc.city,\n" + 
        											"composter.composter_loc.state\n" + 
        											"FROM composter.composter_info\n" + 
        											"JOIN composter.composter_compost "+
        												"ON composter.composter_info.id=composter.composter_compost.id\n" + 
        											"JOIN composter.composter_loc "+
        												"ON composter.composter_info.id=composter_loc.id "+
        											"WHERE composter.composter_compost.compost_weight <> 0 "+
        											" AND date(composter.composter_compost.entry_date)=? " +
        											" ORDER BY composter.composter_compost.inc_id DESC " );
        stmt.setDate( 1, date );
        ResultSet rs = stmt.executeQuery();
        List<ComposterFullSelect> lc = new ArrayList<ComposterFullSelect>();
        List<Long> l=new ArrayList<Long>();
        Integer i=0;
        while (rs.next())
        {
        	ListIterator<Long> li=l.listIterator();
        	Boolean rsMain=false;
        	if(i==0)
        	{
        		li.add(rs.getLong("init_id"));
        		rsMain=false;
        	}
        	while(li.hasNext())
        	{
        		Long init_id=li.next();
        		if(init_id==rs.getLong("init_id"))
        		{
        			rsMain=true;
        			break;
        		}
        		else
        		{
        			li.add(rs.getLong("init_id"));
        			rsMain=false;
        		}
        	}
        	if(!rsMain)
        	{
            	DateTime dt = new DateTime(rs.getTimestamp("date_time").getTime());
    			SimpleDateFormat formatDate=new SimpleDateFormat("EEEE");
    			Calendar gCal=new GregorianCalendar(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(),dt.getHourOfDay(),dt.getMinuteOfHour(),dt.getSecondOfMinute());
    			String dateString=formatDate.format(
    					rs.getTimestamp("date_time").getTime())+
    					" "+
    					dt.getDayOfMonth()+
    					" "+
    					month[gCal.get(Calendar.MONTH)-1]+
    					" "+
    					dt.getYear()+
    					" "+
    					dt.getHourOfDay()+
    					":"+
    					dt.getMinuteOfHour()+
    					":"+
    					dt.getSecondOfMinute();
                lc.add(
                		new ComposterFullSelect(
                				rs.getLong( "id" ),
                				rs.getLong( "init_id" ), 
                				rs.getString( "name" ), 
                				rs.getString( "contact" ),
                				rs.getString( "email" ), 
                				rs.getString( "reg_no" ), 
                				rs.getBoolean( "add_or_sub" ), 
                				rs.getDouble( "price" ), 
                				rs.getDouble( "compost_weight" ),
                				rs.getTimestamp( "date_time" ),
                				rs.getString( "latitude" ),
                				rs.getString( "longitude" ), 
                				rs.getString( "street" ),
                				rs.getString( "area" ), 
                				rs.getString( "city" ),
                				rs.getString( "state" ) ,
                				dateString
                				) 
                		);
        	}
                i++;
        }
        rs.close();
        stmt.close();
        c.close();
        return lc;
	}

	@Override
	public Integer addComposterCompostImages(Long composterId, Long init_id, Timestamp date_time,String image_url)throws SQLException, ClassNotFoundException
	{
		Connection c=Connections.setConnection();
		PreparedStatement stmt=c.prepareCall("INSERT INTO composter.composter_compost_image(\r\n" + 
				"	composter_id,\r\n" + 
				"	date_time, \r\n" + 
				"	image_url,\r\n" + 
				"	composter_init_id)\r\n" + 
				"	VALUES (?, ?, ?, ?);");
		stmt.setLong(1, composterId);
		stmt.setTimestamp(2, date_time);
		stmt.setString(3, image_url);
		stmt.setLong(4, init_id);
		Integer rs=stmt.executeUpdate();
		c.commit();
		stmt.close();
		c.close();
		return rs;
	}
	
	@Override
	public String[] storeFile(MultipartFile[] file,Long composterId,Long initId,Timestamp timeOfEntry) throws IOException 
	{
		String[] qualifiedPaths=new String[file.length];
		for(int i=0;i<file.length;i++)
		{
		String fileName = StringUtils.cleanPath(file[i].getOriginalFilename());

        if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        String path="C:/composterCompostImages/"+composterId+"/"+initId+"/"+CompostBusinessLogic.replaceColonToPeriod(timeOfEntry);
        File f=new File(path);
        if(!f.exists())
        {
        Boolean b=f.mkdirs();
        if(!b)
        {
        	throw new IOException("Cannot Create Director Specified: "+path);
        }
        }
        String qualifiedPath=path+"/"+fileName;
        Path targetLocation = Paths.get(qualifiedPath);
        Files.copy(file[i].getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        qualifiedPaths[i]=qualifiedPath;
	}
		return qualifiedPaths;
	}

	@Override
	public List<ComposterCompostImageSelect> selectComposterCompostImage(Long composterId, Long initId,Date dateToSearch) throws SQLException, ClassNotFoundException 
	{
		Connection c=Connections.setConnection();
		PreparedStatement stmt=c.prepareStatement(
				"SELECT \r\n" + 
				"		composter.composter_compost_image.composter_compost_image_id,\r\n" + 
				"		composter.composter_compost_image.composter_id,\r\n" + 
				"		composter.composter_compost_image.composter_init_id,\r\n" + 
				"		composter.composter_compost_image.date_time,\r\n" + 
				"		composter.composter_compost_image.image_url\r\n" + 
				"FROM\r\n" + 
				"	composter.composter_compost_image\r\n" + 
				"WHERE \r\n" + 
				"	composter.composter_compost_image.composter_id=?\r\n" + 
				"AND	\r\n" + 
				"	composter.composter_compost_image.composter_init_id=?\r\n" + 
				"AND \r\n" + 
				"	DATE(composter.composter_compost_image.date_time)=?\r\n" + 
				"AND\r\n" + 
				"	delete_index=FALSE;");
		stmt.setLong(1, composterId);
		stmt.setLong(2, initId);
		stmt.setDate(3, dateToSearch);
		ResultSet rs=stmt.executeQuery();
		List<ComposterCompostImageSelect> ll=new ArrayList<ComposterCompostImageSelect>();
		while(rs.next())
		{
			DateTime dt = new DateTime(rs.getTimestamp("date_time").getTime());
			SimpleDateFormat formatDate=new SimpleDateFormat("EEEE");
			Calendar gCal=new GregorianCalendar(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(),dt.getHourOfDay(),dt.getMinuteOfHour(),dt.getSecondOfMinute());
			String dateString=formatDate.format(
					rs.getTimestamp("date_time").getTime())+
					" "+
					dt.getDayOfMonth()+
					" "+
					month[gCal.get(Calendar.MONTH)-1]+
					" "+
					dt.getYear()+
					" "+
					dt.getHourOfDay()+
					":"+
					dt.getMinuteOfHour()+
					":"+
					dt.getSecondOfMinute();
			ll.add(new  ComposterCompostImageSelect(
					rs.getLong("composter_compost_image_id"),
					rs.getLong("composter_init_id"),
					rs.getLong("composter_id"),
					rs.getTimestamp("date_time"),
					rs.getString("image_url"),
					dateString
					));
		}
		rs.close();
		stmt.close();
		c.close();
		return ll;
	}

	@Override
	public Integer deleteComposterCompostWasteImage(Long composterCompostWasteImage)throws SQLException, ClassNotFoundException
	{
		Connection c=Connections.setConnection();
		PreparedStatement stmt=c.prepareStatement("UPDATE composter.composter_compost_image SET delete_index=TRUE WHERE composter.composter_compost_image.composter_compost_image_id=?");
		stmt.setLong(1, composterCompostWasteImage);
		Integer rs=stmt.executeUpdate();
		c.commit();
		stmt.close();
		c.close();
		return rs;
	}

	@Override
	public List<ComposterFullSelect> getComposterByDateByState(Date date, String state)throws SQLException, ClassNotFoundException 
	{
		Connection c = Connections.setConnection();
        PreparedStatement stmt = c.prepareStatement( "SELECT composter.composter_info.name,\n" + 
        											"composter.composter_compost.init_id,\n" + 
        											"composter.composter_info.id,\n" + 
        											"composter.composter_info.contact,\n" + 
        											"composter.composter_info.email,\n" + 
        											"composter.composter_info.reg_no,\n" + 
        											"composter.composter_compost.add_or_sub,\n" +
        											"composter.composter_compost.price,\n" + 
        											"composter.composter_compost.compost_weight,\n" +
        											"composter.composter_compost.date_time,\n" + 
        											"composter.composter_loc.latitude,\n" + 
        											"composter.composter_loc.longitude,\n" +
        											"composter.composter_loc.street,\n" +
        											"composter.composter_loc.area,\n" + 
        											"composter.composter_loc.city,\n" + 
        											"composter.composter_loc.state\n" + 
        											"FROM composter.composter_info\n" + 
        											"JOIN composter.composter_compost "+
        												"ON composter.composter_info.id=composter.composter_compost.id\n" + 
        											"JOIN composter.composter_loc "+
        												"ON composter.composter_info.id=composter_loc.id "+
        											"WHERE composter.composter_compost.compost_weight <> 0 "+
        											" AND date(composter.composter_compost.date_time)=? "+
        											" AND LOWER(composter.composter_loc.state)=LOWER(?) " );
        stmt.setDate( 1, date );
        stmt.setString(2, state);
        ResultSet rs = stmt.executeQuery();
        List<ComposterFullSelect> lc = new ArrayList<ComposterFullSelect>();
        List<Long> l=new ArrayList<Long>();
        Integer i=0;
        while (rs.next())
        {
        	ListIterator<Long> li=l.listIterator();
        	Boolean rsMain=false;
        	if(i==0)
        	{
        		System.out.println(rs.getLong("init_id"));
        		li.add(rs.getLong("init_id"));
        		rsMain=false;
        	}
        	while(li.hasNext())
        	{
        		Long init_id=li.next();
        		System.out.println("Check: "+(init_id==rs.getLong("init_id")));
        		if(init_id==rs.getLong("init_id"))
        		{
        			rsMain=true;
        			break;
        		}
        		else
        		{
        			li.add(rs.getLong("init_id"));
        			rsMain=false;
        		}
        	}
        	if(!rsMain)
        	{
    			DateTime dt = new DateTime(rs.getTimestamp("date_time").getTime());
    			SimpleDateFormat formatDate=new SimpleDateFormat("EEEE");
    			Calendar gCal=new GregorianCalendar(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(),dt.getHourOfDay(),dt.getMinuteOfHour(),dt.getSecondOfMinute());
    			String dateString=formatDate.format(
    					rs.getTimestamp("date_time").getTime())+
    					" "+
    					dt.getDayOfMonth()+
    					" "+
    					month[gCal.get(Calendar.MONTH)-1]+
    					" "+
    					dt.getYear()+
    					" "+
    					dt.getHourOfDay()+
    					":"+
    					dt.getMinuteOfHour()+
    					":"+
    					dt.getSecondOfMinute();
                lc.add(
                		new ComposterFullSelect(
                				rs.getLong( "id" ),
                				rs.getLong( "init_id" ), 
                				rs.getString( "name" ), 
                				rs.getString( "contact" ),
                				rs.getString( "email" ), 
                				rs.getString( "reg_no" ), 
                				rs.getBoolean( "add_or_sub" ), 
                				rs.getDouble( "price" ), 
                				rs.getDouble( "compost_weight" ),
                				rs.getTimestamp( "date_time" ),
                				rs.getString( "latitude" ),
                				rs.getString( "longitude" ), 
                				rs.getString( "street" ),
                				rs.getString( "area" ), 
                				rs.getString( "city" ),
                				rs.getString( "state" ),
                				dateString
                				) 
                		);
        	}
                i++;
        }
        rs.close();
        stmt.close();
        c.close();
        return lc;
	}

	@Override
	public List<ComposterFullSelect> getComposterByDateByCity(Date date, String city)throws SQLException, ClassNotFoundException
	{
		Connection c = Connections.setConnection();
        PreparedStatement stmt = c.prepareStatement( "SELECT composter.composter_info.name,\n" + 
        											"composter.composter_compost.init_id,\n" + 
        											"composter.composter_info.id,\n" + 
        											"composter.composter_info.contact,\n" + 
        											"composter.composter_info.email,\n" + 
        											"composter.composter_info.reg_no,\n" + 
        											"composter.composter_compost.add_or_sub,\n" +
        											"composter.composter_compost.price,\n" + 
        											"composter.composter_compost.compost_weight,\n" +
        											"composter.composter_compost.date_time,\n" + 
        											"composter.composter_loc.latitude,\n" + 
        											"composter.composter_loc.longitude,\n" +
        											"composter.composter_loc.street,\n" +
        											"composter.composter_loc.area,\n" + 
        											"composter.composter_loc.city,\n" + 
        											"composter.composter_loc.state\n" + 
        											"FROM composter.composter_info\n" + 
        											"JOIN composter.composter_compost "+
        												"ON composter.composter_info.id=composter.composter_compost.id\n" + 
        											"JOIN composter.composter_loc "+
        												"ON composter.composter_info.id=composter_loc.id "+
        											"WHERE composter.composter_compost.compost_weight <> 0 "+
        											" AND date(composter.composter_compost.date_time)=? "+
        											" AND LOWER(composter.composter_loc.city)=LOWER(?) ");
        stmt.setDate( 1, date );
        stmt.setString(2, city);
        ResultSet rs = stmt.executeQuery();
        List<ComposterFullSelect> lc = new ArrayList<ComposterFullSelect>();
        List<Long> l=new ArrayList<Long>();
        Integer i=0;
        while (rs.next())
        {
        	ListIterator<Long> li=l.listIterator();
        	Boolean rsMain=false;
        	if(i==0)
        	{
        		li.add(rs.getLong("init_id"));
        		rsMain=false;
        	}
        	while(li.hasNext())
        	{
        		Long init_id=li.next();
        		if(init_id==rs.getLong("init_id"))
        		{
        			rsMain=true;
        			break;
        		}
        		else
        		{
        			li.add(rs.getLong("init_id"));
        			rsMain=false;
        		}
        	}
        	if(!rsMain)
        	{
    			DateTime dt = new DateTime(rs.getTimestamp("date_time").getTime());
    			SimpleDateFormat formatDate=new SimpleDateFormat("EEEE");
    			Calendar gCal=new GregorianCalendar(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(),dt.getHourOfDay(),dt.getMinuteOfHour(),dt.getSecondOfMinute());
    			String dateString=formatDate.format(
    					rs.getTimestamp("date_time").getTime())+
    					" "+
    					dt.getDayOfMonth()+
    					" "+
    					month[gCal.get(Calendar.MONTH)-1]+
    					" "+
    					dt.getYear()+
    					" "+
    					dt.getHourOfDay()+
    					":"+
    					dt.getMinuteOfHour()+
    					":"+
    					dt.getSecondOfMinute();
                lc.add(
                		new ComposterFullSelect(
                				rs.getLong( "id" ),
                				rs.getLong( "init_id" ), 
                				rs.getString( "name" ), 
                				rs.getString( "contact" ),
                				rs.getString( "email" ), 
                				rs.getString( "reg_no" ), 
                				rs.getBoolean( "add_or_sub" ), 
                				rs.getDouble( "price" ), 
                				rs.getDouble( "compost_weight" ),
                				rs.getTimestamp( "date_time" ),
                				rs.getString( "latitude" ),
                				rs.getString( "longitude" ), 
                				rs.getString( "street" ),
                				rs.getString( "area" ), 
                				rs.getString( "city" ),
                				rs.getString( "state" ),
                				dateString
                				) 
                		);
        	}
                i++;
        }
        rs.close();
        stmt.close();
        c.close();
        return lc;
	}


	

}
