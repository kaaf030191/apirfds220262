package com.epiis.apirfds220262.business;

import java.util.Date;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epiis.apirfds220262.dto.request.RequestUserLogin;
import com.epiis.apirfds220262.dto.request.RequestUserRegister;
import com.epiis.apirfds220262.dto.response.ResponseUserAuthenticated;
import com.epiis.apirfds220262.dto.response.ResponseUserLogin;
import com.epiis.apirfds220262.dto.response.ResponseUserRegister;
import com.epiis.apirfds220262.entity.EntityUser;
import com.epiis.apirfds220262.repository.RepositoryUser;
import com.epiis.apirfds220262.security.JwtTokenService;
import com.epiis.apirfds220262.staticdata.EnumRole;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BusinessUser {
	private final RepositoryUser repositoryUser;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenService jwtTokenService;

	public BusinessUser(
		RepositoryUser repositoryUser,
		PasswordEncoder passwordEncoder,
		JwtTokenService jwtTokenService
	) {
		this.repositoryUser = repositoryUser;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenService = jwtTokenService;
	}

	public ResponseUserRegister register(RequestUserRegister request) {
		ResponseUserRegister response = new ResponseUserRegister();

		if(request.getFirstName() == null || request.getFirstName().isBlank()) {
			response.listMessage.add("El nombre es obligatorio.");
		}

		if(request.getSurName() == null || request.getSurName().isBlank()) {
			response.listMessage.add("El apellido es obligatorio.");
		}

		if(request.getEmail() == null || request.getEmail().isBlank()) {
			response.listMessage.add("El correo electrónico es obligatorio.");
		} else if(!isValidEmail(request.getEmail())) {
			response.listMessage.add("El correo electrónico no tiene un formato válido.");
		} else if(this.repositoryUser.existsByEmailIgnoreCase(request.getEmail().trim())) {
			response.listMessage.add("El correo electrónico ya se encuentra registrado.");
		}

		if(request.getPassword() == null || request.getPassword().isBlank()) {
			response.listMessage.add("La contraseña es obligatoria.");
		} else if(request.getPassword().length() < 6) {
			response.listMessage.add("La contraseña debe tener al menos 6 caracteres.");
		}

		if(!response.listMessage.isEmpty()) {
			return response;
		}

		EntityUser entityUser = new EntityUser();

		entityUser.setIdUser(UUID.randomUUID().toString());
		entityUser.setFirstName(request.getFirstName().trim());
		entityUser.setSurName(request.getSurName().trim());
		entityUser.setEmail(request.getEmail().trim().toLowerCase());
		entityUser.setPassword(this.passwordEncoder.encode(request.getPassword()));
		entityUser.setRole(EnumRole.ESTUDIANTE.toString());
		entityUser.setCreatedAt(new java.sql.Date(new Date().getTime()));
		entityUser.setUpdatedAt(entityUser.getCreatedAt());

		this.repositoryUser.save(entityUser);

		response.setIdUser(entityUser.getIdUser());
		response.setEmail(entityUser.getEmail());
		response.setRole(entityUser.getRole());

		response.success();
		response.listMessage.add("Registro realizado correctamente.");

		return response;
	}

	public ResponseUserLogin login(RequestUserLogin request) {
		ResponseUserLogin response = new ResponseUserLogin();

		if(request.getEmail() == null || request.getEmail().isBlank() || request.getPassword() == null || request.getPassword().isBlank()) {
			response.listMessage.add("El correo electrónico y la contraseña son obligatorios.");

			return response;
		}

		EntityUser entityUser = this.repositoryUser.findByEmailIgnoreCase(request.getEmail().trim());

		if(entityUser == null || !this.passwordEncoder.matches(request.getPassword(), entityUser.getPassword())) {
			response.listMessage.add("Las credenciales ingresadas son incorrectas.");

			return response;
		}

		response.setToken(this.jwtTokenService.generate(entityUser));
		response.setIdUser(entityUser.getIdUser());
		response.setFirstName(entityUser.getFirstName());
		response.setSurName(entityUser.getSurName());
		response.setEmail(entityUser.getEmail());
		response.setRole(entityUser.getRole());

		response.success();
		response.listMessage.add("Inicio de sesión correcto.");

		return response;
	}

	public ResponseUserAuthenticated getAuthenticated(HttpServletRequest request) {
		ResponseUserAuthenticated response = new ResponseUserAuthenticated();

		String header = request.getHeader("Authorization");

		if(header == null || !header.startsWith("Bearer ")) {
			response.listMessage.add("Sesión no iniciada.");

			return response;
		}

		EntityUser entityUser = this.repositoryUser.findById(this.jwtTokenService.extractIdUser(header.substring(7))).orElse(null);

		if(entityUser == null) {
			response.listMessage.add("Sesión no iniciada.");

			return response;
		}

		response.setIdUser(entityUser.getIdUser());
		response.setFirstName(entityUser.getFirstName());
		response.setSurName(entityUser.getSurName());
		response.setEmail(entityUser.getEmail());
		response.setRole(entityUser.getRole());

		response.success();
		response.listMessage.add("Sesión validada.");

		return response;
	}

	private boolean isValidEmail(String email) {
		return email != null && !email.isBlank() && email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
	}
}
