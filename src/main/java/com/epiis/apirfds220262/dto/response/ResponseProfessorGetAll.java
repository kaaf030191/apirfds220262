package com.epiis.apirfds220262.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.epiis.apirfds220262.generic.ResponseGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseProfessorGetAll extends ResponseGeneric {
	List<Map<String, String>> listProfessor = new ArrayList<>();
}
