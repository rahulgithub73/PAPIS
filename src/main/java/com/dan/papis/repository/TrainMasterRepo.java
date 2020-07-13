package com.dan.papis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.papis.entity.TrainMaster;

public interface TrainMasterRepo extends JpaRepository<TrainMaster, Long> {
	
	TrainMaster findByTrainNumber(Long trainNumber);
	
	TrainMaster findByTrainReturnNo(Long trainReturnNo);

}
