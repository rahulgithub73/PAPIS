package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "intensity_setting")
public class IntensitySetting extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column(name = "intensity_type")
	private String intensityType;

	@Column(name = "night_intensity")
	private String nightIntensity;

	@Column(name = "night_start_time")
	private String nightStartTime;

	@Column(name = "night_end_time")
	private String nightEndTime;

	@Column(name = "day_intensity")
	private String dayIntensity;

}
