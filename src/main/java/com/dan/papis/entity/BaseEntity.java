package com.dan.papis.entity;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity {

	private String createdBy;

	private String lastModifiedBy;

	private LocalDateTime createdDate;

	private LocalDateTime lastModifiedDate;
	
	private String lastModified;

}
