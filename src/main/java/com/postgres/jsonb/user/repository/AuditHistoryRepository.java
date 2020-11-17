package com.postgres.jsonb.user.repository;

import com.postgres.jsonb.user.entity.AuditHistory;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 13/November/2020 By Author Eresh, Gorantla
 **/
public interface AuditHistoryRepository extends JpaRepository<AuditHistory, Long> {

}