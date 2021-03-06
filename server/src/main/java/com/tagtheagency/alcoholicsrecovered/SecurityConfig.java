package com.tagtheagency.alcoholicsrecovered;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tagtheagency.alcoholicsrecovered.service.UserService;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true, jsr250Enabled=true, prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().ignoringAntMatchers("/stripe-webhook/**")
			.and()
			.authorizeRequests()
				.antMatchers("/login*").anonymous()
				.antMatchers("/forgotPassword*").anonymous()
				.antMatchers("/user/changePassword").anonymous()
				//.antMatchers("/").permitAll()
				.antMatchers("/updatePassword*").permitAll()
				//.antMatchers("/public/**").permitAll()  
				.antMatchers("/join/**").anonymous()  
				.antMatchers("/stripe-webhook/**").permitAll()  
				.antMatchers("/css/**").permitAll()  
				.antMatchers("/img/**").permitAll()  
				.antMatchers("/resources/**").permitAll()  
				.antMatchers("/js/**").permitAll()  
				.anyRequest().authenticated()
			.and()
				.formLogin()
					.loginPage("/join/account")
					.loginProcessingUrl("/perform_login")
					.defaultSuccessUrl("/welcome")
					.failureUrl("/login?error=true")
			.and()
				.logout()
					.logoutSuccessUrl("/login")
					/*.deleteCookies("JSESSIONID")
		    
		    .and()
		    	.rememberMe().key("recoveredGroupRememberSecret")*/;
	}
	
	@Autowired
	private UserService userDetailsService;
	 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.authenticationProvider(authenticationProvider());
	}
	 
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
}
