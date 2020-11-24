package com.postgres.jsonb.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * Created on 18/November/2020 By Author Eresh, Gorantla
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSAuditHistoryResponse {
	private List<WSAuditHistoryDetails> historyDetails = Collections.emptyList();
	private Long totalRecords;
}