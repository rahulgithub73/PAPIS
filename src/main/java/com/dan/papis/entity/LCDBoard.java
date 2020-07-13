package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "LCD_board")
public class LCDBoard extends BaseEntity {

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

	@Column(name = "device_type_id")
	private Integer deviceTypeId;

	@Column(name = "board_IP_address")
	private String boardIPAddress;

	@Column(name = "board_MAC_address")
	private String boardMACAddress;

	@Column(name = "default_image")
	private String defaultImage;

	@Column(name = "data_change_seconds")
	private String dataChangeSeconds;

	@Column(name = "default_message")
	private String defaultMessage;

	@Column(name = "effect_speed")
	private String effectSpeed;

	@Column(name = "server_url")
	private String serverUrl;

	@Column(name = "is_welcome_message")
	private Boolean isWelcomeMessage;

	@Column(name = "is_train_route")
	private Boolean isTrainRoute;

	@Column(name = "is_running_info")
	private Boolean isRunningInfo;

	@Column(name = "is_slogans")
	private Boolean isSlogans;

	@Column(name = "effect_type")
	private String effectType;

	@Column(name = "status")
	private String status;

	@Transient
	private String periDeviceType;

	@Transient
	private String boardHardwareId;

	@Transient
	private String deviceTypeName;
}
