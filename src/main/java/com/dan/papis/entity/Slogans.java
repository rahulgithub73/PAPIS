package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Entity(name = "slogans")
public class Slogans extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column(name = "device_id")
	private Long deviceId;

	@Column(name = "device_type_id")
	private Integer deviceTypeId;

	@Column(name = "message_type_id")
	private Integer messageTypeId;

	@Column(name = "message_type")
	private String messageType;

	@Column(name = "message")
	private String message;

	@Column(name = "language")
	private String language;

	@Column(name = "audio_file")
	private String audioFile;

	@Transient
	private MultipartFile file;

	@Transient
	private String deviceTypeName;

}
