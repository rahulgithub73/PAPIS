package com.dan.papis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.papis.entity.TrainRakeComposition;

public interface TrainRakeCompositionRepo extends JpaRepository<TrainRakeComposition, Long> {
	
	List<TrainRakeComposition> findByTrainNo(Long trainNo);

}
