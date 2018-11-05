package com.tagtheagency.alcoholicsrecovered;

import java.util.Collections;
import java.util.Map;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PropertySource(value= {"classpath:secret.properties"})
public class LMSController {
	
	
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
	
	@GetMapping(path="/join")
	public String getJoinPage(Model model) {
		model.addAttribute("apiKey", apiKey);
		return "join";
	}
}
