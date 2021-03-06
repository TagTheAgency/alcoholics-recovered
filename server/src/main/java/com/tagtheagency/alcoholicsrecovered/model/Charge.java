package com.tagtheagency.alcoholicsrecovered.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="charge")
public class Charge {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="charge_id")
	private long id;
	
	@Column(name="stripe_id")
	private String stripeId;
	
	@Column(name="amount")
	private long amount;
	
	@Column(name="status")
	private String status;
	
	@Column(name="date_entered")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEntered;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStripeId() {
		return stripeId;
	}

	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getDateEntered() {
		return dateEntered;
	}
	
	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}
