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

import com.epiis.apirfds220262.business.BusinessComplaintComment;
import com.epiis.apirfds220262.dto.request.RequestComplaintCommentInsert;
import com.epiis.apirfds220262.dto.response.ResponseComplaintCommentGetByCode;
import com.epiis.apirfds220262.dto.response.ResponseComplaintCommentInsert;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "complaintcomment")
public class ComplaintCommentController {
	private final BusinessComplaintComment businessComplaintComment;
	
	public ComplaintCommentController(
		BusinessComplaintComment businessComplaintComment
	) {
		this.businessComplaintComment = businessComplaintComment;
	}
	
	@PostMapping(path = "insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseComplaintCommentInsert> actionInsert(@Valid @ModelAttribute RequestComplaintCommentInsert request, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			ResponseComplaintCommentInsert response = new ResponseComplaintCommentInsert();
			
			bindingResult.getAllErrors().forEach(error -> {
				response.listMessage.add(error.getDefaultMessage());
			});
			
			return ResponseEntity.ok(response);
		}
		
		try {
			return ResponseEntity.ok(businessComplaintComment.insert(request));
		} catch(Exception _) {
			ResponseComplaintCommentInsert response = new ResponseComplaintCommentInsert();
			
			response.exception();
			response.listMessage.add("Ocurrió un error al registrar el comentario.");
			
			return ResponseEntity.ok(response);
		}
	}
	
	@GetMapping(path = "getbycode/{code}")
	public ResponseEntity<ResponseComplaintCommentGetByCode> actionGetByCode(@PathVariable String code) {
		return ResponseEntity.ok(businessComplaintComment.getByCode(code));
	}
}
