package com.hack.comp.controller;

import java.sql.Date;
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

import com.hack.comp.bl.SupplierBusinessLogic;
import com.hack.comp.model.supplier.SupplierModelDailyWaste;
import com.hack.comp.model.supplier.SupplierModelDailyWasteNew;
import com.hack.comp.model.supplier.SupplierModelFullSelect;
import com.hack.comp.model.supplier.SupplierModelInsert;
import com.hack.comp.model.supplier.SupplierModelSelect;
import com.hack.comp.model.supplier.SupplierWasteImagesSelect;

@RestController
@RequestMapping("/supplier")
@CrossOrigin(origins = "*")
public class SupplierController
{

	@Autowired
	SupplierBusinessLogic sbl;
	
	/*
	 * This method is used to add
	 * a new Supplier
	 */
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> addSupplier(@Valid @RequestBody SupplierModelInsert smi)
    {
    	return sbl.addSupplier(smi);
    }

    
    /*
     * This method is used to add
     * a new product by Supplier
     */
    @PutMapping(path = "/product/add", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> addSupplierProduct(@Valid @RequestBody SupplierModelDailyWaste data)
    {
    	return sbl.addSupplierProduct(data);
    }

    
    /*
     * This method is used to substract
     * a portion from the product by
     * composter
     */
    @PutMapping(path = "/product/sub", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> subSupplierProduct(@Valid @RequestBody SupplierModelDailyWaste data,@RequestParam(name = "composterId") @NotNull Long composterId,@RequestParam(name = "searchDate") @NotNull Date searchDate)
    {
    	System.out.println("Hello!! In Controller"); 
    	return sbl.subSupplierProduct(data,composterId,searchDate);
    }

    
    /*
     * This method is used to display
     * the suppliers which have added
     * a product
     */
    //HOME Page
    @GetMapping(path = "{id}",
    		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<SupplierModelDailyWasteNew>> displaySuppliers(@PathVariable @NotNull Long id)
    {
    	return sbl.displaySuppliers(id);
    }

    
    /*
     * This method is used to validate
     * supplier for login in application
     */
    @GetMapping(path = "/login",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<SupplierModelSelect> getSupplier(@RequestParam(name = "username") @NotNull String username, @RequestParam(name = "password") @NotNull String password)
    {
    	return sbl.getSupplier(username, password);
    }
    
    
    /*
     * This method is used to get
     * suppliers based on date
     */
    @GetMapping(path = "/display",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<SupplierModelFullSelect>> getUniqueSupplierByDate(@RequestParam(name = "date") @NotNull Date date_t)
    {
    	return sbl.getUniqueSupplierByDate(date_t);
    }
    
    
    /*
     * This method is used to get
     * suppliers based on date and
     * state
     */
    @GetMapping(path = "/display/state",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<SupplierModelFullSelect>> getUniqueSupplierByDateAndState(@RequestParam(name = "date") @NotNull Date date_t,@RequestParam(name = "state") @NotNull String state)
    {
    	return sbl.getUniqueSupplierByDateAndState(date_t, state);
    }
    
    
    /*
     * This method is used to get
     * suppliers based on date and
     * city
     */
    @GetMapping(path = "/display/city",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<SupplierModelFullSelect>> getUniqueSupplierByDateAndCity(@RequestParam(name = "date") @NotNull Date date_t,@RequestParam(name = "city") @NotNull String city)
    {
    	return sbl.getUniqueSupplierByDateAndCity(date_t, city);
    }
    
    
    /*
     * This method is used to delete
     * an added waste entry
     */
    @DeleteMapping(path="/waste/{supplierWasteId}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> deleteSupplierWaste(@PathVariable @NotNull Long supplierWasteId,@RequestParam(name = "date") @NotNull Date dateToSearch,@RequestParam(name = "currentTimestamp") @NotNull Timestamp currentTime)
	 {
    	return sbl.deleteSupplierWaste(supplierWasteId,dateToSearch,currentTime);
	 }
    
    
    /*
     * This method is used to update
     * the description of the waste
     */
    @PutMapping(path = "/description/{supplierWasteId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> updateDescriptionInWaste(@PathVariable @NotNull Long supplierWasteId,@RequestParam(name = "date") @NotNull  Date dateToSearch,@RequestParam(name = "description") @NotNull String description)
	 {
    	return sbl.updateDescriptionInWaste(supplierWasteId, dateToSearch, description);
	 }

    
    /*
     * This method is used to add
     * and upload waste image file
     * to server
     */
    @PostMapping("/uploadImage/{supplierWasteId}")
    public ResponseEntity<Void> addSupplierWasteImage(@RequestPart("file") MultipartFile[] file,@PathVariable @NotNull Long supplierWasteId,@RequestParam(name = "timestamp") @NotNull  Timestamp timeOfEntry)
    {
    	return sbl.addSupplierWasteImage(file, supplierWasteId, timeOfEntry);
    }
    
    
    /*
     * This method is used to
     * retrieve/fetch the images
     * from the server
     */
	@RequestMapping("/fileView")
	public void viewFile(HttpServletRequest request, 
									HttpServletResponse response,
									@RequestParam("filePath") String filePath, 
									@RequestParam("fileName") String fileName) {
		
		 sbl.viewFile(request, response, filePath, fileName);
	}
	
	
	/*
	 * This method is used to
	 * download image from the
	 * server
	 */
	@RequestMapping("/fileDownload")
	public void downloadFile(HttpServletRequest request, 
									HttpServletResponse response,
									@RequestParam("filePath") String filePath, 
									@RequestParam("fileName") String fileName) {
		
		 sbl.downloadFile(request, response, filePath, fileName);
	}
	
	
	/*
	 * This method is used to
	 * select the added records
	 * for waste images
	 */
	@GetMapping(path = "/display/supplierWasteImages/{supplierId}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<SupplierWasteImagesSelect>> selectSupplierWasteImages(@PathVariable @NotNull Long supplierId,@RequestParam(name = "date") @NotNull  Date dateForSearch)
	{
		return sbl.selectSupplierWasteImages(supplierId, dateForSearch);
	}
	
	
	/*
	 * This method is used to delete
	 * a waste image uploaded by the 
	 * supplier
	 */
	@DeleteMapping(path="/waste/image/{wasteImageId}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Void> deleteSupplierWasteImage(@PathVariable @NotNull Long wasteImageId)
	{
		return sbl.deleteSupplierWasteImage(wasteImageId);
	}
}
