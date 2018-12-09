package com.tagtheagency.alcoholicsrecovered;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Subscription;
import com.tagtheagency.alcoholicsrecovered.dto.GenericResponse;
import com.tagtheagency.alcoholicsrecovered.dto.PasswordDTO;
import com.tagtheagency.alcoholicsrecovered.dto.UserDTO;
import com.tagtheagency.alcoholicsrecovered.model.ForumThread;
import com.tagtheagency.alcoholicsrecovered.model.ProcessPhase;
import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;
import com.tagtheagency.alcoholicsrecovered.model.User;
import com.tagtheagency.alcoholicsrecovered.service.ARUserDetails;
import com.tagtheagency.alcoholicsrecovered.service.StripeService;
import com.tagtheagency.alcoholicsrecovered.service.UserService;
import com.tagtheagency.alcoholicsrecovered.service.exception.EmailExistsException;
import com.tagtheagency.alcoholicsrecovered.service.exception.UserNotFoundException;
import com.tagtheagency.alcoholicsrecovered.view.CommunityViewHelper;
import com.tagtheagency.alcoholicsrecovered.view.ProcessViewHelper;

@Controller
@PropertySource(value= {"classpath:secret.properties"})
public class LMSController {
	
	@Autowired StripeService stripe;
	
	@Autowired UserService users;
	
	@Value("${stripe.api.key}")
	private String apiKey;
	
	@GetMapping("/")
	public String getHomepage() {
		return "redirect:/theProcess";
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class ResourceNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;

	}
	
	@GetMapping("/public/{page}")
	public String showPublicPage(@PathVariable("page") String page) {
		return "public/" + page;
	}
	
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
	public String getLoginPage(Model model) {
		return "login";
	}

	@GetMapping(path="/forgotPassword")
	public String getForgotPasswordPage() {
		return "forgotPassword";
	}

	
	@GetMapping(path="/join/account")
	public String getJoinPage(Model model) {
		//TODO - ip locality lookup?? http://api.ipstack.com/116.251.192.193?access_key=abac143383f0171117603e3cc6feaf09&format=1
		model.addAttribute("apiKey", apiKey);
		return "join";
	}

	@GetMapping(path="/account")
	public String getAccountPage(Model model) {
		return "account";
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
			Charge charge = stripe.createCharge(49700, "aud", "Recovered Group Signup", stripeToken, email);
			users.addCharge(user, charge);


			
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
				
		return "redirect:/welcome";
	}
	
	@PostMapping(path="/community-subscription")
	public String communitySignup(@RequestParam String stripeEmail, @RequestParam String stripeToken, @RequestParam String subscription, Principal principal) throws StripeException {
		User user = getUserFromPrincipal(principal);
		Subscription sub = stripe.addCommunitySubscription(user, stripeEmail, stripeToken, subscription);
		
		users.addSubscription(user, sub);
		
		return "redirect:/community";
	}
	
	
	@GetMapping(path="/welcome")
	public String getWelcomePage(Model model, Principal principal) {
		User user = getUserFromPrincipal(principal);
		ProcessStep currentStep = users.getCurrentStep(user);
		model.addAttribute("user", user);
		model.addAttribute("newUser", currentStep == null || (currentStep.getPhase().getPhaseNumber() == 1 && currentStep.getStepNumber() == 1));
		return "welcome";
	}
	
	@GetMapping(path="/theProcess")
	public String getCurrentStepOfTheProcess(Model model, Principal principal) {
		User user = getUserFromPrincipal(principal);
		
		ProcessStep currentStep = users.getCurrentStep(user);
		if (currentStep == null) {
			currentStep = users.getFirstStepOfTheProcess();
		}
		
		setProcessPage(model, currentStep, currentStep);
		
		return "process";
		
	}
	
	private void setProcessPage(Model model, ProcessStep viewingStep, ProcessStep currentStep) {
		ProcessViewHelper helper = new ProcessViewHelper(currentStep, viewingStep);
		ProcessPhase viewPhase = viewingStep.getPhase();
		int totalSteps = users.getStepCount(viewPhase);
		
		List<ProcessStep> sortedSteps = viewPhase.getSteps();
		sortedSteps.sort((i1,i2) -> i1.getStepNumber() - i2.getStepNumber());
		
		model.addAttribute("stepCount", totalSteps);
		model.addAttribute("currentStep", viewingStep);
		model.addAttribute("currentPhase", viewPhase);
		model.addAttribute("steps", sortedSteps);
		model.addAttribute("helper", helper);
	}
	
	@GetMapping(path="/theProcess/{phase}/{step}")
	public String getSpecificStepOfTheProcess(Model model, Principal principal, @PathVariable int phase, @PathVariable int step) {
		User user = getUserFromPrincipal(principal);
		
		ProcessStep currentStep = users.getCurrentStep(user);
		ProcessPhase currentPhase = currentStep.getPhase();
		
		if (phase > currentPhase.getPhaseNumber()) {
			return "redirect:/theProcess";//getCurrentStepOfTheProcess(model, principal);
		}
		if (phase == currentPhase.getPhaseNumber() && step > currentStep.getStepNumber()) {
			return "redirect:/theProcess";//getCurrentStepOfTheProcess(model, principal);
		}
		
		ProcessStep viewStep = users.getStepByNumber(step, phase);
		
		setProcessPage(model, viewStep, currentStep);

		return "process";
		
	}
	
	@GetMapping(path="/theProcess/{phase}/{step}/previous")
	public String gotoPreviousStep(Model model, Principal principal, @PathVariable int phase, @PathVariable int step) {
		ProcessStep currentStep = users.getStepByNumber(step, phase);
		ProcessStep requestedStep = users.getPreceedingStep(currentStep);
		
		return getSpecificStepOfTheProcess(model, principal, requestedStep.getPhase().getPhaseNumber(), requestedStep.getStepNumber());
	}
	
