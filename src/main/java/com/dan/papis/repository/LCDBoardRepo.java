package com.dan.papis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.papis.entity.LCDBoard;

public interface LCDBoardRepo extends JpaRepository<LCDBoard, Long> {
	List<LCDBoard> findByDeviceId(String deviceId);

}
