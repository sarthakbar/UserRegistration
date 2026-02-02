package com.at.service;

import java.util.Map;

import com.at.dto.LoginFormDTO;
import com.at.dto.RegisterFormDTO;
import com.at.dto.ResetPasswordDTO;
import com.at.dto.UserDTO;

public interface UserService {

	public Map<Integer, String> getCountries();
	public Map<Integer, String> getStates(Integer countryId);
	public Map<Integer, String> getCities(Integer stateId);
	public boolean duplicateEmailCheck(String email);
	public boolean saveUser(RegisterFormDTO regFormDTO);
	public UserDTO login(LoginFormDTO loginFormDTO);
	public boolean resetpassword(ResetPasswordDTO resetPwdDTO);
	
}
