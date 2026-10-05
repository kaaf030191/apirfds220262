package com.epiis.apirfds220262.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epiis.apirfds220262.entity.EntityComplaint;

@Repository
public interface RepositoryComplaint extends JpaRepository<EntityComplaint, String> {
	EntityComplaint findByCode(String code);

	@Query(value = "SELECT c FROM EntityComplaint c LEFT JOIN FETCH c.parentOffice WHERE (:status IS NULL OR c.status = :status) ORDER BY c.createdAt DESC", countQuery = "SELECT COUNT(c) FROM EntityComplaint c WHERE (:status IS NULL OR c.status = :status)")
	List<EntityComplaint> findAllForAdmin(@Param("status") String status);

	@Query("select c.status, count(c) from EntityComplaint c group by c.status")
	List<Object[]> countGroupByStatus();
}
