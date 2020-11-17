package com.postgres.jsonb.user.controller;

import com.postgres.jsonb.user.entity.AuditHistory;
import com.postgres.jsonb.user.service.AuditHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 16/November/2020 By Author Eresh, Gorantla
 **/
@RestController
@RequestMapping("/api/audit")
public class AuditHistoryController {

	final AuditHistoryService auditHistoryService;

	public AuditHistoryController(AuditHistoryService auditHistoryService) {
		this.auditHistoryService = auditHistoryService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuditHistory> getAuditHistoryById(@PathVariable Long id) {
		return ResponseEntity.ok(auditHistoryService.getAuditHistory(id));
	}
}