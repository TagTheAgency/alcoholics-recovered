package com.tagtheagency.alcoholicsrecovered;

import java.util.Collections;
import java.util.Map;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stripe.exception.StripeException;
import com.tagtheagency.alcoholicsrecovered.service.StripeService;

@Controller
@PropertySource(value= {"classpath:secret.properties"})
public class LMSController {
	
	@Autowired StripeService stripe;
	
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
	public String getJoinPage(@RequestParam String stripeToken, @RequestParam String stripeTokenType, @RequestParam String stripeEmail) throws StripeException {
		
		stripe.createCharge(49990, "nzd", "test transaction", stripeToken);
		
		System.out.println("Stripe token: "+stripeToken);
		
		return "login";
	}
}
