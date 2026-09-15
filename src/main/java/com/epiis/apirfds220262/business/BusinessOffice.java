package com.epiis.apirfds220262.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.epiis.apirfds220262.dto.response.ResponseOfficeGetAll;
import com.epiis.apirfds220262.entity.EntityOffice;
import com.epiis.apirfds220262.repository.RepositoryOffice;

@Service
public class BusinessOffice {
	private final RepositoryOffice repositoryOffice;

	public BusinessOffice(
			RepositoryOffice repositoryOffice
	) {
		this.repositoryOffice = repositoryOffice;
	}
	
	public ResponseOfficeGetAll getAll() {
		ResponseOfficeGetAll response = new ResponseOfficeGetAll();
		
		List<EntityOffice> listEntityOffice = repositoryOffice.findAll();
		
		for(EntityOffice item: listEntityOffice) {
			Map<String, String> data = new HashMap<>();
			
			data.put("idOffice", item.getIdOffice());
			data.put("name", item.getName());
			
			response.getListOffice().add(data);
		}
		
		response.success();
		
		return response;
	}
}