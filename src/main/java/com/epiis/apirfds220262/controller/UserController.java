package com.epiis.apirfds220262.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.apirfds220262.business.BusinessUser;
import com.epiis.apirfds220262.dto.request.RequestUserLogin;
import com.epiis.apirfds220262.dto.request.RequestUserRegister;
import com.epiis.apirfds220262.dto.response.ResponseUserAuthenticated;
import com.epiis.apirfds220262.dto.response.ResponseUserLogin;
import com.epiis.apirfds220262.dto.response.ResponseUserRegister;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "user")
public class UserController {
	private final BusinessUser businessUser;

	public UserController(BusinessUser businessUser) {
		this.businessUser = businessUser;
	}

	@PostMapping(path = "register")
	public ResponseEntity<ResponseUserRegister> insert(RequestUserRegister request) {
		return ResponseEntity.ok(this.businessUser.register(request));
	}

	@PostMapping(path = "login")
	public ResponseEntity<ResponseUserLogin> login(RequestUserLogin request) {
		return ResponseEntity.ok(this.businessUser.login(request));
	}

	@GetMapping(path = "getauthenticated")
	public ResponseEntity<ResponseUserAuthenticated> getAuthenticated(HttpServletRequest request) {
		return ResponseEntity.ok(this.businessUser.getAuthenticated(request));
	}
}
