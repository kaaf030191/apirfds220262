package com.epiis.apirfds220262.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epiis.apirfds220262.entity.EntityProfessor;

@Repository
public interface RepositoryProfessor extends JpaRepository<EntityProfessor, String> {}