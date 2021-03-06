package com.tagtheagency.alcoholicsrecovered.dto;

public class UserDTO {

	private String firstName;
	private String email;
	private String password;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static UserDTO from(String firstName, String email, String password) {
		UserDTO dto = new UserDTO();
		dto.firstName = firstName;
		dto.email = email;
		dto.password = password;
		return dto;
	}
	
	
	
}
