package com.at.dto;



import lombok.Data;

@Data
public class UserDTO {

	private Integer userId;
	private String name;
	private String email;
	private String password;
	private String pwdUpdated;
	
	
}
