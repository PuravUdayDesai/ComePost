package com.hack.comp.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hack.comp.bl.ComposterFarmerTransactionBusinessLogic;
import com.hack.comp.model.composterFarmerTransaction.ComposterFarmerTransactionModel;

@RestController
@RequestMapping("/composterFarmerTransaction")
@CrossOrigin(origins = "*")
public class ComposterFarmerTransactionController
{

	@Autowired
	ComposterFarmerTransactionBusinessLogic cftbl;
	
	@GetMapping(path = "{init_id}",
    		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<ComposterFarmerTransactionModel>> selectComposterFarmerTransaction(@PathVariable @NotNull Long init_id)
	{
		return cftbl.selectComposterFarmerTransaction(init_id);
	}
	
	@GetMapping(path = "/composter/{composterId}",
    		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<ComposterFarmerTransactionModel>> selectComposterFarmerTransactionByComposterId(@PathVariable @NotNull Long composterId)
	{
		return cftbl.selectComposterFarmerTransactionByComposterId(composterId);
	}
	
	@GetMapping(path = "/composter/date/{composterId}",
    		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<ComposterFarmerTransactionModel>> selectComposterFarmerTransactionByDate(@PathVariable @NotNull Long composterId,@RequestParam("date") @NotNull Date dateToSearch)
	{
		return cftbl.selectComposterFarmerTransactionByDate(composterId, dateToSearch);
	}
}
