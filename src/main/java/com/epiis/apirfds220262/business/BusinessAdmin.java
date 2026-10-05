package com.epiis.apirfds220262.business;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.epiis.apirfds220262.dto.request.RequestAdminGetAll;
import com.epiis.apirfds220262.dto.request.RequestAdminUpdateStatus;
import com.epiis.apirfds220262.dto.response.ResponseAdminGetAll;
import com.epiis.apirfds220262.dto.response.ResponseAdminUpdateStatus;
import com.epiis.apirfds220262.entity.EntityComplaint;
import com.epiis.apirfds220262.entity.EntitySuggestion;
import com.epiis.apirfds220262.repository.RepositoryComplaint;
import com.epiis.apirfds220262.repository.RepositoryComplaintComment;
import com.epiis.apirfds220262.repository.RepositorySuggestion;
import com.epiis.apirfds220262.staticdata.EnumProcess;

@Service
public class BusinessAdmin {
	private final RepositoryComplaint repositoryComplaint;
	private final RepositorySuggestion repositorySuggestion;
	private final RepositoryComplaintComment repositoryComplaintComment;

	public BusinessAdmin(
		RepositoryComplaint repositoryComplaint,
		RepositorySuggestion repositorySuggestion,
		RepositoryComplaintComment repositoryComplaintComment
	) {
		this.repositoryComplaint = repositoryComplaint;
		this.repositorySuggestion = repositorySuggestion;
		this.repositoryComplaintComment = repositoryComplaintComment;
	}

	public ResponseAdminGetAll complaintGetAll(RequestAdminGetAll request) {
		ResponseAdminGetAll response = new ResponseAdminGetAll();

		String status = normalizeStatus(request == null ? null : request.getStatus());

		if(request != null && request.getStatus() != null && !request.getStatus().isBlank() && status == null) {
			response.listMessage.add("El estado seleccionado no es válido.");

			return response;
		}

		Map<String, Long> counters = countComplaintByStatus();

		applyCounters(response, counters);

		for(EntityComplaint entityComplaint : this.repositoryComplaint.findAllForAdmin(status)) {
			Map<String, Object> data = new LinkedHashMap<>();

			data.put("idComplaint", entityComplaint.getIdComplaint());
			data.put("code", entityComplaint.getCode());
			data.put("personFullName", entityComplaint.getPersonFullName());
			data.put("description", entityComplaint.getDescription());
			data.put("issueDate", entityComplaint.getIssueDate() != null ? entityComplaint.getIssueDate().toString() : null);
			data.put("status", entityComplaint.getStatus());
			data.put("officeName", entityComplaint.getParentOffice() != null ? entityComplaint.getParentOffice().getName() : null);
			data.put("createdAt", entityComplaint.getCreatedAt() != null ? entityComplaint.getCreatedAt().toString() : null);
			data.put("updatedAt", entityComplaint.getUpdatedAt() != null ? entityComplaint.getUpdatedAt().toString() : null);
			data.put("totalComments", countComments(entityComplaint.getIdComplaint()));

			response.listData.add(data);
		}

		response.success();
		response.listMessage.add("Listado de quejas obtenido correctamente.");

		return response;
	}

	public ResponseAdminGetAll suggestionGetAll(RequestAdminGetAll request) {
		ResponseAdminGetAll response = new ResponseAdminGetAll();

		String status = normalizeStatus(request == null ? null : request.getStatus());

		if(request != null && request.getStatus() != null && !request.getStatus().isBlank() && status == null) {
			response.listMessage.add("El estado seleccionado no es válido.");

			return response;
		}

		Map<String, Long> counters = countSuggestionByStatus();

		applyCounters(response, counters);

		for(EntitySuggestion entitySuggestion : this.repositorySuggestion.findAllForAdmin(status)) {
			Map<String, Object> data = new LinkedHashMap<>();

			data.put("idSuggestion", entitySuggestion.getIdSuggestion());
			data.put("code", entitySuggestion.getCode());
			data.put("personFullName", entitySuggestion.getPersonFullName());
			data.put("description", entitySuggestion.getDescription());
			data.put("status", entitySuggestion.getStatus());
			data.put("officeName", entitySuggestion.getParentOffice() != null ? entitySuggestion.getParentOffice().getName() : null);
			data.put("createdAt", entitySuggestion.getCreatedAt() != null ? entitySuggestion.getCreatedAt().toString() : null);
			data.put("updatedAt", entitySuggestion.getUpdatedAt() != null ? entitySuggestion.getUpdatedAt().toString() : null);
			data.put("totalFiles", entitySuggestion.getChildSuggestionFile() == null ? 0 : entitySuggestion.getChildSuggestionFile().size());

			response.listData.add(data);
		}

		response.success();
		response.listMessage.add("Listado de sugerencias obtenido correctamente.");

		return response;
	}

