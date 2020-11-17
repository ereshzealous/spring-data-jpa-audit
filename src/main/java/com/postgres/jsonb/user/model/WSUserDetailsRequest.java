package com.postgres.jsonb.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 11/November/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
public class WSUserDetailsRequest {
	private Long id;
	private String firstName;
	private String lastName;
	private String securityNumber;
	private String contactNumber;
	private WSUserPreference preference;
}