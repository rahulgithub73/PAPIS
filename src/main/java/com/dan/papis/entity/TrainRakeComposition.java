package com.dan.papis.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;

import lombok.Data;

@Data
@Entity(name = "train_rake_composition")
public class TrainRakeComposition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column(name = "train_no")
	private Long trainNo;

	@Valid
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "rake_composition_id")
	private List<RakeComposition> rakeCompositions = new ArrayList<>();
	
	@Transient
	private String trainNameEnglish;
	
	@Transient
	private Long downTrainNo;
	
	@Transient
	private String downTrainName;
	
	@Transient
	private Long trainNumber;
	
	

}
