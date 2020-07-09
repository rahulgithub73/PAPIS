package com.dan.papis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.papis.entity.IntensitySetting;

public interface IntensitySettingRepo extends JpaRepository<IntensitySetting, Long> {
	
	List<IntensitySetting> findByDeviceId(String deviceId);

}
