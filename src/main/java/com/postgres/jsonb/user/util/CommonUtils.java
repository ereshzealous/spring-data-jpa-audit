package com.postgres.jsonb.user.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created on 24/November/2020 By Author Eresh, Gorantla
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

	public static String maskSecureInformation(String data, Integer radius) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		radius = radius == null || NumberUtils.INTEGER_ZERO.equals(radius) ? NumberUtils.INTEGER_ONE : radius;
		int length = data.length() - data.length() / radius;
		String value = data.substring(0, length);
		String result = value.replaceAll("[A-Za-z0-9]", "X") + data.substring(length);
		return result;
	}
}