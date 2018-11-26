package com.tagtheagency.alcoholicsrecovered.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;

@Repository
public interface ProcessStepDAO extends JpaRepository<ProcessStep, Integer>{

	List<ProcessStep> findByStepNumberAndPhase_PhaseNumber(int stepNumber, int phaseNumber);
	
}
