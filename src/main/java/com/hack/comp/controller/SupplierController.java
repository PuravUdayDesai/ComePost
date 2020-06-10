package com.hack.comp.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hack.comp.bl.SupplierBusinessLogic;
import com.hack.comp.model.supplier.SupplierModelDailyWaste;
import com.hack.comp.model.supplier.SupplierModelDailyWasteNew;
import com.hack.comp.model.supplier.SupplierModelFullSelect;
import com.hack.comp.model.supplier.SupplierModelInsert;
import com.hack.comp.model.supplier.SupplierModelSelect;

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
    public ResponseEntity<Void> subSupplierProduct(@Valid @RequestBody SupplierModelDailyWaste data)
    {
       return sbl.subSupplierProduct(data);
    }

    
    /*
     * This method is used to display
     * the suppliers which have added
     * a product
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, path = "{id}")
    public ResponseEntity<List<SupplierModelDailyWasteNew>> displaySuppliers(@PathVariable @NotNull Integer id)
    {
    	return sbl.displaySuppliers(id);
    }

    
    /*
     * This method is used to validate
     * supplier for login in application
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, path = "/unp")
    public ResponseEntity<SupplierModelSelect> getSupplier(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password)
    {
    	return sbl.getSupplier(username, password);
    }

    
    /*
     * This method is used to get 
     * supplier by filter on Date
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, path = "/display")
    public ResponseEntity<List<SupplierModelFullSelect>> getSupplierByDate(@RequestParam(name = "date") Date date_t)
    {
    	return sbl.getSupplierByDate(date_t);
    }

    
    /*
     * This method is used to get
     * filtered suppliers based on 
     * criteria and date of search
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, path = "/unique/display")
    public ResponseEntity<List<SupplierModelFullSelect>> getUniqueSupplierByDate(@RequestParam(name = "date") Date date_t)
    {
    	return sbl.getUniqueSupplierByDate(date_t);
    }

}