	public ResponseAdminUpdateStatus complaintUpdateStatus(RequestAdminUpdateStatus request) {
		ResponseAdminUpdateStatus response = new ResponseAdminUpdateStatus();

		response.setIdParent(request.getIdParent());

		if(request.getIdParent() == null || request.getIdParent().isBlank()) {
			response.listMessage.add("El identificador de la queja es obligatorio.");

			return response;
		}

		EnumProcess process = EnumProcess.fromValue(request.getStatus());

		if(process == null) {
			response.listMessage.add("El estado seleccionado no es válido.");

			return response;
		}

		EntityComplaint entityComplaint = this.repositoryComplaint.findById(request.getIdParent()).orElse(null);

		if(entityComplaint == null) {
			response.listMessage.add("La queja solicitada no existe.");

			return response;
		}

		entityComplaint.setStatus(process.toString());
		entityComplaint.setUpdatedAt(new java.sql.Date(new Date().getTime()));

		this.repositoryComplaint.save(entityComplaint);

		response.setStatus(entityComplaint.getStatus());

		response.success();
		response.listMessage.add("Estado de la queja actualizado correctamente.");

		return response;
	}

	public ResponseAdminUpdateStatus suggestionUpdateStatus(RequestAdminUpdateStatus request) {
		ResponseAdminUpdateStatus response = new ResponseAdminUpdateStatus();

		response.setIdParent(request.getIdParent());

		if(request.getIdParent() == null || request.getIdParent().isBlank()) {
			response.listMessage.add("El identificador de la sugerencia es obligatorio.");

			return response;
		}

		EnumProcess process = EnumProcess.fromValue(request.getStatus());

		if(process == null) {
			response.listMessage.add("El estado seleccionado no es válido.");

			return response;
		}

		EntitySuggestion entitySuggestion = this.repositorySuggestion.findById(request.getIdParent()).orElse(null);

		if(entitySuggestion == null) {
			response.listMessage.add("La sugerencia solicitada no existe.");

			return response;
		}

		entitySuggestion.setStatus(process.toString());
		entitySuggestion.setUpdatedAt(new java.sql.Date(new Date().getTime()));

		this.repositorySuggestion.save(entitySuggestion);

		response.setStatus(entitySuggestion.getStatus());

		response.success();
		response.listMessage.add("Estado de la sugerencia actualizado correctamente.");

		return response;
	}

	public void applyCounters(ResponseAdminGetAll response, Map<String, Long> counters) {
		response.setTotalPending(counters.getOrDefault(EnumProcess.PENDING.toString(), 0L));
		response.setTotalSeen(counters.getOrDefault(EnumProcess.SEEN.toString(), 0L));
		response.setTotalRefused(counters.getOrDefault(EnumProcess.REFUSED.toString(), 0L));
		response.setTotalCoordination(counters.getOrDefault(EnumProcess.COORDINATION.toString(), 0L));
		response.setTotalClose(counters.getOrDefault(EnumProcess.CLOSE.toString(), 0L));
	}

	private long countComments(String idComplaint) {
		return this.repositoryComplaintComment.countByIdComplaint(idComplaint);
	}

	private Map<String, Long> countComplaintByStatus() {
		return toCounters(this.repositoryComplaint.countGroupByStatus());
	}

	private Map<String, Long> countSuggestionByStatus() {
		return toCounters(this.repositorySuggestion.countGroupByStatus());
	}

	private Map<String, Long> toCounters(List<Object[]> rows) {
		Map<String, Long> counters = new LinkedHashMap<>();

		for(EnumProcess process : EnumProcess.values()) {
			counters.put(process.toString(), 0L);
		}

		for(Object[] row : rows) {
			counters.put(String.valueOf(row[0]), ((Number) row[1]).longValue());
		}

		return counters;
	}

	private String normalizeStatus(String status) {
		if(status == null || status.isBlank()) {
			return null;
		}

		EnumProcess process = EnumProcess.fromValue(status);

		return process == null ? null : process.toString();
	}
}
