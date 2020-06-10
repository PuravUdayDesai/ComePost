package com.hack.comp.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.stereotype.Service;

import com.hack.comp.connection.Connections;
import com.hack.comp.dao.schema.CompostDAO;
import com.hack.comp.model.compost.CompostAndSupplierModel;
import com.hack.comp.model.compost.CompostModelInsert;
import com.hack.comp.model.compost.ComposterDailyModelCompost;
import com.hack.comp.model.compost.ComposterDailyModelCompostNew;
import com.hack.comp.model.compost.ComposterFullSelect;
import com.hack.comp.model.compost.ComposterLoginModel;

@Service
public class CompostDAOImpl implements CompostDAO
{

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
	     stmt.setString( 7, cmi.getStreet() );
	     stmt.setString( 8, cmi.getArea() );
	     stmt.setString( 9, cmi.getCity() );
	     stmt.setString( 10, cmi.getState() );
	     stmt.setString( 11, cmi.getPassword() );
	     ResultSet rs=stmt.executeQuery();
	     c.commit();
	     Boolean rsMain=false;
	     if(rs.next())
	     {
	    	 rsMain=rs.getBoolean("fn_insertComposter");
	    	 System.out.println(rsMain);
	     }
	     rs.close();
	     stmt.close();
	     c.close();
		return rsMain;
	}

	@Override
	public Integer compAndSuppTran(Long composter_id, CompostAndSupplierModel casm)throws SQLException, ClassNotFoundException 
	{
		Connection c = Connections.setConnection();
        String sql = "INSERT INTO public.comp_supp_transaction(supplier_id, composter_id, date, wet_waste, dry_waste) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement stmt = c.prepareStatement( sql );
        stmt.setLong( 1, casm.getSupplier_id() );
        stmt.setLong( 2, casm.getComposter_id() );
        stmt.setDate( 3, casm.getDate() );
        stmt.setDouble( 4, casm.getWet_waste() );
        stmt.setDouble( 5, casm.getDry_waste() );
        int result = stmt.executeUpdate();
        c.commit();
        stmt.close();
        c.close();
		return result;
	}

	@Override
	public Integer addCompostProduct(ComposterDailyModelCompost data) throws SQLException, ClassNotFoundException 
	{
		String query = "INSERT INTO composter.composter_compost(id, date_time, compost_weight, price, add_or_sub) VALUES (?,?,?,?,?)";
        Connection c = Connections.setConnection();
        CallableStatement stmt = c.prepareCall( query );
        stmt.setLong( 1, data.getId() );
        stmt.setTimestamp( 2, data.getDateAndTime() );
        stmt.setDouble( 3, data.getCompostWeight() );
        stmt.setDouble( 4, data.getPrice() );
        stmt.setBoolean( 5, true );
        Integer result = stmt.executeUpdate();
        c.commit();
        stmt.close();
        c.close();
        return result;
	}
	
	@Override
	public Double refreshCompostSubProduct(ComposterDailyModelCompost data, Long init_id)throws SQLException, ClassNotFoundException 
	{
		String query = "SELECT compost_weight FROM composter.composter_compost WHERE id=? AND date(date_time)=? AND init_id=?  ORDER BY date_time DESC LIMIT 1";
        Connection c = Connections.setConnection();
        CallableStatement stmt = c.prepareCall( query );
        stmt.setLong( 1, data.getId() );
        stmt.setTimestamp( 2, data.getDateAndTime() );
        stmt.setLong( 3, init_id );
        Double compostWeight = 0.0;
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
        	compostWeight = rs.getDouble( "compost_weight" );
        }
		return compostWeight;
	}

	@Override
	public Integer subCompostProduct(ComposterDailyModelCompost data, Long init_id)throws SQLException, ClassNotFoundException 
	{
		 String query = "INSERT INTO composter.composter_compost(id, date_time, compost_weight, price, add_or_sub) VALUES (?,?,?,?,?)";
	     Connection c = Connections.setConnection();
	     CallableStatement stmt = c.prepareCall( query );
	     stmt.setInt( 1, data.getId() );
	     stmt.setTimestamp( 2, data.getDateAndTime() );
	     stmt.setDouble( 3, data.getCompostWeight() );
	     stmt.setDouble( 4, data.getPrice() );
	     stmt.setBoolean( 5, false );
	     Integer result = stmt.executeUpdate();
	     c.commit();
	     stmt.close();
	     c.close();
		return result;
	}

	@Override
	public List<ComposterDailyModelCompostNew> displaySuppliers(Long id)throws SQLException, ClassNotFoundException
	{
		List<ComposterDailyModelCompostNew> sms = new ArrayList<ComposterDailyModelCompostNew>();
        String query = "SELECT * FROM composter.composter_compost WHERE id=?;";
        Connection c = Connections.setConnection();
        CallableStatement stmt = c.prepareCall( query );
        stmt.setLong( 1, id );
        ResultSet rs = stmt.executeQuery();
        while (rs.next())
        {
            sms.add(
            		new ComposterDailyModelCompostNew( 
            				rs.getInt( "init_id" ),
            				rs.getTimestamp( "date_time" ),
            				rs.getDouble( "price" ),
            				rs.getDouble( "compost_weight" ), 
            				rs.getBoolean( "add_or_sub" ) ) 
            		);
        }
        rs.close();
        stmt.close();
        c.close();
		return sms;
	}

	@Override
	public List<ComposterFullSelect> getSupplierByDate(Date date) throws SQLException, ClassNotFoundException 
	{
		Connection c = Connections.setConnection();
        PreparedStatement stmt = c.prepareStatement( "SELECT composter.composter_info.name,\n" + 
        											"composter.composter_compost.init_id,\n" + 
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
        											"ORDER BY composter.composter_compost.date_time DESC;" );
        stmt.setDate( 1, date );
        ResultSet rs = stmt.executeQuery();
        List<ComposterFullSelect> lc = new ArrayList<ComposterFullSelect>();

        List<Double> price = new ArrayList<Double>();

        while (rs.next())
        {
            ListIterator<Double> iterator = price.listIterator();
            Boolean isThere = true;
            while (iterator.hasNext())
            {
                if (iterator.next() == rs.getDouble( "price" ))
                {
                    isThere = false;
                    break;
                }
            }

            if (isThere)
            {
                lc.add(
                		new ComposterFullSelect(
                				rs.getInt( "init_id" ), 
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
                				rs.getString( "state" ) 
                				) 
                		);
                price.add( rs.getDouble( "price" ) );
            }

        }
        rs.close();
        stmt.close();
        c.close();
        return lc;
	}

}
