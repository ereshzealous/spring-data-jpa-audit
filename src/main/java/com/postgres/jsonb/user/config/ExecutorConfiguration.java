package com.postgres.jsonb.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created on 17/November/2020 By Author Eresh, Gorantla
 **/
@Component
public class ExecutorConfiguration {
	@Bean("auditHistory")
	public ExecutorService auditHistoryExecutorService() {
		ExecutorService executorService = new ThreadPoolExecutor(50, 75, 5, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));
		return executorService;
	}
}