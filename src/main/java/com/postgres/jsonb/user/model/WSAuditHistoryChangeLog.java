package com.postgres.jsonb.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created on 18/November/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WSAuditHistoryChangeLog {
	private String auditAction;
	private Map<String, Object> data = Collections.emptyMap();
	private List<String> changes = Collections.emptyList();
	private ZonedDateTime modifiedDate;
}