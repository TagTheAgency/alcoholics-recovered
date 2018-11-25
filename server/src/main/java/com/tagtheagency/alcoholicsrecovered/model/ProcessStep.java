package com.tagtheagency.alcoholicsrecovered.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="process_step")
public class ProcessStep {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="step_id")
	private int id;
	
	@Column(name="step_number")
	private int stepNumber;
	
	@Column 
	private String title;
	
	@Column
	private String html;
	
	@Column 
	private boolean needsOkay;
	
	@Column(name="tick_box_text")
	private String tickBoxText;
	
	@Column
	private String download;
	
	@Column
	private String video;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "phase_id", nullable = false)
	private ProcessPhase phase;

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
	
	public ProcessPhase getPhase() {
		return phase;
	}
	
	public void setPhase(ProcessPhase phase) {
		this.phase = phase;
	}
	
	public String getDownload() {
		return download;
	}
	
	public void setDownload(String download) {
		this.download = download;
	}
	
	public String getTickBoxText() {
		return tickBoxText;
	}
	
	public void setTickBoxText(String tickBoxText) {
		this.tickBoxText = tickBoxText;
	}
	
	public String getVideo() {
		return video;
	}
	
	public void setVideo(String video) {
		this.video = video;
	}
	
	
}
