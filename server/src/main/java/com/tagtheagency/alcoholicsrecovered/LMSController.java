package com.tagtheagency.alcoholicsrecovered;

import java.util.Collections;
import java.util.Map;

import javax.annotation.security.PermitAll;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LMSController {

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
}
