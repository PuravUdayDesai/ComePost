package com.hack.comp.dao.impl;

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

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.hack.comp.connection.Connections;
import com.hack.comp.dao.schema.SupplierComposterTransactionDAO;
import com.hack.comp.model.supplierComposterTransaction.SupplierComposterTransactionSelectModel;

@Service
public class SupplierComposterTransactionDAOImpl implements SupplierComposterTransactionDAO
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
	public Integer addSupplierComposterTransaction(
													Long supplier_waste_id, 
													Long supplier_id,
													Long composter_id,
													Timestamp date_time,
													Double dryWaste,
													Double wetWaste) throws SQLException, ClassNotFoundException 
	{
		Connection c=Connections.setConnection();
		PreparedStatement stmt=c.prepareCall(
				"INSERT INTO public.supplier_composter_transaction(\r\n" + 
				"												supplier_waste_init_id, \r\n" + 
				"												supplier_id, \r\n" + 
				"												composter_id,\r\n" + 
				"												composter_name,\r\n" + 
				"												composter_emailid, \r\n" + 
				"												composter_contact,\r\n" + 
				"												date_time,\r\n" + 
				"												dry_waste,\r\n" + 
				"												wet_waste)\r\n"+
				"	VALUES (\r\n" + 
				"		?, \r\n" + 
				"		?, \r\n" + 
				"		?, \r\n" + 
				"		(SELECT composter.composter_info.name FROM composter.composter_info WHERE composter.composter_info.id=?),\r\n" + 
				"		(SELECT composter.composter_info.email FROM composter.composter_info WHERE composter.composter_info.id=?),\r\n" + 
				"		(SELECT composter.composter_info.contact FROM composter.composter_info WHERE composter.composter_info.id=?),\r\n" + 
				"		?, \r\n"+
				"		?, \r\n"+
				"		?);");
		stmt.setLong(1, supplier_waste_id);
		stmt.setLong(2, supplier_id);
		stmt.setLong(3, composter_id);
		stmt.setLong(4, composter_id);
		stmt.setLong(5, composter_id);
		stmt.setLong(6, composter_id);
		stmt.setTimestamp(7, date_time);
		stmt.setDouble(8, dryWaste);
		stmt.setDouble(9, wetWaste);
		Integer rs=stmt.executeUpdate();
		c.commit();
		
		stmt.close();
		c.close();
		return rs;
	}

	@Override
	public SupplierComposterTransactionSelectModel selectSupplierComposterTrasnsaction(Long init_id)throws SQLException, ClassNotFoundException
	{
		Connection c=Connections.setConnection();
		PreparedStatement stmt=c.prepareStatement("SELECT \r\n" + 
				"	inc_id, \r\n" + 
				"	supplier_waste_init_id, \r\n" + 
				"	supplier_id,\r\n" + 
				"	composter_id, \r\n" + 
				"	composter_name, \r\n" + 
				"	composter_emailid, \r\n" + 
				"	composter_contact,\r\n" + 
				"	date_time,\r\n" + 
				"	dry_waste,\r\n" + 
				"	wet_waste\r\n" + 
				"	FROM public.supplier_composter_transaction\r\n" + 
				"	WHERE supplier_waste_init_id=?;");
		stmt.setLong(1, init_id);
		ResultSet rs=stmt.executeQuery();
		SupplierComposterTransactionSelectModel sc=new SupplierComposterTransactionSelectModel();
		if(rs.next())
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
			sc=new SupplierComposterTransactionSelectModel(
					rs.getLong("inc_id"),
					rs.getLong("supplier_waste_init_id"),
					rs.getLong("supplier_id"),
					rs.getLong("composter_id"),
					rs.getString("composter_name"),
					rs.getString("composter_emailid"),
					rs.getString("composter_contact"),
					rs.getTimestamp("date_time"),
					dateString,
					rs.getDouble("dry_waste"),
					rs.getDouble("wet_waste")
					);
		}
		rs.close();
		stmt.close();
		c.close();
		return sc;
	}

	public List<SupplierComposterTransactionSelectModel>	selectSupplierComposterTransactionByDate(Long supplierId,Date dateToSearch)throws SQLException,ClassNotFoundException
	{
		Connection c=Connections.setConnection();
		PreparedStatement stmt=c.prepareStatement("SELECT \r\n" + 
				"	inc_id, \r\n" + 
				"	supplier_waste_init_id, \r\n" + 
				"	supplier_id,\r\n" + 
				"	composter_id, \r\n" + 
				"	composter_name, \r\n" + 
				"	composter_emailid, \r\n" + 
				"	composter_contact,\r\n" + 
				"	date_time,\r\n" + 
				"	dry_waste,\r\n" + 
				"	wet_waste\r\n" + 
				"	FROM public.supplier_composter_transaction\r\n" + 
				"	WHERE supplier_id=?\r\n"+
				"	AND DATE(date_time)=?;");
		stmt.setLong(1, supplierId);
		stmt.setDate(2, dateToSearch);
		ResultSet rs=stmt.executeQuery();
		System.out.println(stmt);
		List<SupplierComposterTransactionSelectModel> sc=new ArrayList<SupplierComposterTransactionSelectModel>();
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
			sc.add(new SupplierComposterTransactionSelectModel(
					rs.getLong("inc_id"),
					rs.getLong("supplier_waste_init_id"),
					rs.getLong("supplier_id"),
					rs.getLong("composter_id"),
					rs.getString("composter_name"),
					rs.getString("composter_emailid"),
					rs.getString("composter_contact"),
					rs.getTimestamp("date_time"),
					dateString,
					rs.getDouble("dry_waste"),
					rs.getDouble("wet_waste")
					));
		}
		rs.close();
		stmt.close();
		c.close();
		return sc;
	}
	public List<SupplierComposterTransactionSelectModel> selectSupplierComposterTransactionBySupplierId(Long supplierId)throws SQLException,ClassNotFoundException
	{
		Connection c=Connections.setConnection();
		PreparedStatement stmt=c.prepareStatement("SELECT \r\n" + 
				"	inc_id, \r\n" + 
				"	supplier_waste_init_id, \r\n" + 
				"	supplier_id,\r\n" + 
				"	composter_id, \r\n" + 
				"	composter_name, \r\n" + 
				"	composter_emailid, \r\n" + 
				"	composter_contact,\r\n" + 
				"	date_time,\r\n" + 
				"	dry_waste,\r\n" + 
				"	wet_waste\r\n" + 
				"	FROM public.supplier_composter_transaction\r\n" + 
				"	WHERE supplier_id=?;");
		stmt.setLong(1, supplierId);
		ResultSet rs=stmt.executeQuery();
		System.out.println(stmt);
		List<SupplierComposterTransactionSelectModel> sc=new ArrayList<SupplierComposterTransactionSelectModel>();
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
			sc.add(new SupplierComposterTransactionSelectModel(
					rs.getLong("inc_id"),
					rs.getLong("supplier_waste_init_id"),
					rs.getLong("supplier_id"),
					rs.getLong("composter_id"),
					rs.getString("composter_name"),
					rs.getString("composter_emailid"),
					rs.getString("composter_contact"),
					rs.getTimestamp("date_time"),
					dateString,
					rs.getDouble("dry_waste"),
					rs.getDouble("wet_waste")
					));
		}
		rs.close();
		stmt.close();
		c.close();
		return sc;
	}

	
}
