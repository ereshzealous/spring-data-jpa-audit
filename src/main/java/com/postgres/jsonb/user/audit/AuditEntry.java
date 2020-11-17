package com.postgres.jsonb.user.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgres.jsonb.user.config.CustomObjectMapper;

import java.io.Serializable;

public class AuditEntry  implements Serializable  {

	private static final long serialVersionUID = 7445344365317803724L;

	private String name;
	
	private Object content;
	
	private Action action;

	private String data;
	
	public AuditEntry(String name, Object content, Action action){
		this.withAction(action).withName(name).withContent(content).withData(content);
	}

	public String getName() {
		return name;
	}

	public AuditEntry withName(String name) {
		this.name = name;
		return this;
	}

	public String getData() {
		return data;
	}

	public Object getContent() {
		return content;
	}

	public AuditEntry withContent(Object content) {
		this.content = content;
		return this;
	}

	public Action getAction() {
		return action;
	}

	public AuditEntry withAction(Action action) {
		this.action = action;
		return this;
	}

	public AuditEntry withData(Object content) {
		ObjectMapper objectMapper = new CustomObjectMapper();
		String data = null;
		try {
			data = objectMapper.writeValueAsString(content);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		this.data = data;
		return this;
	}
}
