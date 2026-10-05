package com.epiis.apirfds220262.dto.response;

import com.epiis.apirfds220262.generic.ResponseGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAdminUpdateStatus extends ResponseGeneric {
	private String idParent;
	private String status;
}
