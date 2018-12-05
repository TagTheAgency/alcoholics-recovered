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
	
	public Charge createCharge(int cents, String currency, String description, String token, String email) throws StripeException {
		Stripe.apiKey = secretKey;
		Map<String, Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", cents);
		chargeParams.put("currency", currency);
		chargeParams.put("description", description);
		chargeParams.put("source", token);
		chargeParams.put("receipt_email", email);
		// ^ obtained with Stripe.js

		RequestOptions options = RequestOptions
		  .builder()
		  .setIdempotencyKey("XDchK16FSfixIONW")
		  .build();

		Charge charge = Charge.create(chargeParams);//, options);
		return charge;
//		System.out.println("Charge created, status "+charge.getStatus());
	}
	
}
