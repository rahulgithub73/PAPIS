package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "rake_composition")
public class RakeComposition {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

   @Column(name = "coach_name")
	private String coachName;
	
	@Column(name = "device_id")
	private Long deviceId;
	
	@Column(name = "coach_id")
	private String coachId;
	
	@Column(name = "device_available")
	private boolean  deviceAvailable;
	
	@Column(name = "sequence_number")
	private Integer sequenceNumber;


}
