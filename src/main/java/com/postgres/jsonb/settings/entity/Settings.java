package com.postgres.jsonb.settings.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

/**
 * Created on 18/December/2020 By Author Eresh, Gorantla
 **/
@Entity
@Table(name = "settings")
@Getter
@Setter
@NoArgsConstructor
public class Settings {
	@Id
	private Integer id;

	@Column(name = "entity_name")
	private String entityName;

	@Column(name = "country")
	private String country;

	@Column(name = "mandatory")
	private Boolean mandatory;

	@Column(name = "param_name")
	private String paramName;

	@Column(name = "param_type")
	private String paramType;

	@Column(name = "conditional_property")
	private String conditionalProperty;

	@Column(name = "conditional_association")
	private String conditionalAssociation;

	@Column(name = "conditional_value")
	private String conditionalValue;

	@Column(name = "conditional_param_name")
	private String conditionalParamName;

	@Column(name = "conditional_param_type")
	private String conditionalParamType;

	@Column(name = "created_at")
	private ZonedDateTime createdAt;
}