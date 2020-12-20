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
public enum Designation {
	TRAINEE("Trainee"),
	SOFTWARE_ENGINEER("Software Engineer"),
	SR_SOFTWARE_ENGINEER("Sr Software Engineer"),
	PRINCIPAL_ENGINEER("Principal Engineer"),
	TECH_LEAD("Tech Lead"),
	PROJECT_MANAGER("Project Manager"),
	MANAGER("Manager"),
	QA_LEAD("QA Lead"),
	HR("HR");

	private String designation;

	public static Designation generateDesignation() {
		Designation[] designations = Designation.values();
		Random random = new Random();
		final Integer index = random.ints(0, (designations.length - 1)).limit(1).findFirst().getAsInt();
		return designations[index];
	}
}