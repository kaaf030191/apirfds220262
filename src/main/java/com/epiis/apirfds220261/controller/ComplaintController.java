package com.epiis.apirfds220262.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.apirfds220262.business.BusinessComplaint;
import com.epiis.apirfds220262.dto.request.RequestComplaintInsert;
import com.epiis.apirfds220262.dto.response.ResponseComplaintInsert;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "complaint")
public class ComplaintController {
	private final BusinessComplaint businessComplaint;
	
	public ComplaintController(
		BusinessComplaint businessComplaint
	) {
		this.businessComplaint = businessComplaint;
	}
	
	@PostMapping(path = "insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseComplaintInsert> actionInsert(@Valid @ModelAttribute RequestComplaintInsert request, BindingResult bindingResult) {
		try {
			ResponseComplaintInsert response;
			
			if (bindingResult.hasErrors()) {
				response = new ResponseComplaintInsert();
				
				bindingResult.getAllErrors().forEach(error -> {
					response.listMessage.add(error.getDefaultMessage());
				});
				
				return ResponseEntity.ok(response);
			}
			
			response = businessComplaint.insert(request);
			
			return ResponseEntity.ok(response);
		} catch(Exception _) {
			return null;
		}
	}
}