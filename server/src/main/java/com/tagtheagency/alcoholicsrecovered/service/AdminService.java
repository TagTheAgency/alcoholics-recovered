package com.tagtheagency.alcoholicsrecovered.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagtheagency.alcoholicsrecovered.model.ProcessPhase;
import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;
import com.tagtheagency.alcoholicsrecovered.persistence.ProcessPhaseDAO;
import com.tagtheagency.alcoholicsrecovered.persistence.ProcessStepDAO;

@Service
public class AdminService {

	@Autowired
	private ProcessPhaseDAO processPhaseDao;
	
	@Autowired
	private ProcessStepDAO processStepDAO;
	
	public ProcessPhase getProcessPhase(int id) {
		return processPhaseDao.getOne(id);
	}

	public void createPhase(ProcessPhase phase) {
		processPhaseDao.save(phase);
	}

	public List<ProcessStep> getAllSteps() {
		return processStepDAO.findAll();
	}

	public ProcessStep getStep(int id) {
		return processStepDAO.getOne(id);
	}

	public void updateStep(ProcessStep step) {
		processStepDAO.save(step);
		
	}
	
	
}
