package com.tagtheagency.alcoholicsrecovered.view;

import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;

public class ProcessViewHelper {

	private ProcessStep currentStep;
	private ProcessStep viewingStep;

	public ProcessViewHelper(ProcessStep currentStep, ProcessStep viewingStep) {
		this.currentStep = currentStep;
		this.viewingStep = viewingStep;
	}

	public ProcessStep getCurrentStep() {
		return currentStep;
	}
	
	public ProcessStep getViewingStep() {
		return viewingStep;
	}
	
	public int getUniqueOrder(ProcessStep step) {
		return (step.getPhase().getPhaseNumber() * 1000) + step.getStepNumber();
	}
	
	public boolean active(ProcessStep step) {
		return step.getId() == viewingStep.getId();
	}

	public boolean current(ProcessStep step) {
		return step.getId() == currentStep.getId();
	}

	public boolean viewable(ProcessStep step) {
		return getUniqueOrder(step) < getUniqueOrder(currentStep);
	}

}
