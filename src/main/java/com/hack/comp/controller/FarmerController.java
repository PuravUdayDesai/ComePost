package com.hack.comp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hack.comp.bl.FarmerBusinessLogic;
import com.hack.comp.model.farmer.FarmerInsert;
import com.hack.comp.model.farmer.FarmerLoginModel;


@RestController
@RequestMapping("/farmer")
@CrossOrigin(origins = "*")
public class FarmerController
{
	@Autowired
	FarmerBusinessLogic fbl;
	
    @GetMapping
    public ResponseEntity<FarmerLoginModel> validateFarmer(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) 
    {
    	return fbl.validateFarmer(username, password);
    }

    // REGISTER
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> addFarmer(@Valid @RequestBody FarmerInsert fi)
    {
    	return fbl.addFarmer(fi);
    }


}
