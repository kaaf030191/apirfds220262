package com.epiis.apirfds220262.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.apirfds220262.business.BusinessAdmin;
import com.epiis.apirfds220262.dto.request.RequestAdminGetAll;
import com.epiis.apirfds220262.dto.request.RequestAdminUpdateStatus;
import com.epiis.apirfds220262.dto.response.ResponseAdminGetAll;
import com.epiis.apirfds220262.dto.response.ResponseAdminUpdateStatus;

@RestController
@RequestMapping(path = "admin")
public class AdminController {
	private final BusinessAdmin businessAdmin;

	public AdminController(BusinessAdmin businessAdmin) {
		this.businessAdmin = businessAdmin;
	}

	@PostMapping(path = "complaint/getall")
	public ResponseEntity<ResponseAdminGetAll> complaintGetAll(RequestAdminGetAll request) {
		return ResponseEntity.ok(this.businessAdmin.complaintGetAll(request));
	}

	@PostMapping(path = "suggestion/getall")
	public ResponseEntity<ResponseAdminGetAll> suggestionGetAll(RequestAdminGetAll request) {
		return ResponseEntity.ok(this.businessAdmin.suggestionGetAll(request));
	}

	@PostMapping(path = "complaint/updatestatus")
	public ResponseEntity<ResponseAdminUpdateStatus> complaintUpdateStatus(RequestAdminUpdateStatus request) {
		return ResponseEntity.ok(this.businessAdmin.complaintUpdateStatus(request));
	}

	@PostMapping(path = "suggestion/updatestatus")
	public ResponseEntity<ResponseAdminUpdateStatus> suggestionUpdateStatus(RequestAdminUpdateStatus request) {
		return ResponseEntity.ok(this.businessAdmin.suggestionUpdateStatus(request));
	}
}
