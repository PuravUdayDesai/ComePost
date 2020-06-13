package com.hack.comp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import com.hack.comp.connection.Connections;
import com.hack.comp.dao.schema.SupplierComposterTransactionDAO;
import com.hack.comp.model.supplierComposterTransaction.SupplierComposterTransactionSelectModel;

@Service
public class SupplierComposterTransactionDAOImpl implements SupplierComposterTransactionDAO
{

	@Override
	public Integer addSupplierComposterTransaction(Long supplier_waste_id, Long supplier_id, Long composter_id,Timestamp date_time) throws SQLException, ClassNotFoundException 
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
				"												date_time)\r\n" + 
				"	VALUES (\r\n" + 
				"		?, \r\n" + 
				"		?, \r\n" + 
				"		?, \r\n" + 
				"		(SELECT composter.composter_info.name FROM composter.composter_info WHERE composter.composter_info.id=?),\r\n" + 
				"		(SELECT composter.composter_info.email FROM composter.composter_info WHERE composter.composter_info.id=?),\r\n" + 
				"		(SELECT composter.composter_info.contact FROM composter.composter_info WHERE composter.composter_info.id=?),\r\n" + 
				"		?);");
		stmt.setLong(1, supplier_waste_id);
		stmt.setLong(2, supplier_id);
		stmt.setLong(3, composter_id);
		stmt.setLong(4, composter_id);
		stmt.setLong(5, composter_id);
		stmt.setLong(6, composter_id);
		stmt.setTimestamp(7, date_time);
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
				"	date_time\r\n" + 
				"	FROM public.supplier_composter_transaction\r\n" + 
				"	WHERE supplier_waste_init_id=?;");
		stmt.setLong(1, init_id);
		ResultSet rs=stmt.executeQuery();
		SupplierComposterTransactionSelectModel sc=new SupplierComposterTransactionSelectModel();
		if(rs.next())
		{
			System.out.println(rs.getLong("inc_id"));
			sc=new SupplierComposterTransactionSelectModel(
					rs.getLong("inc_id"),
					rs.getLong("supplier_waste_init_id"),
					rs.getLong("supplier_id"),
					rs.getLong("composter_id"),
					rs.getString("composter_name"),
					rs.getString("composter_emailid"),
					rs.getString("composter_contact"),
					rs.getTimestamp("date_time")
					);
		}
		rs.close();
		stmt.close();
		c.close();
		return sc;
	}

}
