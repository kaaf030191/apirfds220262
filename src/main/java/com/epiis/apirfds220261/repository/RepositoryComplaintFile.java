package com.epiis.apirfds220262.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epiis.apirfds220262.entity.EntityComplaintFile;

@Repository
public interface RepositoryComplaintFile extends JpaRepository<EntityComplaintFile, String> {}