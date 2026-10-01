package com.epiis.apirfds220262.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.epiis.apirfds220262.dto.request.RequestComplaintCommentInsert;
import com.epiis.apirfds220262.dto.response.ResponseComplaintCommentGetByCode;
import com.epiis.apirfds220262.dto.response.ResponseComplaintCommentInsert;
import com.epiis.apirfds220262.entity.EntityComplaint;
import com.epiis.apirfds220262.entity.EntityComplaintComment;
import com.epiis.apirfds220262.entity.EntityUser;
import com.epiis.apirfds220262.repository.RepositoryComplaint;
import com.epiis.apirfds220262.repository.RepositoryComplaintComment;
import com.epiis.apirfds220262.repository.RepositoryUser;

@Service
public class BusinessComplaintComment {
	private final RepositoryComplaint repositoryComplaint;
	private final RepositoryComplaintComment repositoryComplaintComment;
	private final RepositoryUser repositoryUser;
	
	public BusinessComplaintComment(
		RepositoryComplaint repositoryComplaint,
		RepositoryComplaintComment repositoryComplaintComment,
		RepositoryUser repositoryUser
	) {
		this.repositoryComplaint = repositoryComplaint;
		this.repositoryComplaintComment = repositoryComplaintComment;
		this.repositoryUser = repositoryUser;
	}
	
	public ResponseComplaintCommentInsert insert(RequestComplaintCommentInsert request) {
		ResponseComplaintCommentInsert response = new ResponseComplaintCommentInsert();
		
		EntityComplaint entityComplaint = repositoryComplaint.findByCode(request.getCode().trim());
		
		if(entityComplaint == null) {
			response.listMessage.add("El código de seguimiento \"" + request.getCode().trim() + "\" no está registrado.");
			
			return response;
		}
		
		String idUser = request.getIdUser() == null ? null : request.getIdUser().trim();
		
		if(idUser != null && !idUser.isEmpty()) {
			EntityUser entityUser = repositoryUser.findById(idUser).orElse(null);
			
			if(entityUser == null) {
				response.listMessage.add("El usuario indicado no está registrado.");
				
				return response;
			}
		} else {
			idUser = null;
		}
		
		EntityComplaintComment entityComplaintComment = new EntityComplaintComment();
		
		entityComplaintComment.setIdComplaintcomment(UUID.randomUUID().toString());
		entityComplaintComment.setIdComplaint(entityComplaint.getIdComplaint());
		entityComplaintComment.setIdUser(idUser);
		entityComplaintComment.setDescription(request.getDescription().trim());
		entityComplaintComment.setCreatedAt(new java.sql.Date(new Date().getTime()));
		entityComplaintComment.setUpdatedAt(entityComplaintComment.getCreatedAt());
		
		repositoryComplaintComment.save(entityComplaintComment);
		
		response.setIdComplaintcomment(entityComplaintComment.getIdComplaintcomment());
		
		response.success();
		response.listMessage.add("Registro realizado correctamente.");
		
		return response;
	}
	
	public ResponseComplaintCommentGetByCode getByCode(String code) {
		ResponseComplaintCommentGetByCode response = new ResponseComplaintCommentGetByCode();
		
		EntityComplaint entityComplaint = repositoryComplaint.findByCode(code);
		
		if(entityComplaint == null) {
			response.listMessage.add("El código de seguimiento \"" + code + "\" no está registrado.");
			
			return response;
		}
		
		List<EntityComplaintComment> listEntityComplaintComment = repositoryComplaintComment.findListByIdComplaint(entityComplaint.getIdComplaint());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(EntityComplaintComment item: listEntityComplaintComment) {
			Map<String, String> data = new HashMap<>();
			
			EntityUser entityUser = item.getParentUser();
			
			data.put("idComplaintcomment", item.getIdComplaintcomment());
			data.put("code", entityComplaint.getCode());
			data.put("idUser", item.getIdUser() == null ? "" : item.getIdUser());
			data.put("personFullName", entityUser == null ? "" : entityUser.getFirstName() + " " + entityUser.getSurName());
			data.put("description", item.getDescription());
			data.put("createdAt", dateFormat.format(item.getCreatedAt()));
			
			response.getListComplaintComment().add(data);
		}
		
		response.success();
		
		if(listEntityComplaintComment.isEmpty()) {
			response.listMessage.add("La queja no tiene comentarios registrados.");
		}
		
		return response;
	}
}
