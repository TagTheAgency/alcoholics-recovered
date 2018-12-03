package com.tagtheagency.alcoholicsrecovered.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FileLink {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="file_id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "step_id", nullable = false)
	private ProcessStep step;
	
	@Column
	private String filename;
	
	@Column
	private String displayName;
	
	public String getDisplayName() {
		return displayName;
	}
	public String getFilename() {
		return filename;
	}
	public int getId() {
		return id;
	}
	public ProcessStep getStep() {
		return step;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setStep(ProcessStep step) {
		this.step = step;
	}
	
}
