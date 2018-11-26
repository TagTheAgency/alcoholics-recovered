package com.tagtheagency.alcoholicsrecovered;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tagtheagency.alcoholicsrecovered.dto.ProcessPhaseDTO;
import com.tagtheagency.alcoholicsrecovered.dto.ProcessStepDTO;
import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;
import com.tagtheagency.alcoholicsrecovered.model.User;
import com.tagtheagency.alcoholicsrecovered.service.ARUserDetails;
import com.tagtheagency.alcoholicsrecovered.service.AdminService;
import com.tagtheagency.alcoholicsrecovered.service.UserService;

@Controller
@PropertySource(value= {"classpath:secret.properties"})
@RequestMapping("admin")
public class AdminController {
	
	@Autowired AdminService adminService;
	
	@Autowired UserService userService;
	
	@GetMapping(path="/phase/{phaseId}")
	@ResponseBody
	public ProcessPhaseDTO getRest(@PathVariable int phaseId) {
		//TODO
		return null;
//		return adminService.getProcessPhase(phaseId);
	}

	@PostMapping(path="/phase/")
	@ResponseBody
	public ProcessPhaseDTO updatePhase(@RequestBody ProcessPhaseDTO phase) {
		
		//TODO actually update it!
		return phase;
	}
	
	@PutMapping(path="/phase/")
	@ResponseBody
	public ProcessPhaseDTO createPhase(@RequestBody ProcessPhaseDTO phase) {
//		adminService.createPhase(phase);
		return phase;
	}
	
	@GetMapping(path="/cms")
	public String edit(Model model) {
		model.addAttribute("steps", adminService.getAllSteps());
		return "admin/cms";
	}
	
	@GetMapping(path="/step/{id}")
	@ResponseBody
	public ProcessStepDTO getStep(@PathVariable int id) {
		System.out.println("Reading step "+id);

		return ProcessStepDTO.from(adminService.getStep(id));
	}
	
	@PostMapping(path="/step")
	@ResponseBody
	public ProcessStepDTO createStep(@RequestParam int phase, @RequestParam String title) {
		ProcessStep step = new ProcessStep();
		step.setPhase(adminService.getProcessPhase(phase));
		step.setTitle(title);
		step = adminService.createStep(step);
		return ProcessStepDTO.from(step);
	}
	
	@PostMapping(path="/step/{id}")
	@ResponseBody
	public ProcessStepDTO updateStep(@PathVariable int id, @RequestParam String html, @RequestParam String title) {
		System.out.println("Updating step "+id);
		ProcessStep step = adminService.getStep(id);
		step.setHtml(html);
		step.setTitle(title);
		adminService.updateStep(step);
		
		return ProcessStepDTO.from(step); 
	}
	
	private User getUserFromPrincipal(Principal principal) {
		if (principal instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
			ARUserDetails user = (ARUserDetails) token.getPrincipal();
			return userService.getUser(user.getUsername());
		}
		return null;
	}
	
	@GetMapping(path="/init")
	@ResponseBody
	public void init() throws JsonParseException, JsonMappingException, IOException {
		adminService.init();
	}
	
}
