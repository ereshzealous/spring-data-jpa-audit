package com.postgres.jsonb;

import com.postgres.jsonb.user.audit.AuditorAwareImpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

public class PostgresJsonbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostgresJsonbApplication.class, args);
	}


}
