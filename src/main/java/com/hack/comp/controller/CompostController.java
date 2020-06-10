package com.hack.comp.controller;

import java.sql.Date;
import java.sql.SQLException;
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

import com.hack.comp.bl.CompostBusinessLogic;
import com.hack.comp.model.compost.CompostAndSupplierModel;
import com.hack.comp.model.compost.CompostModelInsert;
import com.hack.comp.model.compost.ComposterDailyModelCompost;
import com.hack.comp.model.compost.ComposterDailyModelCompostNew;
import com.hack.comp.model.compost.ComposterFullSelect;
import com.hack.comp.model.compost.ComposterLoginModel;

@RestController
@RequestMapping("/compost")
@CrossOrigin(origins = "*")
public class CompostController
{
	
	@Autowired
	CompostBusinessLogic cbl;

    /*
     * This method is used for login Validation
     * For Composter
     */
    @GetMapping()
    public ResponseEntity<ComposterLoginModel> validateComposter(@RequestParam(name = "username") @NotNull String username, @RequestParam(name = "password") @NotNull String password)
    {
        return cbl.validateComposter(username, password);
    }

    
    /*
     * This method is used for creating/registering
     * a new composter in the application
     */
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> addCompost(@Valid @RequestBody CompostModelInsert cmi)
    {
    	return cbl.addCompost(cmi);
    }
    
    
    /*
     * This method is used to add a transaction
     * record between composter and supplier
     */
    @PostMapping(path = "{composter_id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> compAndSuppTran(@PathVariable long composter_id, @Valid @RequestBody CompostAndSupplierModel casm)
    {
    	return cbl.compAndSuppTran(composter_id, casm);
    }

    
    /*
     * This method is used to add a product
     * by composter
     */
    @PostMapping(path = "/compost/add", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> addCompostProduct(@Valid @RequestBody ComposterDailyModelCompost data) throws SQLException
    {
        return cbl.addCompostProduct(data);
    }

    
    /*
     * This method is used to substract the
     * product weight created by composter
     * and too to add funds in government
     * account
     */
    @PutMapping(path = "/compost/sub/{init_id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Double> subCompostProduct(@Valid @RequestBody ComposterDailyModelCompost data, @PathVariable Long init_id)
    {
       return cbl.subCompostProduct(data, init_id);
    }

    
    /*
     * This method is used to display
     * all the suppliers
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, path = "{id}")
    public ResponseEntity<List<ComposterDailyModelCompostNew>> displaySuppliers(@PathVariable Long id) throws SQLException
    {
       return cbl.displaySuppliers(id);
    }

    
    /*
     * This method is used to display
     * filtered suppliers by date
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, path = "/display")
    public ResponseEntity<List<ComposterFullSelect>> getSupplierByDate(@RequestParam(name = "date") Date date) throws SQLException
    {
    	return cbl.getSupplierByDate(date);
    }

}