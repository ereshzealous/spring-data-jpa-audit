package com.postgres.jsonb.user.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

/**
 * Created on 10/December/2020 By Author Eresh, Gorantla
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum FarmSize {
	LESS_THAN_1("less_than_1", "< 1"),
	BETWEEN_1_2("between_1_2", "1-2"),
	BETWEEN_2_3("between_2_3", "2-3"),
	BETWEEN_3_4("between_3_4", "3-4"),
	GREATER_THAN_4("greater_than_4", "> 4");

	private String key;
	private String value;

	public static FarmSize generateFarmSize() {
		FarmSize[] farmSizes = FarmSize.values();
		Random random = new Random();
		final Integer index = random.ints(0, (farmSizes.length - 1)).limit(1).findFirst().getAsInt();
		return farmSizes[index];
	}
}