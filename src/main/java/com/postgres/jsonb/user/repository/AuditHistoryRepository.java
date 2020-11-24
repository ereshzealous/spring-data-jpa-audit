package com.postgres.jsonb.user.repository;

import com.postgres.jsonb.user.entity.AuditHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created on 13/November/2020 By Author Eresh, Gorantla
 **/
public interface AuditHistoryRepository extends JpaRepository<AuditHistory, Long>, JpaSpecificationExecutor<AuditHistory> {

	@Query(value = "SELECT entity_content ->> 'id' as id FROM audit_history where modified_date >= :fromDate and modified_date <= :toDate and entity_name = :entity " +
			"ORDER BY modified_date DESC", nativeQuery = true)
	List<String> findAuditHistoriesByModifiedDate(@Param("fromDate") ZonedDateTime fromDate, @Param("toDate") ZonedDateTime toDate, @Param("entity") String entity);
}