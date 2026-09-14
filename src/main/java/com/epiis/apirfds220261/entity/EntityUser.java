package com.epiis.apirfds220262.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tuser")
@Setter
@Getter
public class EntityUser {
	@Id
	@Column(name = "idUser")
	private String idUser;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "surName")
	private String surName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "createdAt")
	private Date createdAt;
	
	@Column(name = "updatedAt")
	private Date updatedAt;
	
	@OneToMany(mappedBy = "parentUser", cascade = CascadeType.ALL)
	private List<EntityComplaintComment> childComplaintComment;
}