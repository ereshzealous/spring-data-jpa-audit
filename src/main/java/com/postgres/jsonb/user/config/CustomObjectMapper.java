package com.postgres.jsonb.user.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created on 16/November/2020 By Author Eresh, Gorantla
 **/
public class CustomObjectMapper extends ObjectMapper {

	public CustomObjectMapper() {
		super();
		//setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		Jackson8Module module = new Jackson8Module();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
		                                                   .withZone(ZoneId.of("UTC"));
		module.addStringSerializer(ZonedDateTime.class, (val) -> val.format(timeFormatter));
		module.addStringDeserializer(ZonedDateTime.class, (val) -> ZonedDateTime.parse(val, timeFormatter));

		DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
		                                                        .withZone(ZoneId.of("UTC"));
		module.addStringDeserializer(LocalDate.class, (val) -> LocalDate.parse(val, localDateFormatter));
		module.addStringSerializer(LocalDate.class, (val) -> val.format(localDateFormatter));

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		module.addStringSerializer(Date.class, (val) -> val.toInstant()
		                                                   .atZone(ZoneId.of("UTC"))
		                                                   .toLocalDateTime()
		                                                   .format(dateFormatter));
		registerModule(module);
	}
}