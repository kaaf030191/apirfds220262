package com.epiis.apirfds220262.business;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.epiis.apirfds220262.dto.request.RequestComplaintInsert;
import com.epiis.apirfds220262.dto.response.ResponseComplaintGetByCode;
import com.epiis.apirfds220262.dto.response.ResponseComplaintInsert;
import com.epiis.apirfds220262.entity.EntityComplaint;
import com.epiis.apirfds220262.entity.EntityComplaintFile;
import com.epiis.apirfds220262.helper.GenericHelper;
import com.epiis.apirfds220262.repository.RepositoryComplaint;
import com.epiis.apirfds220262.repository.RepositoryComplaintFile;
import com.epiis.apirfds220262.staticdata.EnumProcess;

@Service
public class BusinessComplaint {
	private final RepositoryComplaint repositoryComplaint;
	private final RepositoryComplaintFile repositoryComplaintFile;
	
	public BusinessComplaint(
		RepositoryComplaint repositoryComplaint,
		RepositoryComplaintFile repositoryComplaintFile
	) {
		this.repositoryComplaint = repositoryComplaint;
		this.repositoryComplaintFile = repositoryComplaintFile;
	}
	
	public ResponseComplaintInsert insert(RequestComplaintInsert request) throws IOException {
		ResponseComplaintInsert response = new ResponseComplaintInsert();
		
		EntityComplaint entityComplaint = new EntityComplaint();
		
		String codeTemp = GenericHelper.followCodeGeneration();
		
		entityComplaint.setIdComplaint(UUID.randomUUID().toString());
		entityComplaint.setIdOffice(request.getIdOffice());
		entityComplaint.setIdProfessor(request.getIdProfessor());
		entityComplaint.setCode(codeTemp);
		entityComplaint.setComplaintFullName(request.getComplaintFullName().trim());
		entityComplaint.setPersonFullName(request.getPersonFullName().trim());
		entityComplaint.setDescription(request.getDescription().trim());
		entityComplaint.setIssueDate(new java.sql.Date(request.getIssueDate().getTime()));
		entityComplaint.setStatus(EnumProcess.PENDING.toString());
		entityComplaint.setCreatedAt(new java.sql.Date(new Date().getTime()));
		entityComplaint.setUpdatedAt(entityComplaint.getCreatedAt());
		
		repositoryComplaint.save(entityComplaint);
		
		if(request.getFiles() != null) {
			Path basePath = Paths.get("storage/complaintfile");
			
			if(!Files.exists(basePath)) {
	            Files.createDirectories(basePath);
	        }
			
			List<EntityComplaintFile> listEntityComplaintFile = new ArrayList<>();
			
			for(var file: request.getFiles()) {
				EntityComplaintFile entityComplaintFile = new EntityComplaintFile();
	            
	            entityComplaintFile.setIdComplaintfile(UUID.randomUUID().toString());
	            entityComplaintFile.setIdComplaint(entityComplaint.getIdComplaint());
	            entityComplaintFile.setName(file.getOriginalFilename());
	            entityComplaintFile.setExtension(entityComplaintFile.getName().substring(entityComplaintFile.getName().lastIndexOf(".") + 1).toLowerCase());
	            entityComplaintFile.setCreatedAt(entityComplaint.getCreatedAt());
	            entityComplaintFile.setUpdatedAt(entityComplaint.getCreatedAt());
	            
	            listEntityComplaintFile.add(entityComplaintFile);
	            
	            Files.copy(file.getInputStream(), basePath.resolve(entityComplaintFile.getIdComplaintfile() + "." + entityComplaintFile.getExtension()));
			}
	        
	        repositoryComplaintFile.saveAll(listEntityComplaintFile);
	    }
		
		response.setCode(codeTemp);
		
		response.success();
		response.listMessage.add("Registro realizado correctamente.");
		
		return response;
	}
	
	public ResponseComplaintGetByCode getByCode(String code) {
		ResponseComplaintGetByCode response = new ResponseComplaintGetByCode();
		
		EntityComplaint entityComplaint = repositoryComplaint.findByCode(code);
		
		if(entityComplaint != null) {
			response.setStatus(entityComplaint.getStatus());
		}
		
		response.success();
		
		return response;
	}
}