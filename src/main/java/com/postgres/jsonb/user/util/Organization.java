package com.postgres.jsonb.user.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

/**
 * Created on 16/December/2020 By Author Eresh, Gorantla
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Organization {
	THOUGHT_WORKS("Thought Works"),
	CUBIC_TRANSPORTATION_SYSTEMS("Cubic Transportation Systems"),
	IBM("IBM"),
	OTHERS("Others");

	private String organization;

	public static Organization generateOrganization() {
		Organization[] organizations = Organization.values();
		Random random = new Random();
		final Integer index = random.ints(0, (organizations.length - 1)).limit(1).findFirst().getAsInt();
		return organizations[index];
	}
}