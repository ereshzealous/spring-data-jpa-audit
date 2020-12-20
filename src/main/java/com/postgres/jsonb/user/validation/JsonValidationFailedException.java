package com.postgres.jsonb.user.validation;

import com.networknt.schema.ValidationMessage;

import java.util.Set;

/**
 * Created on 17/December/2020 By Author Eresh, Gorantla
 **/
public class JsonValidationFailedException extends RuntimeException {
	private final Set<ValidationMessage> validationMessages;

	public JsonValidationFailedException(Set<ValidationMessage> validationMessages) {
		super("Json validation failed: " + validationMessages);
		this.validationMessages = validationMessages;
	}

	public Set<ValidationMessage> getValidationMessages() {
		return validationMessages;
	}

}