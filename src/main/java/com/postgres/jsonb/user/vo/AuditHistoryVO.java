package com.postgres.jsonb.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgres.jsonb.user.audit.Action;
import com.postgres.jsonb.user.config.CustomObjectMapper;
import com.postgres.jsonb.user.entity.AuditHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;

/**
 * Created on 19/November/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditHistoryVO {
	private Long id;
	private String entityName;
	private Action action;
	private Map<String, Object> content = Collections.emptyMap();
	private Long modifiedBy;
	private ZonedDateTime modifiedDate;

	@JsonIgnore
	public ObjectMapper getObjectMapper() {
		return new CustomObjectMapper();
	}

	public AuditHistoryVO(AuditHistory auditHistory) {
		this.id = auditHistory.getId();
		this.entityName = auditHistory.getName();
		this.action = auditHistory.getAction();
		this.content = getObjectMapper().convertValue(auditHistory.getContent(), new TypeReference<Map<String, Object>>(){});
		this.modifiedBy = auditHistory.getModifiedBy();
		this.modifiedDate = auditHistory.getModifiedDate();
	}
}