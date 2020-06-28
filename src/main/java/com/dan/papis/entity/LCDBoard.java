package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "LCD_board")
public class LCDBoard extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column(name = "board_hardware_id")
	private String boardHardwareId;

	@Column(name = "board_IP_address")
	private String boardIPAddress;

	@Column(name = "board_MAC_address")
	private String boardMACAddress;

	@Column(name = "default_image")
	private String defaultImage;

	@Column(name = "data_change_seconds")
	private String dataChangeSeconds;

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

}
