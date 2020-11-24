package com.postgres.jsonb.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Created on 18/November/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WSAuditHistoryRequest {
	private String entityName;
	private String id;
	private ZonedDateTime fromDate;
	private ZonedDateTime toDate = ZonedDateTime.now();
	private String searchString;
	private Integer page = 0;
	private Integer size = 20;
}