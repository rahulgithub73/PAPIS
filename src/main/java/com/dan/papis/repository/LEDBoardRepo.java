package com.dan.papis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.papis.entity.LEDBoard;

public interface LEDBoardRepo extends JpaRepository<LEDBoard, Long> {
	List<LEDBoard> findByDeviceId(String deviceId);

	List<LEDBoard> findByDeviceTypeId(Integer deviceTypeId);

}
