package com.epiis.apirfds220262.dto.response;

import com.epiis.apirfds220262.generic.ResponseGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserLogin extends ResponseGeneric {
	private String token;
	private String idUser;
	private String firstName;
	private String surName;
	private String email;
	private String role;
}
