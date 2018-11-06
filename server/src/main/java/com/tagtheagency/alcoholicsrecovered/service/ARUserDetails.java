package com.tagtheagency.alcoholicsrecovered.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tagtheagency.alcoholicsrecovered.model.User;

public class ARUserDetails implements UserDetails {

	private String username;
	private String password;
	private String firstName;

	public ARUserDetails(User user) {
		this.username = user.getEmail();
		this.password = user.getPassword();
		this.firstName = user.getFirstName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
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

