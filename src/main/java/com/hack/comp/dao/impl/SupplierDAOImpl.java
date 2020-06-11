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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hack.comp.bl.SupplierBusinessLogic;
import com.hack.comp.connection.Connections;
import com.hack.comp.dao.schema.SupplierDAO;
import com.hack.comp.model.exception.FileStorageException;
import com.hack.comp.model.supplier.SupplierModelDailyWaste;
import com.hack.comp.model.supplier.SupplierModelDailyWasteNew;
import com.hack.comp.model.supplier.SupplierModelFullSelect;
import com.hack.comp.model.supplier.SupplierModelInsert;
import com.hack.comp.model.supplier.SupplierModelSelect;
import com.hack.comp.model.supplier.SupplierWasteImagesSelect;

@Service
public class SupplierDAOImpl implements SupplierDAO
{
	@Override
	public Boolean addSupplier(SupplierModelInsert smi) throws SQLException, ClassNotFoundException 
	{
		 String query = "SELECT supplier.\"fn_addSupplier\"(\r\n" + "	?, \r\n" + //<name_in text>
	                "	?, \r\n" + //<contact_number_in text>
	                "	?, \r\n" + //<email_id_in text>
	                "	?, \r\n" + //<reg_no_in text>
	                "	?, \r\n" + //<latitude_in text>
	                "	?, \r\n" + //<longitude_in text>
	                "	?, \r\n" + //<state_in text>
	                "	?, \r\n" + //<city_in text>
	                "	?, \r\n" + //<area_in text>
	                "	?, \r\n" + //<street_in text>
	                "	?\r\n" + //<password_in text>
	                ")";
	        Connection c = Connections.setConnection();
	        CallableStatement stmt = c.prepareCall( query );
	        stmt.setString( 1, smi.getName() );
	        stmt.setString( 2, smi.getContactNumber() );
	        stmt.setString( 3, smi.getEmailId() );
	        stmt.setString( 4, smi.getRegistrationNumber() );
	        stmt.setString( 5, smi.getLatitude() );
	        stmt.setString( 6, smi.getLongitude() );
	        stmt.setString( 7, smi.getState() );
	        stmt.setString( 8, smi.getCity() );
	        stmt.setString( 9, smi.getArea() );
	        stmt.setString( 10, smi.getStreet() );
	        stmt.setString( 11, smi.getPassword() );
	        Boolean result = stmt.execute();
	        c.commit();
	        stmt.close();
	        c.close();
		return result;
	}

	@Override
	public SupplierModelDailyWaste refreshSupplierAddProduct(SupplierModelDailyWaste data)throws SQLException, ClassNotFoundException 
	{
		String query = "SELECT * FROM supplier.supplier_waste WHERE id=? AND date(date_time)=?  AND \"deleteIndex\"=false ORDER BY date_time DESC LIMIT 1";
        Connection c = Connections.setConnection();
        CallableStatement stmt = c.prepareCall( query );
        stmt.setInt( 1, data.getId() );
        stmt.setTimestamp( 2, data.getDate() );
        Double dryWaste = 0.0;
        Double wetWaste = 0.0;
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next())
        {
            dryWaste = rs.getDouble( "dry_waste" );
            wetWaste = rs.getDouble( "wet_waste" );
        }

