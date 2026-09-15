package com.epiis.apirfds220262.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.apirfds220262.business.BusinessSuggestion;
import com.epiis.apirfds220262.dto.request.RequestSuggestionInsert;
import com.epiis.apirfds220262.dto.response.ResponseSuggestionGetByCode;
import com.epiis.apirfds220262.dto.response.ResponseSuggestionInsert;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "suggestion")
public class SuggestionController {
	private final BusinessSuggestion businessSuggestion;
	
	public SuggestionController(
		BusinessSuggestion businessSuggestion
	) {
		this.businessSuggestion = businessSuggestion;
	}
	
	@PostMapping(path = "insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseSuggestionInsert> actionInsert(@Valid @ModelAttribute RequestSuggestionInsert request, BindingResult bindingResult) {
		try {
			ResponseSuggestionInsert response;
			
			if (bindingResult.hasErrors()) {
				response = new ResponseSuggestionInsert();
				
				bindingResult.getAllErrors().forEach(error -> {
					response.listMessage.add(error.getDefaultMessage());
				});
				
				return ResponseEntity.ok(response);
			}
			
			response = businessSuggestion.insert(request);
			
			return ResponseEntity.ok(response);
		} catch(Exception _) {
			return null;
		}
	}
	
	@GetMapping(path = "getbycode/{code}")
	public ResponseEntity<ResponseSuggestionGetByCode> actionGetByCode(@PathVariable String code) {
		return ResponseEntity.ok(businessSuggestion.getByCode(code));
	}
}