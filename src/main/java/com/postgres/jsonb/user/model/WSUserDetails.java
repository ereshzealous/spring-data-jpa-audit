package com.postgres.jsonb.user.model;

import com.postgres.jsonb.user.entity.UserDetails;
import com.postgres.jsonb.user.util.CommonUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Created on 24/November/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
public class WSUserDetails {
	private Long id;
	private String firstName;
	private String lastName;
	private String securityNumber;
	private String contactNumber;
	private WSUserPreference preference;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;

	public WSUserDetails(UserDetails userDetails) {
		this.id = userDetails.getId();
		this.firstName = userDetails.getFirstName();
		this.lastName = userDetails.getLastName();
		this.securityNumber = CommonUtils.maskSecureInformation(userDetails.getSecurityNumber(), 3);
		this.contactNumber = CommonUtils.maskSecureInformation(userDetails.getContactNumber(), 3);
		this.createdAt = userDetails.getCreatedAt();
		this.updatedAt = userDetails.getUpdatedAt();
		//this.preference = new WSUserPreference(userDetails.getUserPreference());
	}
}