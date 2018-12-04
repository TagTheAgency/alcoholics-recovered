package com.tagtheagency.alcoholicsrecovered.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tagtheagency.alcoholicsrecovered.model.FileLink;
import com.tagtheagency.alcoholicsrecovered.dto.ProcessStepDTO;
import com.tagtheagency.alcoholicsrecovered.dto.UserDTO;
import com.tagtheagency.alcoholicsrecovered.model.ProcessPhase;
import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;
import com.tagtheagency.alcoholicsrecovered.model.User;
import com.tagtheagency.alcoholicsrecovered.persistence.FileLinkDAO;
import com.tagtheagency.alcoholicsrecovered.persistence.ProcessPhaseDAO;
import com.tagtheagency.alcoholicsrecovered.persistence.ProcessStepDAO;
import com.tagtheagency.alcoholicsrecovered.service.exception.EmailExistsException;

@Service
public class AdminService {

	@Autowired
	private ProcessPhaseDAO processPhaseDao;
	
	@Autowired
	private ProcessStepDAO processStepDAO;
	
	@Autowired
	private FileLinkDAO fileLinkDAO;
	
	@Autowired 
	private UserService userService;
	
	@Value("classpath:setup.json")
	org.springframework.core.io.Resource setupResource;
	
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

	public ProcessStep createStep(ProcessStep step) {
		processStepDAO.save(step);
		return step;
	}
	
	public FileLink createFile(FileLink file) {
		fileLinkDAO.save(file);
		return file;
	}
	
	
	public void init() throws JsonParseException, JsonMappingException, IOException {
		UserDTO admin = new UserDTO();
		admin.setEmail("admin@tagtheagency.com");
		admin.setFirstName("Admin");
		admin.setLastName("Admin");
		admin.setPassword("TagTag2018");
		User user = null;
		try {
			user = userService.registerNewUserAccount(admin);
		} catch (EmailExistsException e) {
			user = userService.getUser("admin@tagtheagency.com");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		List<ProcessStepDTO> steps = mapper.readValue(setupResource.getInputStream(), new TypeReference<List<ProcessStepDTO>>(){});
	    
		Map<Integer, ProcessPhase> phaseMap = new HashMap<>();
		
		fileLinkDAO.deleteAll();
		processStepDAO.deleteAll();
		processPhaseDao.deleteAll();
		
		
	    for (ProcessStepDTO step : steps) {
	    	ProcessPhase phase = phaseMap.get(step.getPhaseId());
	    	if (phase == null) {
	    		phase = new ProcessPhase();
	    		phase.setPhaseNumber(step.getPhaseId());
	    		phaseMap.put(step.getPhaseId(), phase);
	    		createPhase(phase);
	    	}

	    	final ProcessStep dbStep = step.toModel();
	    	dbStep.setPhase(phase);
	    	createStep(dbStep);
	    	
	    	step.getFiles().forEach(e -> {
	    		FileLink link = com.tagtheagency.alcoholicsrecovered.dto.FileLink.toModel(e, dbStep);
	    		createFile(link);
	    	});
	    	
	    	if (dbStep.getStepNumber() == 1 && phase.getPhaseNumber() == 1) {
	    		userService.setStep(user, dbStep, phase);
	    	}
	    	
	    }
	    
	    
		
	}
	
}
