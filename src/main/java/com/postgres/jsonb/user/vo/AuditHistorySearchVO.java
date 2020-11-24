package com.postgres.jsonb.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created on 23/November/2020 By Author Eresh, Gorantla
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditHistorySearchVO {
	private List<String> ids;
	private String entityName;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private String searchString;

}