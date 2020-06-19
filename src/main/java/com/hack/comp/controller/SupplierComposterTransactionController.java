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

import com.hack.comp.bl.SupplierComposterTransactionBusinessLogic;
import com.hack.comp.model.supplierComposterTransaction.SupplierComposterTransactionSelectModel;

@RestController
@RequestMapping("/supplierComposterTransaction")
@CrossOrigin(origins = "*")
public class SupplierComposterTransactionController 
{
	
	@Autowired
	SupplierComposterTransactionBusinessLogic sctbl;
	
	  @GetMapping(path = "{init_id}",
	    		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<SupplierComposterTransactionSelectModel> selectSupplierComposterTrasnsaction(@PathVariable @NotNull Long init_id)
	{
		return sctbl.selectSupplierComposterTrasnsaction(init_id);
	}
	  
	  @GetMapping(path = "/supplier/date/{supplierId}",
	    		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<SupplierComposterTransactionSelectModel>> selectSupplierComposterTransactionByDate(@PathVariable @NotNull Long supplierId,@RequestParam("date") @NotNull Date dateToSearch)
	{
		return sctbl.selectSupplierComposterTransactionByDate(supplierId, dateToSearch);
	}
	  @GetMapping(path = "/supplier/{supplierId}",
	    		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<SupplierComposterTransactionSelectModel>> selectSupplierComposterTransactionBySupplierId(@PathVariable @NotNull Long supplierId)
	{
		return sctbl.selectSupplierComposterTransactionBySupplierId(supplierId);
	}
}
