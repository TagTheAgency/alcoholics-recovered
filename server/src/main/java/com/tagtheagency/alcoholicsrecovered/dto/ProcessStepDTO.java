package com.tagtheagency.alcoholicsrecovered.dto;

import com.tagtheagency.alcoholicsrecovered.model.ProcessPhase;
import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;

public class ProcessStepDTO {

	
	private int id;
	
	private int stepNumber;
	
	private String title;
	
	private String html;
	
	private boolean needsOkay;
	
	private ProcessPhaseDTO phase;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public boolean isNeedsOkay() {
		return needsOkay;
	}

	public void setNeedsOkay(boolean needsOkay) {
		this.needsOkay = needsOkay;
	}
	
	public int getStepNumber() {
		return stepNumber;
	}
	
	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}
	
	public ProcessPhaseDTO getPhase() {
		return phase;
	}
	
	public void setPhase(ProcessPhaseDTO phase) {
		this.phase = phase;
	}

	public static ProcessStepDTO from(ProcessStep step) {
		ProcessStepDTO dto = new ProcessStepDTO();
		dto.setId(step.getId());
		dto.setHtml(step.getHtml());
		dto.setNeedsOkay(step.isNeedsOkay());
		dto.setStepNumber(step.getStepNumber());
		dto.setTitle(step.getTitle());
		return dto;
	}
	
}
