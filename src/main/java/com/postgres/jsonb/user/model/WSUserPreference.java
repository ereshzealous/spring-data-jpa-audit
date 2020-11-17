package com.postgres.jsonb.user.model;

import com.postgres.jsonb.user.entity.UserPreference;
import com.postgres.jsonb.user.util.FrequencyEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 11/November/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
public class WSUserPreference {
	private Boolean emailFlag = true;
	private Boolean smsFlag = false;
	private Boolean pushFlag = false;
	private String frequency = FrequencyEnum.WEEKLY.getFrequency();

	public WSUserPreference(UserPreference preference) {
		this.emailFlag = preference.getEmailFlag();
		this.smsFlag = preference.getSmsFlag();
		this.pushFlag = preference.getPushFlag();
		this.frequency = preference.getFrequency();
	}
}