	@PostMapping(path="/theProcess/{phase}/{step}/next")
	public String gotoNextStep(Model model, Principal principal, @PathVariable int phase, @PathVariable int step, @RequestParam(required=false) String agreeCheck) {
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

		
		
		if (getUniqueOrder(viewStep) == getUniqueOrder(currentStep) && currentStep.isNeedsOkay() && agreeCheck != null) {
			return getCurrentStepOfTheProcess(model, principal);
		}
		
		ProcessStep requestedStep = users.getNextStep(viewStep);
		
		if (requestedStep == null) {
			//finished!
			users.setFinished(user);
			return "community";
		}

		if (getUniqueOrder(viewStep) == getUniqueOrder(currentStep)) {
			users.setStep(user, requestedStep, requestedStep.getPhase());
			return "redirect:/theProcess";
		}
		
		return getSpecificStepOfTheProcess(model, principal, requestedStep.getPhase().getPhaseNumber(), requestedStep.getStepNumber());

		
	}
	
	private User getUserFromPrincipal(Principal principal) {
		if (principal instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
			if (token.getPrincipal() instanceof ARUserDetails) {
				ARUserDetails user = (ARUserDetails) token.getPrincipal();
				return users.getUser(user.getUsername());
			}
			if (token.getPrincipal() instanceof User) {
				return (User) token.getPrincipal();
			}
		}
		return null;
	}
	
	private int getUniqueOrder(ProcessStep step) {
		return getUniqueOrder(step, step.getPhase());
	}
	
	private int getUniqueOrder(ProcessStep step, ProcessPhase phase) {
		return (phase.getPhaseNumber() * 1000) + step.getStepNumber();
	}


	@PostMapping(value = "/forgotPassword")
	@ResponseBody
	public GenericResponse resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) throws UserNotFoundException {
		User user = users.getUser(userEmail);
		if (user == null) {
			throw new UserNotFoundException();
		}
		String token = UUID.randomUUID().toString();
		users.createPasswordResetTokenForUser(user, token);
		users.sendResetEmail(getAppUrl(request), user, token);
		return new GenericResponse("Thanks, we've sent you an email");
	}
	
	private String getAppUrl(HttpServletRequest request) {
		return request.getLocalName();
	}
	
	@GetMapping(value = "/user/changePassword")
	public String showChangePasswordPage(Model model, @RequestParam("key") long id, @RequestParam("token") String token) {
	    String result = users.validatePasswordResetToken(id, token);
	    
	    if (result != null) {
	    	switch (result) {
	    	case "invalidToken": model.addAttribute("message", "That's an invalid token. Please login to continue");
	    	break;
	    	case "expired": model.addAttribute("message", "Sorry, that token has expired.  Please login to continue");
	    	break;
	    	default: model.addAttribute("message", "Sorry, something went wrong");
	    	}
	    	
//	    	model.addAttribute("message", 
//	          messages.getMessage("auth.message." + result, null, locale));
	        return "redirect:/login?message="+model.asMap().get("message");
	    }
	    return "redirect:/updatePassword";
	}
	
	@GetMapping("/updatePassword")
	public String showUpdatePasswordPage() {
		return "changePassword";
	}
	
	@GetMapping(value = "/user/savePassword")
	@ResponseBody
	public GenericResponse savePassword(@Valid PasswordDTO passwordDto, Principal principal) {
	    User user = getUserFromPrincipal(principal);
	    users.changeUserPassword(user, passwordDto.getNewPassword());
	    return new GenericResponse("Your password has been updated");
	}
	

	@GetMapping("/community") 
	public String showCommunityPage(Principal principal, Model model, @RequestParam(required=false, defaultValue="0") int page) {

		Pageable paging = PageRequest.of(page, 10);
		model.addAttribute("threads", users.getThreads(paging));
		model.addAttribute("service", users);
		model.addAttribute("viewHelper", new CommunityViewHelper());
		return "community";
	}

	@PreAuthorize("hasRole('ROLE_COMMUNITY')")
	@GetMapping("/community/{id}") 
	public String showCommunityThread(Model model, @PathVariable int id) {
		ForumThread thread = users.getForumThread(id);

		if (thread == null) {
			throw new ResourceNotFoundException();
		}
		
		model.addAttribute("thread", thread);
		model.addAttribute("service", users);
		model.addAttribute("viewHelper", new CommunityViewHelper());
		
		
		return "thread";
	}

	@PreAuthorize("hasRole('ROLE_COMMUNITY')")
	@PostMapping("/community") 
	public String createCommunityThread(Model model, @RequestParam String title, @RequestParam String body, Principal principal) {
		ForumThread thread = users.createForumThread(title, body, getUserFromPrincipal(principal));

		return "redirect:/community/"+thread.getThreadId();
	}

	@PreAuthorize("hasRole('ROLE_COMMUNITY')")
	@PostMapping("/community/{id}") 
	public String replyToPost(Model model, @PathVariable int id, @RequestParam String body, Principal principal) {
		ForumThread thread = users.getForumThread(id);
		if (thread == null) {
			throw new ResourceNotFoundException();
		}
		
		users.createForumReply(body, thread, getUserFromPrincipal(principal));

		return "redirect:/community/"+thread.getThreadId();
	}

	
	
	
	protected boolean hasRole(String role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
        	System.out.println("Checking "+ auth.getAuthority());
            if (role.equals(auth.getAuthority()))
                return true;
        }

        return false;
    }
}
