package com.postgres.jsonb.user.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.postgres.jsonb.user.audit.Auditable;
import com.postgres.jsonb.user.audit.EntityListener;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Created on 11/November/2020 By Author Eresh, Gorantla
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_details")
@TypeDefs({
		          @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
          })
@EntityListeners({ AuditingEntityListener.class, EntityListener.class })
@ToString
public class UserDetails extends Auditable<Long> implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "security_number")
	private String securityNumber;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "country")
	private String country;

	@Column(name = "created_at", updatable = false)
	private ZonedDateTime createdAt;

	@Column(name = "updated_at", insertable = false)
	@UpdateTimestamp
	private ZonedDateTime updatedAt;

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb", name = "details")
	private JsonNode details;
}