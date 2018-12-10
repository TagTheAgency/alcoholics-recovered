package com.tagtheagency.alcoholicsrecovered;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.Invoice;
import com.stripe.net.Webhook;
import com.tagtheagency.alcoholicsrecovered.service.StripeService;
import com.tagtheagency.alcoholicsrecovered.service.UserService;

@Controller
@RequestMapping("/stripe-webhook")
@PropertySource(value= {"classpath:secret.properties"})
public class WebhookController {
	
	@Autowired StripeService stripe;
	
	@Autowired UserService users;
	
	@Value("${stripe.api.key}")
	private String apiKey;
	
	@Value("${stripe.webhook.invoice-payment-succeeded}")
	private String paymentSucceededSecret;
	
	@PostMapping("/payment-succeeded")
	@ResponseBody
	public Map<String, Object> paymentSucceededPost(HttpServletRequest request, HttpServletResponse response, @RequestBody String payload) throws IOException {
		
		System.out.println("Post payment succeedded "+payload);
		
//		String payload = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
/*		String payload = request.getbody();*/
        String sigHeader = request.getHeader("Stripe-Signature"); 
        Event event = null;

        try {
            // Retrieve the request's body and parse it as JSON
            event = Webhook.constructEvent(
                payload, sigHeader, paymentSucceededSecret
            );
        } catch (JsonSyntaxException e) {
            String error = "Error: invalid JSON payload ("
                         + e.getMessage() + ")";

            return Collections.singletonMap("error", error);
        } catch (SignatureVerificationException e) {
            String error = "Error: signature verification failed ("
                         + e.getMessage() + ")";
//            response.status(HttpStatus.SC_BAD_REQUEST);
            return Collections.singletonMap("error", error);
        } catch (Exception e) {
        	e.printStackTrace();
            String error = "Error: unexpected exception "
                         + e.getClass().getName() + " ("
                         + e.getMessage() + ")\n";
            return Collections.singletonMap("error", error);
        }

        System.out.println("Received event: " + event.getId()
                    + ", type: " + event.getType()
                    + ", account: " + event.getAccount());

        // Do something with event

        // Reply with 200 status code to acknowledge receipt of the
        // webhook
		
//		System.out.println("Post payment succeeded, got an invoice "+invoice);
		
		return Collections.emptyMap(); 
	}
}
