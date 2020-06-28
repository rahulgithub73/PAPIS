package com.dan.papis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.papis.entity.AudioSettings;

public interface AudioSettingsRepo extends JpaRepository<AudioSettings, Long> {

}
