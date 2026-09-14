package com.epiis.apirfds220262.dto.response;

import com.epiis.apirfds220262.generic.ResponseGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSuggestionGetByCode extends ResponseGeneric {
	public String status;
}
