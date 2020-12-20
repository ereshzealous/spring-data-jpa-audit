package com.postgres.jsonb.user.validation;

/**
 * Created on 17/December/2020 By Author Eresh, Gorantla
 **/
public class JsonSchemaLoadingFailedException extends RuntimeException {

	public JsonSchemaLoadingFailedException(String message) {
		super(message);
	}

	public JsonSchemaLoadingFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}