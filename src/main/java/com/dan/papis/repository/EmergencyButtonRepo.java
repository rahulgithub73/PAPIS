package com.dan.papis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.papis.entity.EmergencyButton;

public interface EmergencyButtonRepo extends JpaRepository<EmergencyButton, Long> {

}
