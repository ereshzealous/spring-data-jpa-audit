package com.postgres.jsonb.user.entity;

import com.postgres.jsonb.user.util.FrequencyEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created on 11/November/2020 By Author Eresh, Gorantla
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference {
	private Boolean emailFlag = true;
	private Boolean smsFlag = false;
	private Boolean pushFlag = false;
	private String frequency = FrequencyEnum.WEEKLY.getFrequency();
}