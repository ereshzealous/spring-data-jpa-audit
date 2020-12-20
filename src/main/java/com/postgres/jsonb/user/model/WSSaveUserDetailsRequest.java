package com.postgres.jsonb.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 17/December/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
public class WSSaveUserDetailsRequest {
	private Long id;

	@NotBlank(message = "firstName Can not be blank")
	private String firstName;

	@NotBlank(message = "lastName Can not be blank")
	private String lastName;

	@NotBlank(message = "securityNumber Can not be blank")
	private String securityNumber;

	@NotBlank(message = "contactNumber Can not be blank")
	private String contactNumber;

	@NotBlank(message = "country Can not be blank")
	private String country;

	private Map<String, Object> details = new HashMap<>();
}