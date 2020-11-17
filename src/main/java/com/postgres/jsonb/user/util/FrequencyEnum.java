package com.postgres.jsonb.user.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created on 11/November/2020 By Author Eresh, Gorantla
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum  FrequencyEnum {

	DAILY(1, "Daily"), WEEKLY(2, "Weekly"), BIWEEKLY(3, "Biweekly"), MONTHLY(4, "Monthly"),
	BIMONTHLY(5, "BiMonthly"), QUARTERLY(6, "Quarterly");

	private Integer id;
	private String frequency;


}