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
public enum Crop {
	MAIZE("Maize"),
	CORN("Corn"),
	RICE("Rice"),
	WHEAT("Wheat"),
	VEGETABLES("vegetables"),
	SEASONAL_FRUITS("Seasonal Fruits");

	private String crop;

	public static Crop generateCrop() {
		Crop[] crops = Crop.values();
		Random random = new Random();
		final Integer index = random.ints(0, (crops.length - 1)).limit(1).findFirst().getAsInt();
		return crops[index];
	}
}