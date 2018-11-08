package com.tagtheagency.alcoholicsrecovered.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stripe.model.Charge;
import com.tagtheagency.alcoholicsrecovered.dto.UserDTO;
import com.tagtheagency.alcoholicsrecovered.model.ProcessPhase;
import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;
import com.tagtheagency.alcoholicsrecovered.model.User;
import com.tagtheagency.alcoholicsrecovered.persistence.ChargeDAO;
import com.tagtheagency.alcoholicsrecovered.persistence.ProcessPhaseDAO;
import com.tagtheagency.alcoholicsrecovered.persistence.ProcessStepDAO;
import com.tagtheagency.alcoholicsrecovered.persistence.UserDAO;
import com.tagtheagency.alcoholicsrecovered.service.exception.EmailExistsException;

@Service
public class UserService implements UserDetailsService {

	@Autowired 
	private UserDAO userDao;
	
	@Autowired
	private ChargeDAO chargeDao;
	
	@Autowired
	private ProcessStepDAO processStepDao;
	
	@Autowired
	private ProcessPhaseDAO processPhaseDao;
	
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
	
	/**
	 * User has paid and is ready to be activated
	 * @param user
	 * @param charge
	 * @return
	 */
	public User activateNewUser(User user, Charge charge) {
		addCharge(user, charge);
		user.setActive(true);
		user.setCurrentPhase(1);
		user.setCurrentStep(1);
		user.setDatePaid(new Date());
		user.setProcessStarted(true);
		userDao.save(user);
		return user;
		
	}
	
	private boolean emailExists(String email) {
		return userDao.findByEmail(email).size() > 0;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByEmail(username).stream().findFirst().orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
		return new ARUserDetails(user);
	}
	
	public User getUser(String email) throws UsernameNotFoundException {
		return userDao.findByEmail(email).stream().findFirst().orElseThrow(() -> new UsernameNotFoundException("No user found with email " + email));
	}
	
	
	public void addCharge(User user, Charge charge) {
		com.tagtheagency.alcoholicsrecovered.model.Charge internalCharge = new com.tagtheagency.alcoholicsrecovered.model.Charge();
		internalCharge.setUser(user);
		internalCharge.setAmount(charge.getAmount());
		internalCharge.setDateEntered(new Date());
		internalCharge.setStripeId(charge.getId());
		internalCharge.setStatus(charge.getStatus());
		chargeDao.save(internalCharge);
		
	}


	public void purge(User user) {
		userDao.delete(user);
		
	}

	public ProcessStep getCurrentStep(User user) {
		int stepId = user.getCurrentStep();
		return processStepDao.getOne(stepId);
		
	}

	public int getStepCount(ProcessPhase currentPhase) {
		return processPhaseDao.getStepCount(currentPhase.getId());
	}
	
	public ProcessStep getNextStep(ProcessStep currentStep) {
		//TODO 
		return null;
		
		
	}
}
