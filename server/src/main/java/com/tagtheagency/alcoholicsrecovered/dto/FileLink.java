package com.tagtheagency.alcoholicsrecovered.dto;

import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;

public class FileLink {

	private String filename;
	private String displayName;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public static com.tagtheagency.alcoholicsrecovered.model.FileLink toModel(FileLink link, ProcessStep step) {
		com.tagtheagency.alcoholicsrecovered.model.FileLink file = new com.tagtheagency.alcoholicsrecovered.model.FileLink();
		file.setDisplayName(link.getDisplayName());
		file.setFilename(link.getFilename());
		file.setStep(step);
		step.getFiles().add(file);
		return file;
	}
}
