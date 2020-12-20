package com.postgres.jsonb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PostgresJsonbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostgresJsonbApplication.class, args);
	}


}
