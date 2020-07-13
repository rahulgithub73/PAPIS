package com.dan.papis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.papis.entity.Slogans;

public interface SlogansRepo extends JpaRepository<Slogans, Long> {
	List<Slogans> findByDeviceId(Long deviceId);

}
