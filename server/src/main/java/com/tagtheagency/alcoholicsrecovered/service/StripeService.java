package com.tagtheagency.alcoholicsrecovered.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;

@Service
@PropertySource(value= {"classpath:secret.properties"})
public class StripeService {

	@Value("${stripe.api.key}")
	private String apiKey;
	
	@Value("${stripe.secret.key}")
	private String secretKey;
	
	public StripeService() {
		Stripe.apiKey = secretKey;
	}
	
	public void createCharge(int cents, String currency, String description) throws StripeException {
		Map<String, Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", 2000);
		chargeParams.put("currency", "nzd");
		chargeParams.put("description", "Charge for jenny.rosen@example.com");
		chargeParams.put("source", "tok_visa");
		// ^ obtained with Stripe.js

		RequestOptions options = RequestOptions
		  .builder()
		  .setIdempotencyKey("XDchK16FSfixIONW")
		  .build();

		Charge.create(chargeParams, options);
	}
	
}
