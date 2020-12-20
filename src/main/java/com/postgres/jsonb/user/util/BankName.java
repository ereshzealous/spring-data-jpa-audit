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
public enum BankName {
	SBI("SBI"),
	ICICI("ICICI"),
	HDFC("HDFC"),
	HSBC("HSBC"),
	INDUSIND("INDUS IND"),
	AXIS("Axis"),
	OTHERS("Others");
	private String bank;

	public static BankName generateBankName() {
		BankName[] bankNames = BankName.values();
		Random random = new Random();
		final Integer index = random.ints(0, (bankNames.length - 1)).limit(1).findFirst().getAsInt();
		return bankNames[index];
	}
}