package com.epiis.apirfds220262.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserRegister {
	private String firstName;
	private String surName;
	private String email;
	private String password;
}
