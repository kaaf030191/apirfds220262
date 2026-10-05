package com.epiis.apirfds220262.dto.response;

import com.epiis.apirfds220262.generic.ResponseGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserAuthenticated extends ResponseGeneric {
	private String idUser;
	private String firstName;
	private String surName;
	private String email;
	private String role;
}
