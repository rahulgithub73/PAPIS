package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "LED_board")
public class LEDBoard extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "device_id")
	private Long deviceId;
	
	@Column(name = "vendor_code")
	private String vendorCode;
	
	@Column(name = "year")
	private String year;
	
	@Column(name = "unique_serial_number")
	private String uniqueSerialNumber;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "device_type_id")
	private Integer deviceTypeId;
	
	@Column(name = "device_type_name")
	private String deviceTypeName;

	@Column(name = "board_IP_address")
	private String boardIPAddress;

	@Column(name = "board_MAC_address")
	private String boardMACAddress;

	@Column(name = "default_message")
	private String defaultMessage;

	@Column(name = "effect_type")
	private String effectType;

	@Column(name = "effect_speed")
	private String effectSpeed;

	@Column(name = "data_change_seconds")
	private String dataChangeSeconds;
	
	@Transient
	private String periDeviceType;
	
	@Transient
	private String boardHardwareId;
	

}
