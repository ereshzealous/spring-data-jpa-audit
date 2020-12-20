package com.postgres.jsonb.settings.repository;

import com.postgres.jsonb.settings.entity.Settings;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created on 18/December/2020 By Author Eresh, Gorantla
 **/
public interface SettingsRepository extends JpaRepository<Settings, Integer> {

	public List<Settings> findByEntityNameAndCountry(String entity, String country);
}