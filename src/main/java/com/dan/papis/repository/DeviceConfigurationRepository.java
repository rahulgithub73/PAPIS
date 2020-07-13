package com.dan.papis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.papis.entity.DeviceConfiguration;

public interface DeviceConfigurationRepository extends JpaRepository<DeviceConfiguration, Long>{
	
	List<DeviceConfiguration> findByDeviceType(String deviceType);

}
