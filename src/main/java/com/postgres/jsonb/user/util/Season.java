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
public enum Season {
	FALL("Fall"),
	WINTER("Winter"),
	SPRING("Spring"),
	SUMMER("Summer");
	
	private String season;

	public static Season generateSeason() {
		Season[] seasons = Season.values();
		Random random = new Random();
		final Integer index = random.ints(0, (seasons.length - 1)).limit(1).findFirst().getAsInt();
		return seasons[index];
	}
}