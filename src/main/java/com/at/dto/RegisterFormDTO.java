package com.at.dto;


import lombok.Data;

@Data
public class RegisterFormDTO {

	private Integer userId;
	private String name;
	private String email;
	private String password;
	private String pwdUpdated;
	private Long phno;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
}
