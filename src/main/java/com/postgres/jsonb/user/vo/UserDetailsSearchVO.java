package com.postgres.jsonb.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Created on 24/November/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsSearchVO {
	private String query;
	private ZonedDateTime fromDate;
	private ZonedDateTime toDate = ZonedDateTime.now();
	private Boolean emailPreference;
	private Boolean smsPreference;
	private Boolean pushPreference;
	private String frequency;
	private Integer page = 0;
	private Integer size = 20;
}