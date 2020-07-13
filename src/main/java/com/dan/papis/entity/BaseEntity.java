package com.dan.papis.entity;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity {

	private String lastModifiedBy;

	private LocalDateTime lastModifiedDate;

	private String lastModified;

	@Transient
	private String deviceIdDisplay;

}
