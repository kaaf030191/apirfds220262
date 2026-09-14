package com.epiis.apirfds220262.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epiis.apirfds220262.entity.EntitySuggestion;

@Repository
public interface RepositorySuggestion extends JpaRepository<EntitySuggestion, String> {
	EntitySuggestion findByCode(String code);
}