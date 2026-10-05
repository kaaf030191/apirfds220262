package com.epiis.apirfds220262.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epiis.apirfds220262.entity.EntitySuggestion;

@Repository
public interface RepositorySuggestion extends JpaRepository<EntitySuggestion, String> {
	EntitySuggestion findByCode(String code);

	@Query(value = "SELECT s FROM EntitySuggestion s LEFT JOIN FETCH s.parentOffice WHERE (:status IS NULL OR s.status = :status) ORDER BY s.createdAt DESC", countQuery = "SELECT COUNT(s) FROM EntitySuggestion s WHERE (:status IS NULL OR s.status = :status)")
	List<EntitySuggestion> findAllForAdmin(@Param("status") String status);

	@Query("select s.status, count(s) from EntitySuggestion s group by s.status")
	List<Object[]> countGroupByStatus();
}
