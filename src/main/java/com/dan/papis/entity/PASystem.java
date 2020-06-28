package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "PA_system")
public class PASystem extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "device_name")
	private String deviceName;

	@Column(name = "device_type_id")
	private Integer deviceTypeId;

	@Column(name = "device_type_name")
	private String deviceTypeName;

	@Column(name = "status")
	private String status;

	@Column(name = "volume")
	private String volume;

	@Transient
	private String periDeviceType;

}
