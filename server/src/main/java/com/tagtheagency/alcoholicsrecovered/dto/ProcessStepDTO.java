package com.tagtheagency.alcoholicsrecovered.dto;

import java.util.ArrayList;
import java.util.List;

import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;

public class ProcessStepDTO {

	
	private int id;
	
	private int stepNumber;
	
	private String title;
	
	private String html;
	
	private boolean needsOkay;
	
	private String tickBoxText;
	
	private ProcessPhaseDTO phase;
	
	private int phaseId;
	
	private String download;
	
	private List<FileLink> files;
	
	private String video;
	

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
	
	public String getTickBoxText() {
		return tickBoxText;
	}
	
	public void setTickBoxText(String tickBoxText) {
		this.tickBoxText = tickBoxText;
	}
	
	public int getPhaseId() {
		return phaseId;
	}
	
	public void setPhaseId(int phaseId) {
		this.phaseId = phaseId;
	}
	
	public String getDownload() {
		return download;
	}
	
	public void setDownload(String download) {
		this.download = download;
	}
	
	public String getVideo() {
		return video;
	}
	
	public void setVideo(String video) {
		this.video = video;
	}

	public static ProcessStepDTO from(ProcessStep step) {
		ProcessStepDTO dto = new ProcessStepDTO();
		dto.setId(step.getId());
		dto.setHtml(step.getHtml());
		dto.setNeedsOkay(step.isNeedsOkay());
		dto.setStepNumber(step.getStepNumber());
		dto.setTitle(step.getTitle());
		dto.setVideo(step.getVideo());
		step.getFiles().forEach(f -> {
			FileLink file = new FileLink();
			file.setDisplayName(f.getDisplayName());
			file.setFilename(f.getFilename());
			dto.getFiles().add(file);
		});
		return dto;
	}

	public ProcessStep toModel() {
		ProcessStep step = new ProcessStep();
		step.setTitle(title);
		step.setHtml(html);
		step.setNeedsOkay(needsOkay);
		step.setDownload(download);
		step.setTickBoxText(tickBoxText);
		step.setStepNumber(stepNumber);
		step.setVideo(video);
		if (files != null) {
			files.forEach(f -> {
				com.tagtheagency.alcoholicsrecovered.model.FileLink file = new com.tagtheagency.alcoholicsrecovered.model.FileLink();
				file.setDisplayName(f.getDisplayName());
				file.setFilename(f.getFilename());
				file.setStep(step);
				step.getFiles().add(file);
			});
		}
		return step;
	}

	public List<FileLink> getFiles() {
		if (files == null) {
			files = new ArrayList<>();
		}
		return files;
	}
	
	public void setFiles(List<FileLink> files) {
		this.files = files;
	}
}
