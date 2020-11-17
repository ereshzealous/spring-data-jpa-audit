package com.postgres.jsonb.user.service;

import com.postgres.jsonb.user.entity.AuditHistory;
import com.postgres.jsonb.user.repository.AuditHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 16/November/2020 By Author Eresh, Gorantla
 **/
@Service
public class AuditHistoryService {

	final AuditHistoryRepository auditHistoryRepository;

	public AuditHistoryService(AuditHistoryRepository auditHistoryRepository) {
		this.auditHistoryRepository = auditHistoryRepository;
	}

	public AuditHistory getAuditHistory(Long id) {
		return auditHistoryRepository.getOne(id);
	}
}