package com.epiis.apirfds220262.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.apirfds220262.business.BusinessOffice;
import com.epiis.apirfds220262.dto.response.ResponseOfficeGetAll;

@RestController
@RequestMapping(path = "office")
public class OfficeController {
private final BusinessOffice businessOffice;
	
	public OfficeController(
			BusinessOffice businessOffice
	) {
		this.businessOffice = businessOffice;
	}
	
	@GetMapping(path = "getall")
	public ResponseEntity<ResponseOfficeGetAll> actionGetAll() {
		return ResponseEntity.ok(businessOffice.getAll());
	}
}