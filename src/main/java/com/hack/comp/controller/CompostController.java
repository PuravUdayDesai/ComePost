package com.hack.comp.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hack.comp.bl.CompostBusinessLogic;
import com.hack.comp.model.compost.CompostModelInsert;
import com.hack.comp.model.compost.ComposterCompostImageSelect;
import com.hack.comp.model.compost.ComposterDailyModelCompost;
import com.hack.comp.model.compost.ComposterDailyModelCompostNew;
import com.hack.comp.model.compost.ComposterFullSelect;
import com.hack.comp.model.compost.ComposterLoginModel;

@RestController
@RequestMapping("/compost")
@CrossOrigin(origins = "*")
public class CompostController {

	@Autowired
	CompostBusinessLogic cbl;

	/*
	 * This method is used for login Validation For Composter
	 */
	@GetMapping()
	public ResponseEntity<ComposterLoginModel> validateComposter(
			@RequestParam(name = "username") @NotNull String username,
			@RequestParam(name = "password") @NotNull String password) {
		return cbl.validateComposter(username, password);
	}

	
	/*
	 * This method is used for creating/registering a new composter in the
	 * application
	 */
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> addCompost(@Valid @RequestBody CompostModelInsert cmi) {
		return cbl.addCompost(cmi);
	}

	
	/*
	 * This method is used to add a product by composter
	 */
	@PostMapping(path = "/compost/add", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Long> addCompostProduct(@Valid @RequestBody ComposterDailyModelCompost data)
			throws SQLException {
		return cbl.addCompostProduct(data);
	}

	
	/*
	 * This method is used to display all the suppliers
	 */
	// HOME Page
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, path = "{id}")
	public ResponseEntity<List<ComposterDailyModelCompostNew>> displayComposters(@PathVariable @NotNull Long id)
			throws SQLException {
		return cbl.displayComposters(id);
	}

	/*
	 * This method is used to substract the product weight created by composter and
	 * too to add funds in government account
	 */
	@PutMapping(path = "/compost/sub/{init_id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Double> subCompostProduct(@Valid @RequestBody ComposterDailyModelCompost data,
			@PathVariable Long init_id,
			@RequestParam(name = "farmerId") @NotNull Long farmerId,
			@RequestParam(name = "searchDate") @NotNull Date searchDate) {
		return cbl.subCompostProduct(data, init_id,farmerId,searchDate);
	}

	/*
	 * This method is used to display filtered suppliers by date
	 */
	@GetMapping(path = "/display",produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ComposterFullSelect>> getComposterByDate(@RequestParam(name = "date") @NotNull Date date)
			throws SQLException {
		return cbl.getComposterByDate(date);
	}
	
    /*
     * This method is used to add
     * and upload waste image file
     * to server
     */
    @PostMapping("/uploadImage/{composterId}")
    public ResponseEntity<Void> addSupplierWasteImage(
    												@RequestPart("file") MultipartFile[] file,
    												@PathVariable @NotNull Long composterId,
    												@RequestParam(name = "init_id") @NotNull  Long init_id,
    												@RequestParam(name = "timestamp") @NotNull  Timestamp timeOfEntry)
    {
    	return cbl.addComposterCompostImages(file, composterId, init_id, timeOfEntry);
    }
    
    
    /*
     * This method is used to
     * retrieve/fetch the images
     * from the server
     */
	@RequestMapping("/fileView")
	public void viewFile(HttpServletRequest request, 
									HttpServletResponse response,
									@RequestParam("filePath") @NotNull String filePath, 
									@RequestParam("fileName") @NotNull String fileName) {
		
		 cbl.viewFile(request, response, filePath, fileName);
	}
	
	
	/*
	 * This method is used to
	 * download image from the
	 * server
	 */
	@RequestMapping("/fileDownload")
	public void downloadFile(HttpServletRequest request, 
									HttpServletResponse response,
									@RequestParam("filePath") @NotNull String filePath, 
									@RequestParam("fileName") @NotNull String fileName) {
		
		 cbl.downloadFile(request, response, filePath, fileName);
	}
	
	@GetMapping(path = "/composterCompostImage/{composterId}",produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ComposterCompostImageSelect>> selectComposterCompostImage(
																				@PathVariable @NotNull Long composterId,
																				@RequestParam(name = "init_id") @NotNull Long initId,
																				@RequestParam(name = "date") @NotNull  Date dateToSearch)
	{
		return cbl.selectComposterCompostImage(composterId, initId, dateToSearch);
	}
	
	@DeleteMapping(path="/waste/image/{composterCompostWasteImage}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Void> deleteComposterCompostWasteImage(@PathVariable @NotNull Long composterCompostWasteImage)
	{
		return cbl.deleteComposterCompostWasteImage(composterCompostWasteImage);
	}
	
	/*
	 * This method is used to display filtered suppliers by date
	 */
	@GetMapping(path = "/display/state",produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ComposterFullSelect>> getComposterByDateAndState(@RequestParam(name = "date") @NotNull Date date,@RequestParam(name = "state") @NotNull String state)
			throws SQLException {
		return cbl.getComposterByDate(date);
	}
	
	/*
	 * This method is used to display filtered suppliers by date
	 */
	@GetMapping(path = "/display/city",produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<ComposterFullSelect>> getComposterByDateAndCity(@RequestParam(name = "date") @NotNull Date date,@RequestParam(name = "city") @NotNull String city)
			throws SQLException {
		return cbl.getComposterByDate(date);
	}
}