package com.postgres.jsonb.user.util;

import com.postgres.jsonb.user.entity.FarmerDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 24/November/2020 By Author Eresh, Gorantla
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

	private static final Supplier<String> mobileSupplier =
			() -> String.format("%01d%01d%01d%01d%01d%01d%01d0%01d%01d%01d", ThreadLocalRandom.current().nextInt(6, 9),
			                    ThreadLocalRandom.current().nextInt(9), ThreadLocalRandom.current().nextInt(9),
			                    ThreadLocalRandom.current().nextInt(9), ThreadLocalRandom.current().nextInt(9),
			                    ThreadLocalRandom.current().nextInt(9), ThreadLocalRandom.current().nextInt(9),
			                    ThreadLocalRandom.current().nextInt(9), ThreadLocalRandom.current().nextInt(9),
			                    ThreadLocalRandom.current().nextInt(9));

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

	public static Boolean generateRandomBoolean() {
		Random random = new Random();
		Integer value = random.ints(0, 100).limit(1).findFirst().getAsInt();
		return value % 2 == 0;
	}

	private static int createRandomIntBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	public static ZonedDateTime createRandomDate(int startYear, int endYear) {
		int day = createRandomIntBetween(1, 28);
		int month = createRandomIntBetween(1, 12);
		int hour = createRandomIntBetween(0, 23);
		int minutes = createRandomIntBetween(1, 59);
		int seconds = createRandomIntBetween(1, 59);
		int nanoSeconds = createRandomIntBetween(100, 900);
		int year = createRandomIntBetween(startYear, endYear);
		LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minutes, seconds, nanoSeconds);
		return localDateTime.atZone(ZoneId.systemDefault());
	}

	public static LocalDate createDateOfBirth(int startYear, int endYear) {
		int year = createRandomIntBetween(startYear, endYear);
		LocalDate localDate = LocalDate.of(year, createRandomIntBetween(1, 12), createRandomIntBetween(1, 28));
		return localDate;
	}

	public static Integer generateRandomNumber(Integer maxValue) {
		Random random = new Random();
		return random.ints(1, maxValue).limit(1).findFirst().getAsInt();
	}

	public static List<String> generateAlternateContacts(String country) {
		String code = "+91";
		List<String> values = new ArrayList<>();
		if ("USA".equalsIgnoreCase(country)) {
			code = "+1";
		}
		if ("Australia".equalsIgnoreCase(country)) {
			code = "+61";
		}
		Random random = new Random();
		Integer count = random.ints(0, 3).limit(1).findFirst().getAsInt();
		for (int index = 0; index < count; index++) {
			values.add(code + Stream.generate(mobileSupplier).limit(1).findFirst().orElse(null));
		}
		return values;
	}
	
	public static Map<String, Object> generateInfoForProfession(String profession) {
		Map<String, Object> objectMap = new HashMap<>();
		if (Profession.FARMER.getValue().equalsIgnoreCase(profession)) {
			objectMap.put("crop", Crop.generateCrop().getCrop());
			objectMap.put("farmSize", FarmSize.generateFarmSize().getValue());
			objectMap.put("cultivationSeasons", generateSeasons());
			Double latitude = generateRandomLatitude();
			objectMap.put("latitude", latitude);
			objectMap.put("longitude", generateLongitudeByLatitude(latitude));
		}
		if (Profession.IT_PROFESSIONAL.getValue().equalsIgnoreCase(profession)) {
			objectMap.put("designation", Designation.generateDesignation().getDesignation());
			objectMap.put("organization", Organization.generateOrganization().getOrganization());
			objectMap.put("employeeCode", StringUtils.upperCase(RandomStringUtils.randomAlphanumeric(10)));
		}
		if (Profession.ACTOR.getValue().equalsIgnoreCase(profession)) {
			objectMap.put("registrationNumber", StringUtils.upperCase(RandomStringUtils.randomAlphanumeric(10)));
		}
		if (Profession.ATHLETE.getValue().equals(profession)) {
			objectMap.put("sports", generateSports());
		}
		if (Profession.BANKER.getValue().equals(profession)) {
			objectMap.put("designation", BankRole.generateBankRole().getRole());
			objectMap.put("bank", BankName.generateBankName().getBank());
			objectMap.put("branchCode", StringUtils.upperCase(RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(5)));
			objectMap.put("employeeCode", StringUtils.upperCase(RandomStringUtils.randomAlphanumeric(10)));
		}
		if (Profession.DOCTOR.getValue().equalsIgnoreCase(profession)) {
			objectMap.put("qualification", generateDoctorQualifications());
			objectMap.put("licence", StringUtils.upperCase(RandomStringUtils.randomAlphanumeric(12)));
		}
		if (Profession.SINGER.getValue().equalsIgnoreCase(profession)) {
			objectMap.put("registrationNumber", StringUtils.upperCase(RandomStringUtils.randomAlphanumeric(10)));
		}
		return objectMap;
	}

	public static FarmerDetails generateFarmerDetails() {
		FarmerDetails farmerDetails = new FarmerDetails();
		farmerDetails.setFarmSize(FarmSize.generateFarmSize().getKey());
		farmerDetails.setCrop(Crop.generateCrop().getCrop());
		farmerDetails.setCultivationSeasons(generateSeasons());
		Double latitude = generateRandomLatitude();
		farmerDetails.setLatitude(latitude);
		farmerDetails.setLongitude(generateLongitudeByLatitude(latitude));
		return farmerDetails;
	}

	public static Double generateRandomLatitude() {
		Random random = new Random();
		return random.doubles(-360, 360).findFirst().getAsDouble();
	}

	public static Double generateLongitudeByLatitude(Double latitude) {
		Random random = new Random();
		return random.doubles(Math.round(latitude), latitude + 1).findFirst().getAsDouble();
	}

	public static List<String> generateHobbies() {
		List<String> hobbies = new ArrayList<>();
		Random random = new Random();
		Integer size = random.ints(0, 3).limit(1).findFirst().getAsInt();
		for (int index = 0; index < size; index++) {
			hobbies.add(Hobby.generateHobby().getHobby());
		}
		return hobbies.stream().distinct().collect(Collectors.toList());
	}

	public static List<String> generateSeasons() {
		List<String> seasons = new ArrayList<>();
		Random random = new Random();
		Integer size = random.ints(0, 3).limit(1).findFirst().getAsInt();
		for (int index = 0; index < size; index++) {
			seasons.add(Season.generateSeason().getSeason());
		}
		return seasons.stream().distinct().collect(Collectors.toList());
	}

	public static List<String> generateSports() {
		List<String> sports = new ArrayList<>();
		Random random = new Random();
		Integer size = random.ints(0, 3).limit(1).findFirst().getAsInt();
		for (int index = 0; index < size; index++) {
			sports.add(Sport.generateSport().getSport());
		}
		return sports.stream().distinct().collect(Collectors.toList());
	}

	public static List<String> generateDoctorQualifications() {
		List<String> qualifications = new ArrayList<>();
		Random random = new Random();
		Integer size = random.ints(0, 3).limit(1).findFirst().getAsInt();
		for (int index = 0; index < size; index++) {
			qualifications.add(DoctorQualification.generateDoctorQualification().getQualification());
		}
		return qualifications.stream().distinct().collect(Collectors.toList());
	}

	public static List<String> generateSiblingsInfo() {
		List<String> values = new ArrayList<>();
		Random random = new Random();
		Integer count = random.ints(0, 3).limit(1).findFirst().getAsInt();
		for (int index = 0; index < count; index++) {
			values.add(RandomStringUtils.randomAlphabetic(10));
		}
		return values;
	}

	private String generateMobileNumber() {
		return Stream.generate(mobileSupplier).limit(1).findFirst().orElse(null);
	}


}