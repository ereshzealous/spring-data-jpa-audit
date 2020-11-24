package com.postgres.jsonb.user.model;

/**
 * Created on 24/November/2020 By Author Eresh, Gorantla
 **/
public class Main {

	public static void main(String[] args) {
		String input = "6013838483";
		int length = input.length() - input.length()/3;
		String s = input.substring(0, length);
		String res = s.replaceAll("[A-Za-z0-9]", "X") + input.substring(length);
		System.out.println(res);
	}
}