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
public enum DoctorQualification {
	MBBS("MBBS"),
	BMBS("BMBS"),
	MBChB("MBChB"),
	MBBCh("MBBCh"),
	MD("MD"),
	Dr_MuD("Dr.MuD"),
	Dr_Med("Dr.Med"),
	DO("DO"),
	MD_RES("MD Res"),
	DM("DM"),
	PhD("PhD"),
	DPhil("DPhil"),
	MCM("MCM"),
	MPHIL("Mphil");

	private String qualification;

	public static DoctorQualification generateDoctorQualification() {
		DoctorQualification[] doctorQualifications = DoctorQualification.values();
		Random random = new Random();
		final Integer index = random.ints(0, (doctorQualifications.length - 1)).limit(1).findFirst().getAsInt();
		return doctorQualifications[index];
	}
}