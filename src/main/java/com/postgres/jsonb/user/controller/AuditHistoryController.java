package com.postgres.jsonb.user.controller;

import com.postgres.jsonb.user.model.WSAuditHistoryRequest;
import com.postgres.jsonb.user.model.WSAuditHistoryResponse;
import com.postgres.jsonb.user.service.AuditHistoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping("/history")
	public ResponseEntity<WSAuditHistoryResponse> getAuditHistoryById(@RequestBody WSAuditHistoryRequest request)  {
		return ResponseEntity.ok(auditHistoryService.getAuditHistoryOfUserId(request));
	}
}