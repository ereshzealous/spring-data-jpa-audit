package com.postgres.jsonb.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created on 24/November/2020 By Author Eresh, Gorantla
 **/
@Data
@NoArgsConstructor
public class WSUserSearchResponse {
	private Long totalRecords;
	private List<WSUserDetails> userDetails;
}