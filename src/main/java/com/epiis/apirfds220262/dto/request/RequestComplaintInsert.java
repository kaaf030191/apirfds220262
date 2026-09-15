package com.epiis.apirfds220262.dto.request;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestComplaintInsert {
	@NotBlank(message = "El campo \"idOffice\" es requerido.")
	private String idOffice;
	
	private String idProfessor;
	
	private String personFullName;
	
	@NotBlank(message = "El campo \"complaintFullName\" es requerido.")
	private String complaintFullName;
	
	@NotBlank(message = "El campo \"description\" es requerido.")
	private String description;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "El campo \"issueDate\" es requerido.")
	private Date issueDate;
	
	private List<MultipartFile> files;
}