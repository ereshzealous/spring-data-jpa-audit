package com.postgres.jsonb.user.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgres.jsonb.user.audit.Action;
import com.postgres.jsonb.user.audit.AuditEntry;
import com.postgres.jsonb.user.config.CustomObjectMapper;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name="audit_history")
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({
		          @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
          })
@DynamicInsert
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditHistory implements Serializable {

	private static final long serialVersionUID = 7237091231517057323L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "entity_name")
	private String name;

	@Column(name = "action_performed")
	@Enumerated(EnumType.STRING)
	private Action action;

	@Column(name = "entity_content", columnDefinition = "jsonb")
	@Type(type = "jsonb")
	private JsonNode content;

	@CreatedBy
	@Column(name = "modified_by")
	private Long modifiedBy;

	@CreationTimestamp
	@Column(name = "modified_date")
	private ZonedDateTime modifiedDate;

	public AuditHistory(AuditEntry entry) {
		withName(entry.getName()).withAction(entry.getAction());
	}

	private JsonNode generateEntityContent(Object object) {
		ObjectMapper objectMapper = new CustomObjectMapper();
		UserDetails userDetails = (UserDetails) object;
		JsonNode jsonNode = objectMapper.convertValue(userDetails, JsonNode.class);
		return jsonNode;
	}

	public AuditHistory withName(String name) {
		this.name = name;
		return this;
	}

	public AuditHistory withAction(Action action) {
		this.action = action;
		return this;
	}

	public JsonNode withContent(JsonNode content) {
		this.content = content;
		return this.content;
	}
}