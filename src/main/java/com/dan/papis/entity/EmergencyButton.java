package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "emergency_button")
public class EmergencyButton extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "device_name")
	private String deviceName;
	
	@Column(name = "device_type_id")
	private String deviceTypeId;
	
	@Column(name = "device_type_name")
	private String deviceTypeName;

	@Column(name = "audio_file")
	private String audioFile;
	
	@Column(name = "status")
	private String status;
	
	@Transient
	private String periDeviceType;

}
