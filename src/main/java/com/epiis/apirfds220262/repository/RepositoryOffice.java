package com.epiis.apirfds220262.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epiis.apirfds220262.entity.EntityOffice;

@Repository
public interface RepositoryOffice extends JpaRepository<EntityOffice, String> {}