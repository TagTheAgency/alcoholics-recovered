package com.tagtheagency.alcoholicsrecovered.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tagtheagency.alcoholicsrecovered.dto.UserDTO;
import com.tagtheagency.alcoholicsrecovered.model.User;
import com.tagtheagency.alcoholicsrecovered.persistence.UserDAO;
import com.tagtheagency.alcoholicsrecovered.service.exception.EmailExistsException;

@Service
public class UserService implements UserDetailsService {

	@Autowired 
	private UserDAO userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	 
	public User registerNewUserAccount(UserDTO accountDto) throws EmailExistsException {
	    if (emailExists(accountDto.getEmail())) {
	        throw new EmailExistsException(
	          "There is an account with that email adress:" + accountDto.getEmail());
	    }
	    User user = new User();
	    user.setFirstName(accountDto.getFirstName());
	    user.setLastName(accountDto.getLastName());
	     
	    user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
	     
	    user.setEmail(accountDto.getEmail());
//	    user.setRole(new Role(Integer.valueOf(1), user));
	    return userDao.save(user);
	}
	
	private boolean emailExists(String email) {
		return userDao.findByEmail(email).size() > 0;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Trying to load a user by username "+username);
		User user = userDao.findByEmail(username).stream().findFirst().orElseThrow();

		
		return new ARUserDetails(user);
	}
	
	
	


	public void purge(User user) {
		userDao.delete(user);
		
	}
}
