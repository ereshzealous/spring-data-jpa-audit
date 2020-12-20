package com.postgres.jsonb.user.validation;

import com.networknt.schema.ValidationMessage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 17/December/2020 By Author Eresh, Gorantla
 **/
@ControllerAdvice
public class JsonValidationExceptionHandler {

	@ExceptionHandler(JsonValidationFailedException.class)
	public ResponseEntity<Map<String, Object>> onJsonValidationFailedException(JsonValidationFailedException ex) {
		List<String> messages = ex.getValidationMessages().stream()
		                          .map(ValidationMessage::getMessage)
		                          .collect(Collectors.toList());
		Map<String, Object> objectMap = new HashMap<>();
		objectMap.put("message", "Json Validation Failed");
		objectMap.put("details", messages);
		return ResponseEntity.badRequest().body(objectMap);
	}

}