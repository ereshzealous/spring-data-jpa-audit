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

	@Query(value = "SELECT entity_content ->> 'id' AS id FROM audit_history WHERE modified_date >= :fromDate AND modified_date <= :toDate AND UPPER(entity_name) = :entity " +
			"ORDER BY modified_date DESC", nativeQuery = true)
	List<String> findAuditHistoriesByModifiedDate(@Param("fromDate") ZonedDateTime fromDate, @Param("toDate") ZonedDateTime toDate, @Param("entity") String entity);

	@Query(value = "SELECT entity_content ->> 'id' AS id FROM audit_history WHERE (UPPER(jsonb_extract_path_text(entity_content, 'firstName')) LIKE :query OR " +
			"UPPER(jsonb_extract_path_text(entity_content, 'lastName')) LIKE :query OR UPPER(jsonb_extract_path_text(entity_content, 'securityNumber')) LIKE :query) " +
			"AND UPPER(entity_name) LIKE :entity ORDER BY modified_date DESC", nativeQuery = true)
	List<String> findAuditHistoryByQuery(@Param("query") String query, @Param("entity") String entity);

	@Query(value = "SELECT entity_content ->> 'id' AS id FROM audit_history WHERE (UPPER(jsonb_extract_path_text(entity_content, 'firstName')) LIKE :query OR " +
			"UPPER(jsonb_extract_path_text(entity_content, 'lastName')) LIKE :query OR UPPER(jsonb_extract_path_text(entity_content, 'securityNumber')) LIKE :query) " +
			"AND UPPER(entity_name) LIKE :entity AND (modified_date >= :fromDate AND modified_date <= :toDate) ORDER BY modified_date DESC", nativeQuery = true)
	List<String> findAuditHistoryByQueryAndModifiedDate(@Param("query") String query, @Param("entity") String entity, @Param("fromDate") ZonedDateTime fromDate,
	                                                    @Param("toDate") ZonedDateTime toDate);
}