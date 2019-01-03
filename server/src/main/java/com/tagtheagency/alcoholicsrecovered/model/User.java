package com.tagtheagency.alcoholicsrecovered.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="users")
public class User {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="user_id")
	private long id;

	@Column
	private String username;
	
	@Column(name="fist_name")
	private String firstName;
	
	@Column
	private String password;
	
	@Column
	private String email;
	
	@Column(name="date_paid")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datePaid;
	
	@Column(name="process_started")
	private Boolean processStarted;
	
	@Column(name="process_completed")
	@Temporal(TemporalType.DATE)
	private Date processCompleted;
	
	@Column(name="current_step")
	private Integer currentStep;
	
	@Column(name="current_phase")
	private Integer currentPhase;
	
	@Column
	private Boolean active;
	
	@Column(name="subscription_paid_to")
	@Temporal(TemporalType.DATE)
	private Date subscriptionPaidTo;
	
	@Column
	private Boolean admin;
	
	@Column(name="stripe_customer_id")
	private String stripeCustomerId;
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}

	public Boolean isProcessStarted() {
		return processStarted;
	}

	public void setProcessStarted(boolean processStarted) {
		this.processStarted = processStarted;
	}

	public Date getProcessCompleted() {
		return processCompleted;
	}

	public void setProcessCompleted(Date processCompleted) {
		this.processCompleted = processCompleted;
	}

	public Integer getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public Integer getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(int currentPhase) {
		this.currentPhase = currentPhase;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getSubscriptionPaidTo() {
		return subscriptionPaidTo;
	}

	public void setSubscriptionPaidTo(Date subscriptionPaidTo) {
		this.subscriptionPaidTo = subscriptionPaidTo;
	}
	
	public Boolean getAdmin() {
		return admin;
	}
	public Boolean getActive() {
		return active;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
	public String getStripeCustomerId() {
		return stripeCustomerId;
	}
	
	public void setStripeCustomerId(String stripeCustomerId) {
		this.stripeCustomerId = stripeCustomerId;
	}
	
	
}
