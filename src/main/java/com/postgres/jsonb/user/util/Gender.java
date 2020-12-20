package com.postgres.jsonb.user.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

/**
 * Created on 09/December/2020 By Author Eresh, Gorantla
 **/
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Gender {
	MALE("Male"),
	FEMALE("Female"),
	CANNOT_SAY("Can not Say");
	private String gender;

	public static Gender generateRandomGender() {
		Gender[] genders = Gender.values();
		Random random = new Random();
		final Integer index = random.ints(0, (genders.length - 1)).limit(1).findFirst().getAsInt();
		return genders[index];
	}
}