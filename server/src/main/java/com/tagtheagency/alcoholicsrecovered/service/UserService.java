package com.tagtheagency.alcoholicsrecovered.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stripe.model.Charge;
import com.tagtheagency.alcoholicsrecovered.dto.UserDTO;
import com.tagtheagency.alcoholicsrecovered.model.PasswordResetToken;
import com.tagtheagency.alcoholicsrecovered.model.ProcessPhase;
import com.tagtheagency.alcoholicsrecovered.model.ProcessStep;
import com.tagtheagency.alcoholicsrecovered.model.User;
import com.tagtheagency.alcoholicsrecovered.persistence.ChargeDAO;
import com.tagtheagency.alcoholicsrecovered.persistence.PasswordResetTokenDAO;
import com.tagtheagency.alcoholicsrecovered.persistence.ProcessPhaseDAO;
import com.tagtheagency.alcoholicsrecovered.persistence.ProcessStepDAO;
import com.tagtheagency.alcoholicsrecovered.persistence.UserDAO;
import com.tagtheagency.alcoholicsrecovered.service.exception.EmailExistsException;

@Service
@PropertySource(value= {"classpath:application.properties"})
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
	private PasswordResetTokenDAO passwordResetDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MailSender mailSender;
	
	@Value("${support.email}")
	private String serviceEmail;
	 
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
	    ProcessStep step = getFirstStepOfTheProcess();
	    user.setCurrentStep(step.getId());
	    user.setCurrentPhase(step.getPhase().getId());
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
		return userDao.findByEmailIgnoreCase(email).size() > 0;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByEmailIgnoreCase(username).stream().findFirst().orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
		return new ARUserDetails(user);
	}
	
	public User getUser(String email) throws UsernameNotFoundException {
		return userDao.findByEmailIgnoreCase(email).stream().findFirst().orElseThrow(() -> new UsernameNotFoundException("No user found with email " + email));
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
		try {
			Integer stepId = user.getCurrentStep();
			System.out.println("Get current step returning id "+stepId);
			if (stepId == null) {
				return getFirstStepOfTheProcess();
			}
			
			Optional<ProcessStep> found = processStepDao.findById(stepId);
			System.out.println("Done a search by example, have a result "+found.isPresent());
			
			return found.orElseGet(this::getFirstStepOfTheProcess);
		} catch (javax.persistence.EntityNotFoundException e) {
			return getFirstStepOfTheProcess();
		}
	}

	public int getStepCount(ProcessPhase currentPhase) {
		return processPhaseDao.getStepCount(currentPhase.getId());
	}


	public List<ProcessStep> getPhaseSteps(ProcessPhase currentPhase) {
		return currentPhase.getSteps();
	}

	/**
	 * Get a phase by its number
	 * @param phase
	 * @return
	 */
	public ProcessPhase getPhaseByNumber(int phase) {
		return processPhaseDao.findByPhaseNumber(phase).get(0);
	}

	/**
	 * Get a step by its number and phase number
	 * @param phase
	 * @return
	 */
	public ProcessStep getStepByNumber(int step, int phase) {
		return processStepDao.findByStepNumberAndPhase_PhaseNumber(step, phase).get(0);
	}

	public void setStep(User user, ProcessStep step, ProcessPhase phase) {
		user.setCurrentPhase(phase.getId());
		user.setCurrentStep(step.getId());
		userDao.save(user);
	}

	public ProcessStep getFirstStepOfTheProcess() {
		return processStepDao.findByStepNumberAndPhase_PhaseNumber(1, 1).get(0);
	}

	public ProcessStep getPreceedingStep(ProcessStep currentStep) {
		int stepNumber = currentStep.getStepNumber() - 1;
		if (stepNumber >= 1) {
			return getStepByNumber(stepNumber, currentStep.getPhase().getPhaseNumber());
		}
		//first step, have to go back a phase.
		int phaseNumber = currentStep.getPhase().getPhaseNumber() - 1;
		if (phaseNumber >= 1) {
			ProcessPhase preceedingPhase = processPhaseDao.findByPhaseNumber(phaseNumber).get(0);
			int lastStep = preceedingPhase.steps.stream().mapToInt(ProcessStep::getStepNumber).max().getAsInt();
			return getStepByNumber(lastStep, phaseNumber);
		}
		return getFirstStepOfTheProcess();
	}
	
	
	public ProcessStep getNextStep(ProcessStep currentStep) {
		int stepNumber = currentStep.getStepNumber() + 1;

		List<ProcessStep> stepPlusOne = processStepDao.findByStepNumberAndPhase_PhaseNumber(stepNumber, currentStep.getPhase().getPhaseNumber());
		if (stepPlusOne.size() > 0) {
			return stepPlusOne.get(0);
		}
		//increment phase
		List<ProcessPhase> phasePlusOne = processPhaseDao.findByPhaseNumber(currentStep.getPhase().getPhaseNumber() + 1);
		if (phasePlusOne.isEmpty()) {
			//TODO handle finished.
			return null;
		}
		return getStepByNumber(1, phasePlusOne.get(0).getPhaseNumber());
	}

	public void createPasswordResetTokenForUser(User user, String token) {
		passwordResetDao.deleteByUser(user);
	    PasswordResetToken dbToken = new PasswordResetToken();
	    dbToken.setUser(user);
	    dbToken.setToken(token);
	    dbToken.setExpiryDate(new Date(new Date().getTime() + (60 * 60 * 24 * 1000)));
	    passwordResetDao.save(dbToken);
	    
	}
	
	private SimpleMailMessage constructResetTokenEmail(String contextPath, String token, User user) {
	    String url = contextPath + "/user/changePassword?key=" + user.getId() + "&token=" + token;
	    String message = "Somebody (hopefully you) has requested to reset your password for the Recovered Group. Please follow the link below";
	    return constructEmail("Reset Password", message + " \r\n" + url, user);
	}
	 
	private SimpleMailMessage constructEmail(String subject, String body, User user) {
	    SimpleMailMessage email = new SimpleMailMessage();
	    email.setSubject(subject);
	    email.setText(body);
	    email.setTo(user.getEmail());
	    email.setFrom(serviceEmail);
	    return email;
	}

	public void sendResetEmail(String applicationURL, User user, String token) {
		mailSender.send(constructResetTokenEmail(applicationURL, token, user));
	}

	public String validatePasswordResetToken(long id, String token) {
	    PasswordResetToken passToken = passwordResetDao.findByToken(token);
	    if ((passToken == null) || (passToken.getUser().getId() != id)) {
	        return "invalidToken";
	    }
	 
	    Calendar cal = Calendar.getInstance();
	    if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	System.out.println("Date from db is "+passToken.getExpiryDate());
	    	System.out.println("And mine is     "+cal.getTime());
	    	System.out.println("Which gives a difference of "+(passToken.getExpiryDate().getTime() - cal.getTime().getTime()));
	        return "expired";
	    }
	 
	    User user = passToken.getUser();
	    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
	    SecurityContextHolder.getContext().setAuthentication(auth);
	    return null;
	}

	public void changeUserPassword(User user, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	
}
