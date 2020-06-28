package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "audio_settings")
public class AudioSettings extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column(name = "disable_audio_at_night")
	private Boolean disableAudioAtNight;

	@Column(name = "night_start_time")
	private String nightStartTime;

	@Column(name = "night_end_time")
	private String nightEndTime;

}
