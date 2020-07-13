package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "tbl_trainmaster")
public class TrainMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@Column(name = "train_Name_English")
	private String trainNameEnglish;
	
	@Column(name = "train_Name_Hindi")
	private String trainNameHindi;
	
	@Column(name = "train_Number")
	private Long trainNumber;
	
	@Column(name = "train_Return_No")
	private Long trainReturnNo;
	

}
