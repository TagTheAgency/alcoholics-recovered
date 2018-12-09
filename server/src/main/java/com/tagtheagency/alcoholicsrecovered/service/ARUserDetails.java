package com.tagtheagency.alcoholicsrecovered.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tagtheagency.alcoholicsrecovered.model.User;

public class ARUserDetails implements UserDetails {

	private String username;
	private String password;
	private String firstName;

	private User user;
	
	public ARUserDetails(User user) {
		this.username = user.getEmail();
		this.password = user.getPassword();
		this.firstName = user.getFirstName();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if (user.getAdmin() != null && user.getAdmin()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			authorities.add(new SimpleGrantedAuthority("ROLE_PROCESS"));
			authorities.add(new SimpleGrantedAuthority("ROLE_COMMUNITY"));
		}
		if (user.getProcessCompleted() == null) {
			authorities.add(new SimpleGrantedAuthority("ROLE_PROCESS"));
		}
		if (user.getSubscriptionPaidTo() != null && user.getSubscriptionPaidTo().after(new Date())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_COMMUNITY"));
		}
		
		System.out.println("User authorities are "+authorities);
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

