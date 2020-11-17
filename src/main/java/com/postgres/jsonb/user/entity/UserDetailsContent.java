package com.postgres.jsonb.user.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created on 16/November/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "firstName", "lastName", "securityNumber", "contactNumber", "userPreference", "createdAt", "updatedAt"})
public class UserDetailsContent extends EntityContent {
	private Long id;
	private String firstName;
	private String lastName;
	private String securityNumber;
	private String contactNumber;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
	private UserPreference userPreference;

	public UserDetailsContent(UserDetails userDetails) {
		this.auditEntity = AuditEntities.USER_DETAILS;
		this.contactNumber = userDetails.getContactNumber();
		this.createdAt = userDetails.getCreatedAt();
		this.firstName = userDetails.getFirstName();
		this.lastName = userDetails.getLastName();
		this.id = userDetails.getId();
		this.securityNumber = userDetails.getSecurityNumber();
		this.updatedAt = userDetails.getUpdatedAt();
		this.userPreference = userDetails.getUserPreference();
	}
}