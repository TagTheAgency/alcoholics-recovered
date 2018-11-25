package com.tagtheagency.alcoholicsrecovered;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.tagtheagency.alcoholicsrecovered.dto.UserDTO;
import com.tagtheagency.alcoholicsrecovered.model.ProcessPhase;
import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;
import com.tagtheagency.alcoholicsrecovered.model.User;
import com.tagtheagency.alcoholicsrecovered.service.ARUserDetails;
import com.tagtheagency.alcoholicsrecovered.service.StripeService;
import com.tagtheagency.alcoholicsrecovered.service.UserService;
import com.tagtheagency.alcoholicsrecovered.service.exception.EmailExistsException;
import com.tagtheagency.alcoholicsrecovered.view.ProcessViewHelper;

@Controller
@PropertySource(value= {"classpath:secret.properties"})
public class LMSController {
	
	@Autowired StripeService stripe;
	
	@Autowired UserService users;
	
	@Value("${stripe.api.key}")
	private String apiKey;
	
	
	@GetMapping(path="/test")
	@ResponseBody
	@PreAuthorize("permitAll()")
	public Map<String, String> getRest() {
		return Collections.singletonMap("Hello", "bob");
	}

	@GetMapping(path="/jsp")
	@PreAuthorize("permitAll()")
	public String getPage() {
		return "testMapping";
	}
	
	
	@GetMapping(path="/login")
	public String getLoginPage() {
		return "login";
	}
	
	@GetMapping(path="/join/account")
	public String getJoinPage(Model model) {
		model.addAttribute("apiKey", apiKey);
		return "join";
	}
	
	@PostMapping(path="/join/process")
	public String getJoinPage(Model model, @RequestParam String stripeToken, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password)  {
		
		UserDTO dto = UserDTO.from(firstName, lastName, email, password);
		User user;
		try {
			user = users.registerNewUserAccount(dto);
		} catch (EmailExistsException e) {
			model.addAttribute("duplicateEmail", true);
			model.addAttribute("apiKey", apiKey);
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("email", email);
			return "join";
		}
		
		try {
			Charge charge = stripe.createCharge(49990, "nzd", "test transaction", stripeToken);
			users.addCharge(user, charge);
			//TODO store the charge against the customer.
			
		} catch (StripeException e) {
			e.printStackTrace();
			model.addAttribute("paymentFailed", true);
			model.addAttribute("stripeCode", e.getCode());
			model.addAttribute("stripeStatusCode", e.getStatusCode());
			model.addAttribute("apiKey", apiKey);
			model.addAttribute("firstName", firstName);
			model.addAttribute("lastName", lastName);
			model.addAttribute("email", email);
			users.purge(user);
			return "join";
		}

		Authentication auth = 
				  new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

		SecurityContextHolder.getContext().setAuthentication(auth);
		
		System.out.println("Stripe token: "+stripeToken);
		
		return "welcome";
	}
	
	@GetMapping(path="/welcome")
	public String getWelcomePage() {
		return "welcome";
	}
	
	@GetMapping(path="/theProcess")
	public String getCurrentStepOfTheProcess(Model model, Principal principal) {
		User user = getUserFromPrincipal(principal);
		
		ProcessStep currentStep = users.getCurrentStep(user);
		ProcessPhase currentPhase = currentStep.getPhase();
		
		int processedCurrentStep = getUniqueOrder(currentStep);
		
		//List<String> steps = currentPhase.getSteps().stream().map(ProcessStep::getTitle).collect(Collectors.toList());
		
		int totalSteps = users.getStepCount(currentPhase);
		

		List<ProcessStep> sortedSteps = currentPhase.getSteps();
		sortedSteps.sort((i1,i2) -> i1.getStepNumber() - i2.getStepNumber());
		
		model.addAttribute("stepCount", totalSteps);
		model.addAttribute("currentStep", currentStep);
		model.addAttribute("phaseAndStep", processedCurrentStep);
		model.addAttribute("currentPhase", currentPhase);
		model.addAttribute("steps", sortedSteps);
		
		return "process";
		
	}
	
