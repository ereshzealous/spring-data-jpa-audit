package com.postgres.jsonb.user.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

/**
 * Created on 10/December/2020 By Author Eresh, Gorantla
 **/
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Disability {

	VISION_IMPAIRMENT("Vision Impairment"),
	INTELLECTUAL_DISABILITY("Intellectual Disability"),
	MENTAL_HEALTH_CONDITIONS("Mental Health Conditions"),
	HEARING_IMPAIRMENT("Hearing Impairment"),
	PHYSICAL_DISABILITY("Physical Disability");

	private String value;

	public static Disability generateDisability() {
		Disability[] disabilities = Disability.values();
		Random random = new Random();
		final Integer index = random.ints(0, (disabilities.length - 1)).limit(1).findFirst().getAsInt();
		return disabilities[index];
	}

}