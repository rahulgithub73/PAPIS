package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "device_configuration")
public class DeviceConfiguration extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long deviceId;
	
	@Column(name = "zonal_code")
	private String zonalCode;
	
	@Column(name = "year")
	private String year;
	
	@Column(name = "coach_serial_number")
	private String coachSerialNumber;
	
	@Column(name = "optional_info")
	private String optionalInfo;
	
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
	
	@Column(name = "device_type")
	private String deviceType;
	
	@Transient
	private String zonalRailwayDisplay;

}
