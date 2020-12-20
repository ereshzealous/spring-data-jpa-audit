package com.postgres.jsonb.user.model;

import com.postgres.jsonb.user.entity.UserDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 11/November/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
public class WSUserDetailsResponse {
	private Long id;
	private String name;
	private WSUserPreference preference;

	public WSUserDetailsResponse(UserDetails userDetails) {
		this.id = userDetails.getId();
		this.name = Stream.of(userDetails.getFirstName(), userDetails.getLastName()).filter(StringUtils::isNotBlank).collect(Collectors.joining(" "));
		//this.preference = new WSUserPreference(userDetails.getUserPreference());
	}
}