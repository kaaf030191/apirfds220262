package com.epiis.apirfds220262.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.epiis.apirfds220262.dto.response.ResponseProfessorGetAll;
import com.epiis.apirfds220262.entity.EntityProfessor;
import com.epiis.apirfds220262.repository.RepositoryProfessor;

@Service
public class BusinessProfessor {
	private final RepositoryProfessor repositoryProfessor;

	public BusinessProfessor(
			RepositoryProfessor repositoryProfessor
	) {
		this.repositoryProfessor = repositoryProfessor;
	}
	
	public ResponseProfessorGetAll getAll() {
		ResponseProfessorGetAll response = new ResponseProfessorGetAll();
		
		List<EntityProfessor> listEntityProfessor = repositoryProfessor.findAll();
		
		for(EntityProfessor item: listEntityProfessor) {
			Map<String, String> data = new HashMap<>();
			
			data.put("idProfessor", item.getIdProfessor());
			data.put("firstName", item.getFirstName());
			data.put("surName", item.getSurName());
			data.put("email", item.getEmail());
			
			response.getListProfessor().add(data);
		}
		
		response.success();
		
		return response;
	}
}