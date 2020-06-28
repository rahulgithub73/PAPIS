package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "languages")
public class Languages extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column(name = "train_no")
	private String trainNo;

	@Column(name = "train_name")
	private String trainName;

	@Column(name = "language1")
	private String language1;

	@Column(name = "language2")
	private String language2;

	@Column(name = "language3")
	private String language3;

	@Column(name = "language4")
	private String language4;

}
