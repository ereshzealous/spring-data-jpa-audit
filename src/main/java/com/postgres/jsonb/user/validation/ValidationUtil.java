package com.postgres.jsonb.user.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 17/December/2020 By Author Eresh, Gorantla
 **/
@Component
public class ValidationUtil {

	private final Map<String, JsonSchema> schemaCache;
	private final ResourcePatternResolver resourcePatternResolver;
	private final ObjectMapper objectMapper;

	public ValidationUtil(ResourcePatternResolver resourcePatternResolver, @Qualifier("jsonMapper") ObjectMapper objectMapper) {
		this.resourcePatternResolver = resourcePatternResolver;
		this.schemaCache = new ConcurrentHashMap<>();
		this.objectMapper = objectMapper;
	}

	public void validateUserAdditionalDetails(Map<String, Object> details, String country) {
		String path = null;
		path = "classpath:schema/user_details-" + country + ".json";
		JsonSchema schema = getJsonSchema(path);
		JsonNode jsonNode = objectMapper.convertValue(details, JsonNode.class);
		Set<ValidationMessage> validationResult = schema.validate(jsonNode);
		if (CollectionUtils.isNotEmpty(validationResult)) {
			throw new JsonValidationFailedException(validationResult);
		}
	}

	private JsonSchema getJsonSchema(String schemaPath) {
		return schemaCache.computeIfAbsent(schemaPath, path -> {
			Resource resource = resourcePatternResolver.getResource(path);
			if (!resource.exists()) {
				throw new JsonSchemaLoadingFailedException("Schema file does not exist, path: " + path);
			}
			JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
			try (InputStream schemaStream = resource.getInputStream()) {
				return schemaFactory.getSchema(schemaStream);
			} catch (Exception e) {
				throw new JsonSchemaLoadingFailedException("An error occurred while loading JSON Schema, path: " + path, e);
			}
		});
	}
}