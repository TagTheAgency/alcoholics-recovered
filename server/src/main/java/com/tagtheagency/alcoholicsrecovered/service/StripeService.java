package com.tagtheagency.alcoholicsrecovered.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import com.stripe.net.RequestOptions;
import com.tagtheagency.alcoholicsrecovered.model.User;

@Service
@PropertySource(value= {"classpath:secret.properties"})
public class StripeService {

	@Value("${stripe.api.key}")
	private String apiKey;
	
	@Value("${stripe.secret.key}")
	private String secretKey;

	@Value("${stripe.community.product}")
	private String communityProduct;
	
	@Value("${stripe.commumity.plan.monthly}")
	private String monthlyPlan;

	@Value("${stripe.commumity.plan.annual}")
	private String annualPlan;

	@Autowired
	private UserService userService;
	
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
	}

	public Subscription addCommunitySubscription(User user, String stripeEmail, String stripeToken, String subscription) throws StripeException {
		Stripe.apiKey = secretKey;
		
		if (user.getStripeCustomerId() == null) {
			Map<String, Object> customerParams = new HashMap<>();
			customerParams.put("email", stripeEmail);
			customerParams.put("source", stripeToken);
			Customer customer = Customer.create(customerParams);
			user.setStripeCustomerId(customer.getId());
			userService.updateUser(user);
		}
		
//		Map<String, Object> subParams = new HashMap<>();
//		subParams.put("customer", user.getStripeCustomerId());
		System.out.println("Using customer "+user.getStripeCustomerId());

		Map<String, Object> item = new HashMap<String, Object>();
		switch (subscription) {
		case "month": item.put("plan", monthlyPlan);
		break;
		case "annual" : item.put("plan", annualPlan);
		break;
		default: 
			throw new IllegalArgumentException("Invalid subscription plan "+subscription);
		}
		item.put("quantity", 1);

		Map<String, Object> items = new HashMap<String, Object>();
		items.put("0", item);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", user.getStripeCustomerId());
		params.put("items", items);

		System.out.println("Creating with params "+params);
		
		Subscription sub = Subscription.create(params);
		return sub;
	}
	
	
	
}
