package com.epiis.apirfds220262.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserLogin {
	private String email;
	private String password;
}
