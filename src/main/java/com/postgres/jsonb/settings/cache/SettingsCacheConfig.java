package com.postgres.jsonb.settings.cache;

import com.postgres.jsonb.settings.entity.Settings;
import com.postgres.jsonb.settings.repository.SettingsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 18/December/2020 By Author Eresh, Gorantla
 **/
@Component
@CacheConfig(cacheNames = "settings")
public class SettingsCacheConfig {

	private final SettingsRepository settingsRepository;

	public SettingsCacheConfig(SettingsRepository settingsRepository) {
		this.settingsRepository = settingsRepository;
	}

	@Cacheable
	public List<Settings> getSettingsByEntityAndCountry(String entity, String country) {
		return settingsRepository.findByEntityNameAndCountry(entity, country);
	}
}