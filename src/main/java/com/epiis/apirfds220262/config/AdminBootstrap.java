package com.epiis.apirfds220262.config;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.epiis.apirfds220262.entity.EntityUser;
import com.epiis.apirfds220262.repository.RepositoryUser;
import com.epiis.apirfds220262.staticdata.EnumRole;

@Component
public class AdminBootstrap implements ApplicationRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminBootstrap.class);

	private final RepositoryUser repositoryUser;
	private final PasswordEncoder passwordEncoder;
	private final boolean enabled;
	private final String email;
	private final String password;
	private final String firstName;
	private final String surName;

	public AdminBootstrap(
		RepositoryUser repositoryUser,
		PasswordEncoder passwordEncoder,
		@Value("${app.bootstrap-admin.enabled:false}") boolean enabled,
		@Value("${app.bootstrap-admin.email:}") String email,
		@Value("${app.bootstrap-admin.password:}") String password,
		@Value("${app.bootstrap-admin.first-name:Encargado}") String firstName,
		@Value("${app.bootstrap-admin.sur-name:Sistema}") String surName
	) {
		this.repositoryUser = repositoryUser;
		this.passwordEncoder = passwordEncoder;
		this.enabled = enabled;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.surName = surName;
	}

	@Override
	public void run(ApplicationArguments args) {
		if(!this.enabled || this.email == null || this.email.isBlank() || this.password == null || this.password.isBlank()) {
			return;
		}

		if(this.repositoryUser.existsByRole(EnumRole.ENCARGADO.toString())) {
			return;
		}

		EntityUser entityUser = new EntityUser();

		entityUser.setIdUser(UUID.randomUUID().toString());
		entityUser.setFirstName(this.firstName);
		entityUser.setSurName(this.surName);
		entityUser.setEmail(this.email.trim().toLowerCase());
		entityUser.setPassword(this.passwordEncoder.encode(this.password));
		entityUser.setRole(EnumRole.ENCARGADO.toString());
		entityUser.setCreatedAt(new java.sql.Date(new Date().getTime()));
		entityUser.setUpdatedAt(entityUser.getCreatedAt());

		this.repositoryUser.save(entityUser);

		LOGGER.warn("Usuario Encargado inicial creado ({}). Desactive app.bootstrap-admin.enabled en produccion.", entityUser.getEmail());
	}
}
