package com.dan.papis.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PeriphralDevices {

	private Long id;

	private Long deviceId;

	private Integer deviceTypeId;
	
	private String deviceTypeName;

	private String boardIPAddress;
	
	private String name;

	private String status;
	
	private String deviceIdDisplay;

	private LocalDateTime lastModifiedDate;

	private String lastModified;

}
