package com.postgres.jsonb.user.entity;

import java.util.Random;

/**
 * Created on 16/December/2020 By Author Eresh, Gorantla
 **/
public class Main {

	public static void main(String[] args) {
		Random random = new Random();
		Double latitude = random.doubles(-360, 360).findFirst().getAsDouble();
		Double longitude = random.doubles(Math.round(latitude), latitude + 1).findFirst().getAsDouble();
		System.out.println("Latitude => " + latitude + " Longitude => " + longitude);
	}
}