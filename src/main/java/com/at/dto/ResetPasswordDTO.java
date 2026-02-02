package com.at.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {

	private String email;
	private String newpwd;
	private String confirmPwd;
}
