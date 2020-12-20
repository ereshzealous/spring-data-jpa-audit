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
public enum BankRole {
	BRANCH_MANAGER("Branch Manager"),
	CASHIER("Cashier"),
	GENERAL_MANAGER("General Manager"),
	ASST_GENERAL_MANAGER("Asst General Manager"),
	REPRESENTATIVE("Representative"),
	OTHERS("Others");

	private String role;

	public static BankRole generateBankRole() {
		BankRole[] bankRoles = BankRole.values();
		Random random = new Random();
		final Integer index = random.ints(0, (bankRoles.length - 1)).limit(1).findFirst().getAsInt();
		return bankRoles[index];
	}
}