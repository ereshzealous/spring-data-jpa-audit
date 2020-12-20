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
public enum Hobby {
	DRAWING("Drawing"),
	COOKING("Cooking"),
	TRAVELLING("Travelling"),
	SPORTS("Sports"),
	AT_LEISURE("AT Leisure"),
	READING("Reading"),
	MOVIES("Movies"),
	SLEEPING("Sleeping"),
	NOT_INTERESTED_TO_DISCLOSE("Not Interested to disclose");

	private String hobby;

	public static Hobby generateHobby() {
		Hobby[] hobbies = Hobby.values();
		Random random = new Random();
		final Integer index = random.ints(0, (hobbies.length - 1)).limit(1).findFirst().getAsInt();
		return hobbies[index];
	}
}