        data.setDryWaste( data.getDryWaste() + dryWaste );
        data.setWetWaste( data.getWetWaste() + wetWaste );
        c.commit();
        rs.close();
        stmt.close();
        c.close();
        return data;
	}

	@Override
	public Integer addSupplierProduct(SupplierModelDailyWaste data) throws SQLException, ClassNotFoundException 
	{
		String query = "INSERT INTO supplier.supplier_waste (id,date_time,dry_waste,wet_waste,description) VALUES (?,?,?,?,?)";
        Connection c = Connections.setConnection();
        CallableStatement stmt = c.prepareCall( query );
        stmt.setInt( 1, data.getId() );
        stmt.setTimestamp( 2, data.getDate() );
        stmt.setDouble( 3, data.getDryWaste() );
        stmt.setDouble( 4, data.getWetWaste() );
        stmt.setString(5, data.getDescription());
        Integer result = stmt.executeUpdate();
        c.commit();
        stmt.close();
        c.close();
        return result;
	}

	@Override
	public SupplierModelDailyWaste refreshSupplierSubProduct(SupplierModelDailyWaste data)throws SQLException, ClassNotFoundException 
	{
        String query = "SELECT * FROM supplier.supplier_waste WHERE id=? AND date(date_time)=? AND \"deleteIndex\"=false ORDER BY date_time DESC LIMIT 1";
        Connection c = Connections.setConnection();
        CallableStatement stmt = c.prepareCall( query );
        stmt.setInt( 1, data.getId() );
        stmt.setTimestamp( 2, data.getDate() );
        Double dryWaste = 0.0;
        Double wetWaste = 0.0;
        ResultSet rs = stmt.executeQuery();
        while (rs.next())
        {
            dryWaste = rs.getDouble( "dry_waste" );
            wetWaste = rs.getDouble( "wet_waste" );

        }
        data.setDryWaste( dryWaste - data.getDryWaste() );
        data.setWetWaste( wetWaste - data.getWetWaste() );
        if (data.getDryWaste() < 0.0 || data.getWetWaste() < 0.0)
        {
            return null;
        }
        rs.close();
        stmt.close();
        c.close();
        return data;
	}

	@Override
	public Integer subSupplierProduct(SupplierModelDailyWaste data) throws SQLException, ClassNotFoundException 
	{
		String query = "INSERT INTO supplier.supplier_waste (id,date_time,dry_waste,wet_waste) VALUES (?,?,?,?)";
        Connection c = Connections.setConnection();
        CallableStatement stmt = c.prepareCall( query );
        stmt.setInt( 1, data.getId() );
        stmt.setTimestamp( 2, data.getDate() );
        stmt.setDouble( 3, data.getDryWaste() );
        stmt.setDouble( 4, data.getWetWaste() );
        Integer result = stmt.executeUpdate();
        c.commit();
        stmt.close();
        c.close();
		return result;
	}

	@Override
	public List<SupplierModelDailyWasteNew> displaySuppliers(Long id) throws SQLException, ClassNotFoundException
	{
		List<SupplierModelDailyWasteNew> sms = new ArrayList<SupplierModelDailyWasteNew>();
		String query = "SELECT * FROM supplier.supplier_waste WHERE id=? AND \"deleteIndex\"=false ORDER BY date_time DESC;";
        Connection c = Connections.setConnection();
        CallableStatement stmt = c.prepareCall( query );
        stmt.setLong( 1, id );
        ResultSet rs = stmt.executeQuery();
        Double prevDW = 0.0;
        Double prevWW = 0.0;
        int i = 0;
        while (rs.next())
        {
            Boolean aos = false;
            if (i == 0)
            {
                aos = true;
            }
            else
            {
                if (prevDW - rs.getDouble( "dry_waste" ) < 0 || prevWW - rs.getDouble( "wet_waste" ) < 0)
                {
                    aos = true;
                }
                else
                {
                    aos = false;
                }
            }
            System.out.println( aos );
            sms.add(
            		new SupplierModelDailyWasteNew( 
            				rs.getLong("init_id"),
            				rs.getLong( "id" ), 
            				rs.getTimestamp( "date_time" ), 
            				rs.getDouble( "dry_waste" ),
            				rs.getDouble( "wet_waste" ), aos,
            				rs.getString( "description" )
            				) );
            prevDW = rs.getDouble( "dry_waste" );
            prevWW = rs.getDouble( "wet_waste" );
            i++;
        }
        rs.close();
        stmt.close();
        c.close();
		return sms;
	}

	@Override
	public SupplierModelSelect getSupplier(String username, String password)throws SQLException, ClassNotFoundException 
	{
        Connection c = Connections.setConnection();
        String query = "SELECT * FROM supplier.supplier_info WHERE id IN(SELECT id FROM supplier.supplier_login WHERE username=? AND password=? AND \"deleteIndex\"=false) AND \"deleteIndex\"=false";
        PreparedStatement stmt = c.prepareStatement( query );
        stmt.setString( 1, username );
        stmt.setString( 2, password );
        ResultSet rs = stmt.executeQuery();
        SupplierModelSelect sms = null;
        if (rs.next())
        {
            sms = new SupplierModelSelect( 
            								rs.getLong( "id" ), 
            								rs.getString( "supplier_name" ),
            								rs.getString( "contact" ), 
            								rs.getString( "email" ), 
            								rs.getString( "reg_no" ),
            								true );
        }
        rs.close();
        stmt.close();
        c.close();
        return sms;
	}

	@Override
	public List<SupplierModelFullSelect> getUniqueSupplierByDate(Date date_t)throws SQLException, ClassNotFoundException
	{
        List<Integer> l = new ArrayList<Integer>();
        Connection c = Connections.setConnection();
        String query = "SELECT * FROM supplier.\"fn_selectSuppliers\"(?) ORDER BY date_time DESC;";
        PreparedStatement stmt = c.prepareStatement( query );
        stmt.setDate( 1, date_t );
        ResultSet rs = stmt.executeQuery();
        List<SupplierModelFullSelect> sms = new ArrayList<SupplierModelFullSelect>();
        while (rs.next())
        {
            Boolean b = false;
            @SuppressWarnings("rawtypes")
			Iterator iterator = l.iterator();
            while (iterator.hasNext())
            {
                if (rs.getInt( "id" ) == (Integer) iterator.next())
                {
                    b = true;
                    break;
                }
            }
            if (!b)
            {
                sms.add( 
                		new SupplierModelFullSelect( 
                				rs.getInt( "id" ), 
                				rs.getString( "supplier_name" ), 
                				rs.getString( "contact" ), 
                				rs.getString( "email" ),
                				rs.getString( "reg_no" ), 
                				rs.getString( "latitude" ), 
                				rs.getString( "longitude" ), 
                				rs.getString( "state" ),
                				rs.getString( "city" ), 
                				rs.getString( "area" ), 
                				rs.getString( "street" ),
                				rs.getDouble( "dry_waste" ), 
                				rs.getDouble( "wet_waste" ), 
                				rs.getTimestamp( "date_time" ),
                				rs.getString( "description" )==null?"":rs.getString("description") ) );
                l.add( rs.getInt( "id" ) );
            }
        }
        rs.close();
        stmt.close();
        c.close();
        return sms;
	}

	@Override
	public Integer deleteSupplierWaste(Long supplierWasteId,Date dateToSearch) throws SQLException, ClassNotFoundException 
	{
		Connection c=Connections.setConnection();
		PreparedStatement stmt=c.prepareStatement("UPDATE supplier.supplier_waste SET \"deleteIndex\"=true WHERE supplier.supplier_waste.id=? AND DATE(date_time)=?;");
		stmt.setLong(1, supplierWasteId);
		stmt.setDate(2, dateToSearch);
		Integer rs=stmt.executeUpdate();
		c.commit();
		stmt.close();
		c.close();
		return rs;
	}

	@Override
	public Integer updateDescriptionInWaste(Long supplierWasteId, Date dateToSearch,String description)throws SQLException, ClassNotFoundException
	{
		Connection c=Connections.setConnection();
		PreparedStatement stmt=c.prepareStatement("UPDATE supplier.supplier_waste SET description=? WHERE supplier.supplier_waste.id=? AND DATE(date_time)=?;");
		stmt.setString(1, description);
		stmt.setLong(2, supplierWasteId);
		stmt.setDate(3, dateToSearch);
		Integer rs=stmt.executeUpdate();
		c.commit();
		stmt.close();
		c.close();
		return rs;
	}

	@Override
	public String[] storeFile(MultipartFile[] file, Long supplierId, Timestamp timeOfEntry) throws IOException 
	{
		String[] qualifiedPaths=new String[file.length];
		for(int i=0;i<file.length;i++)
		{
		String fileName = StringUtils.cleanPath(file[i].getOriginalFilename());

        if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        String path="E:/supplierWasteImages/"+supplierId+"/"+SupplierBusinessLogic.replaceColonToPeriod(timeOfEntry);
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
	public Integer addSupplierWasteImage(Long supplierId, Timestamp timeOfEntry,String imageURL)throws SQLException, ClassNotFoundException 
	{
		Connection c=Connections.setConnection();
		PreparedStatement stmt=c.prepareStatement("INSERT INTO supplier.supplier_waste_images (supplier_id,date_time,image_url) VALUES(?,?,?);");
		stmt.setLong(1, supplierId);
		stmt.setTimestamp(2, timeOfEntry);
		stmt.setString(3, imageURL);
		Integer rs=stmt.executeUpdate();
		c.commit();
		stmt.close();
		c.close();
		return rs;
	}

	@Override
	public List<SupplierWasteImagesSelect> selectSupplierWasteImages(Long supplierId, Date dateForSearch)throws SQLException, ClassNotFoundException
	{
		Connection c=Connections.setConnection();
		PreparedStatement stmt=c.prepareStatement("SELECT \r\n" + 
				"		supplier.supplier_waste_images.supplier_id,\r\n" + 
				"		supplier.supplier_waste_images.supplier_waste_image_id,\r\n" + 
				"		supplier.supplier_waste_images.date_time,\r\n" + 
				"		supplier.supplier_waste_images.image_url\r\n" + 
				"FROM supplier.supplier_waste_images\r\n" + 
				"WHERE supplier.supplier_waste_images.supplier_id=?\r\n" + 
				"AND DATE(supplier.supplier_waste_images.date_time)=?\r\n" + 
				"AND supplier.supplier_waste_images.delete_index=FALSE");
		stmt.setLong(1, supplierId);
		stmt.setDate(2, dateForSearch);
		List<SupplierWasteImagesSelect> ll=new ArrayList<SupplierWasteImagesSelect>();
		ResultSet rs=stmt.executeQuery();
		while(rs.next())
		{
			ll.add(new SupplierWasteImagesSelect(
					rs.getLong("supplier_id"),
					rs.getLong("supplier_waste_image_id"),
					rs.getTimestamp("date_time"),
					rs.getString("image_url")) );
		}
		rs.close();
		stmt.close();
		c.close();
		return ll;
	}

	@Override
	public List<SupplierModelFullSelect> getUniqueSupplierByDateAndState(Date date_t, String state)throws SQLException, ClassNotFoundException 
	{
		List<Integer> l = new ArrayList<Integer>();
        Connection c = Connections.setConnection();
        String query = "SELECT * FROM supplier.\"fn_selectSuppliers\"(?) WHERE LOWER(state)=LOWER(?) ORDER BY date_time DESC;";
        PreparedStatement stmt = c.prepareStatement( query );
        stmt.setDate( 1, date_t );
        stmt.setString(2, state);
        ResultSet rs = stmt.executeQuery();
        List<SupplierModelFullSelect> sms = new ArrayList<SupplierModelFullSelect>();
        while (rs.next())
        {
            Boolean b = false;
            @SuppressWarnings("rawtypes")
			Iterator iterator = l.iterator();
            while (iterator.hasNext())
            {
                if (rs.getInt( "id" ) == (Integer) iterator.next())
                {
                    b = true;
                    break;
                }
            }
            if (!b)
            {
                sms.add( 
                		new SupplierModelFullSelect( 
                				rs.getInt( "id" ), 
                				rs.getString( "supplier_name" ), 
                				rs.getString( "contact" ), 
                				rs.getString( "email" ),
                				rs.getString( "reg_no" ), 
                				rs.getString( "latitude" ), 
                				rs.getString( "longitude" ), 
                				rs.getString( "state" ),
                				rs.getString( "city" ), 
                				rs.getString( "area" ), 
                				rs.getString( "street" ),
                				rs.getDouble( "dry_waste" ), 
                				rs.getDouble( "wet_waste" ), 
                				rs.getTimestamp( "date_time" ),
                				rs.getString( "description" )==null?"":rs.getString("description") ) );
                l.add( rs.getInt( "id" ) );
            }
        }
        rs.close();
        stmt.close();
        c.close();
        return sms;
	}

	@Override
	public List<SupplierModelFullSelect> getUniqueSupplierByDateAndCity(Date date_t, String city)throws SQLException, ClassNotFoundException
	{
		List<Integer> l = new ArrayList<Integer>();
        Connection c = Connections.setConnection();
        String query = "SELECT * FROM supplier.\"fn_selectSuppliers\"(?) WHERE LOWER(city)=LOWER(?) ORDER BY date_time DESC;";
        PreparedStatement stmt = c.prepareStatement( query );
        stmt.setDate( 1, date_t );
        stmt.setString(2, city);
        ResultSet rs = stmt.executeQuery();
        List<SupplierModelFullSelect> sms = new ArrayList<SupplierModelFullSelect>();
        while (rs.next())
        {
            Boolean b = false;
            @SuppressWarnings("rawtypes")
			Iterator iterator = l.iterator();
            while (iterator.hasNext())
            {
                if (rs.getInt( "id" ) == (Integer) iterator.next())
                {
                    b = true;
                    break;
                }
            }
            if (!b)
            {
                sms.add( 
                		new SupplierModelFullSelect( 
                				rs.getInt( "id" ), 
                				rs.getString( "supplier_name" ), 
                				rs.getString( "contact" ), 
                				rs.getString( "email" ),
                				rs.getString( "reg_no" ), 
                				rs.getString( "latitude" ), 
                				rs.getString( "longitude" ), 
                				rs.getString( "state" ),
                				rs.getString( "city" ), 
                				rs.getString( "area" ), 
                				rs.getString( "street" ),
                				rs.getDouble( "dry_waste" ), 
                				rs.getDouble( "wet_waste" ), 
                				rs.getTimestamp( "date_time" ),
                				rs.getString( "description" )==null?"":rs.getString("description") ) );
                l.add( rs.getInt( "id" ) );
            }
        }
        rs.close();
        stmt.close();
        c.close();
        return sms;
	}

	@Override
	public Integer deleteSupplierWasteImage(Long wasteImageId) throws SQLException, ClassNotFoundException 
	{
		Connection c=Connections.setConnection();
		PreparedStatement stmt=c.prepareStatement("UPDATE supplier.supplier_waste_images SET delete_index=true WHERE supplier_waste_image_id=?;");
		stmt.setLong(1, wasteImageId);
		Integer rs=stmt.executeUpdate();
		c.commit();
		stmt.close();
		c.close();
		return rs;
	}


}
