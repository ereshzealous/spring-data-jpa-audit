package com.postgres.jsonb.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgres.jsonb.user.validation.JsonSchemaValidatingArgumentResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created on 16/November/2020 By Author Eresh, Gorantla
 **/
@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	private final ResourcePatternResolver resourcePatternResolver;

	public ApplicationConfig(ResourcePatternResolver resourcePatternResolver) {
		this.resourcePatternResolver = resourcePatternResolver;
	}

	@Bean(name = "jsonMapper")
	@Primary
	public ObjectMapper jsonMapper() {
		return new CustomObjectMapper();
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter(jsonMapper()));
	}

	/*@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new JsonSchemaValidatingArgumentResolver(jsonMapper(), resourcePatternResolver));
	}*/


}