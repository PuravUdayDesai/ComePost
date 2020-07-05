package com.hack.comp.dao.schema;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hack.comp.model.compost.CompostModelInsert;
import com.hack.comp.model.compost.ComposterCompostImageSelect;
import com.hack.comp.model.compost.ComposterDailyModelCompost;
import com.hack.comp.model.compost.ComposterDailyModelCompostNew;
import com.hack.comp.model.compost.ComposterFullSelect;
import com.hack.comp.model.compost.ComposterLoginModel;

public interface CompostDAO 
{
	 public ComposterLoginModel 				validateComposter(String username,String password)												throws SQLException,ClassNotFoundException;
	 public Boolean 							addCompost(CompostModelInsert cmi)																throws SQLException,ClassNotFoundException;
	 public Long	 							addCompostProduct(ComposterDailyModelCompost data)												throws SQLException,ClassNotFoundException;
	 public List<ComposterDailyModelCompostNew>	displayComposters(Long id) 																		throws SQLException,ClassNotFoundException;
	 public Integer 							subCompostProduct(ComposterDailyModelCompost data,Long init_id,Date searchDate)				throws SQLException,ClassNotFoundException;
	 public List<ComposterFullSelect> 			getComposterByDate(Date date) 																	throws SQLException,ClassNotFoundException;
	 public Double 								refreshCompostSubProduct(ComposterDailyModelCompost data, Long init_id)							throws SQLException,ClassNotFoundException;
	 public Integer								addComposterCompostImages(Long composterId,Long init_id,Timestamp date_time,String image_url)	throws SQLException,ClassNotFoundException;
	 public String[]							storeFile(MultipartFile[] file,Long composterId,Long initId,Timestamp timeOfEntry)				throws IOException;
	 public List<ComposterCompostImageSelect>	selectComposterCompostImage(Long composterId,Long initId,Date dateToSearch)						throws SQLException,ClassNotFoundException;	
	 public Integer								deleteComposterCompostWasteImage(Long composterCompostWasteImage)								throws SQLException,ClassNotFoundException;
	 public List<ComposterFullSelect> 			getComposterByDateByState(Date date,String state) 												throws SQLException,ClassNotFoundException;
	 public List<ComposterFullSelect> 			getComposterByDateByCity(Date date,String city) 												throws SQLException,ClassNotFoundException;
}