	@GetMapping(path="/theProcess/{phase}/{step}")
	public String getSpecificStepOfTheProcess(Model model, Principal principal, @PathVariable int phase, @PathVariable int step) {
		User user = getUserFromPrincipal(principal);
		
		ProcessStep currentStep = users.getCurrentStep(user);
		ProcessPhase currentPhase = currentStep.getPhase();
		int processedCurrentStep = getUniqueOrder(currentStep);
		
		if (phase > currentPhase.getPhaseNumber()) {
			return "redirect:/theProcess";//getCurrentStepOfTheProcess(model, principal);
		}
		if (phase == currentPhase.getPhaseNumber() && step > currentStep.getStepNumber()) {
			return "redirect:/theProcess";//getCurrentStepOfTheProcess(model, principal);
		}
		
		ProcessPhase viewPhase = users.getPhaseByNumber(phase);
		ProcessStep viewStep = users.getStepByNumber(step, phase);
		
		ProcessViewHelper helper = new ProcessViewHelper(currentStep, viewStep);
		
		int totalSteps = users.getStepCount(viewPhase);
		
		List<ProcessStep> sortedSteps = viewPhase.getSteps();
		sortedSteps.sort((i1,i2) -> i1.getStepNumber() - i2.getStepNumber());
		
		model.addAttribute("stepCount", totalSteps);
		model.addAttribute("currentStep", viewStep);
		model.addAttribute("phaseAndStep", processedCurrentStep);
		model.addAttribute("currentPhase", viewPhase);
		model.addAttribute("steps", sortedSteps);
		model.addAttribute("helper", helper);
		
		return "process";
		
	}
	
	@PostMapping(path="/theProcess/{phase}/{step}/next")
	public String gotoNextStep(Model model, Principal principal, @PathVariable int phase, @PathVariable int step) {
		User user = getUserFromPrincipal(principal);
		
		ProcessStep currentStep = users.getCurrentStep(user);
		ProcessPhase currentPhase = currentStep.getPhase();
		int processedCurrentStep = getUniqueOrder(currentStep);
		
		if (phase > currentPhase.getPhaseNumber()) {
			return "redirect:/theProcess";//getCurrentStepOfTheProcess(model, principal);
		}
		if (phase == currentPhase.getPhaseNumber() && step > currentStep.getStepNumber()) {
			return "redirect:/theProcess";//getCurrentStepOfTheProcess(model, principal);
		}

		
		ProcessPhase viewPhase = users.getPhaseByNumber(phase);
		ProcessStep viewStep = users.getStepByNumber(step, phase);

		ProcessViewHelper helper = new ProcessViewHelper(currentStep, viewStep);
		
		//List<String> steps = currentPhase.getSteps().stream().map(ProcessStep::getTitle).collect(Collectors.toList());
		
		int totalSteps = users.getStepCount(viewPhase);
		
		List<ProcessStep> sortedSteps = viewPhase.getSteps();
		sortedSteps.sort((i1,i2) -> i1.getStepNumber() - i2.getStepNumber());
		
		model.addAttribute("stepCount", totalSteps);
		model.addAttribute("currentStep", viewStep);
		model.addAttribute("phaseAndStep", processedCurrentStep);
		model.addAttribute("currentPhase", viewPhase);
		model.addAttribute("steps", sortedSteps);
		model.addAttribute("helper", helper);

		return "process";
		
	}
	
	private User getUserFromPrincipal(Principal principal) {
		if (principal instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
			ARUserDetails user = (ARUserDetails) token.getPrincipal();
			return users.getUser(user.getUsername());
		}
		return null;
	}
	
	private int getUniqueOrder(ProcessStep step) {
		return getUniqueOrder(step, step.getPhase());
	}
	
	private int getUniqueOrder(ProcessStep step, ProcessPhase phase) {
		return (phase.getPhaseNumber() * 1000) + step.getStepNumber();
	}
}
