package com.dan.papis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.papis.entity.PASystem;

public interface PASystemRepo extends JpaRepository<PASystem, Long> {
	List<PASystem> findByDeviceId(Long deviceId);

}
