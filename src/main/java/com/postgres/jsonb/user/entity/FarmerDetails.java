package com.postgres.jsonb.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created on 16/December/2020 By Author Eresh, Gorantla
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerDetails {
	private String crop;
	private String farmSize;
	private List<String> cultivationSeasons;
	private Double latitude;
	private Double longitude;
}