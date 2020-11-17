package com.postgres.jsonb.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 16/November/2020 By Author Eresh, Gorantla
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceVO {
	private Boolean emailFlag = true;
	private Boolean smsFlag = false;
	private Boolean pushFlag = false;
	private String frequency;

	public UserPreferenceVO(UserPreference userPreference) {
		this.emailFlag = userPreference.getEmailFlag();
		this.frequency = userPreference.getFrequency();
		this.pushFlag = userPreference.getPushFlag();
		this.smsFlag = userPreference.getSmsFlag();
	}
}