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
public enum Sport {
	CRICKET("Cricket"),
	SOCCER("Soccer"),
	RUGBY("RugBy"),
	KABADDI("Kabaddi"),
	SPRINTING("Sprinting"),
	SHOOTING("Shooting"),
	HOCKEY("Hockey"),
	BADMINTON("Badminton"),
	TENNIS("Tennis"),
	OTHERS("Others");
	private String sport;

	public static Sport generateSport() {
		Sport[] sports = Sport.values();
		Random random = new Random();
		final Integer index = random.ints(0, (sports.length - 1)).limit(1).findFirst().getAsInt();
		return sports[index];
	}
}