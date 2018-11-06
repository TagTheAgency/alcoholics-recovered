package com.tagtheagency.alcoholicsrecovered;

import java.util.Collections;
import java.util.Map;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.tagtheagency.alcoholicsrecovered.dto.UserDTO;
import com.tagtheagency.alcoholicsrecovered.model.User;
import com.tagtheagency.alcoholicsrecovered.service.StripeService;
import com.tagtheagency.alcoholicsrecovered.service.UserService;
import com.tagtheagency.alcoholicsrecovered.service.exception.EmailExistsException;

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
}
