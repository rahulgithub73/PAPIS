package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "device_configuration")
public class DeviceConfiguration extends BaseEntity {

	@Id
	@Column(name = "device_id")
	private String deviceId;
	
	@Column(name = "device_name")
	private String deviceName;

	@Column(name = "zonal_railway")
	private String zonalRailway;

	@Column(name = "train_no")
	private String trainNo;

	@Column(name = "km_run")
	private Double kmRun;

	@Column(name = "coach_name")
	private String coachName;

	@Column(name = "coach_no")
	private String coachNo;

	@Column(name = "status")
	private String status;
	
	@Transient
	private String zonalRailwayDisplay;

}
