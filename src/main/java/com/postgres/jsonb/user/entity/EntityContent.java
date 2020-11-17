package com.postgres.jsonb.user.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 16/November/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY,
              property = "auditEntity",
              visible = true)
@JsonSubTypes({
		              @JsonSubTypes.Type(value = UserDetailsContent.class, name = EntityContent.AuditEntities.USER_DETAILS)
              })
public class EntityContent {

	protected String auditEntity;

	public interface AuditEntities {

		String USER_DETAILS = "UserDetails";
		String FARMER_DETAILS = "FarmerDetails";
	}

}