package com.tagtheagency.alcoholicsrecovered.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="process_phase")
public class ProcessPhase {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="phase_id")
	private int id;
	
	@Column(name="phase_number")
	private int phaseNumber;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="phase")
	public List<ProcessStep> steps;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPhaseNumber() {
		return phaseNumber;
	}

	public void setPhaseNumber(int phaseNumber) {
		this.phaseNumber = phaseNumber;
	}

	public List<ProcessStep> getSteps() {
		if (steps == null) {
			steps = new ArrayList<>();
		}
		return steps;
	}

	public void setSteps(List<ProcessStep> steps) {
		this.steps = steps;
	}
	

	
	
}
