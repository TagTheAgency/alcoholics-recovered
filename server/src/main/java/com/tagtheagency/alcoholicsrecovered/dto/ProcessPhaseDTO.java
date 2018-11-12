package com.tagtheagency.alcoholicsrecovered.dto;

import com.tagtheagency.alcoholicsrecovered.model.ProcessPhase;

public class ProcessPhaseDTO {

	private int id;
	private int number;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	
	public static ProcessPhaseDTO from(ProcessPhase phase) {
		ProcessPhaseDTO dto = new ProcessPhaseDTO();
		dto.setId(phase.getId());
		dto.setNumber(dto.getNumber());
		return dto;
	}
	
	public static ProcessPhase to(ProcessPhaseDTO dto) {
		ProcessPhase phase = new ProcessPhase();
		phase.setId(dto.getId());
		phase.setPhaseNumber(dto.getNumber());
		return phase;
	}
}
