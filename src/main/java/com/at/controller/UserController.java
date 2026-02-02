package com.at.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.at.dto.LoginFormDTO;
import com.at.dto.QuoteApiResponseDTO;
import com.at.dto.RegisterFormDTO;
import com.at.dto.ResetPasswordDTO;
import com.at.dto.UserDTO;
import com.at.service.DashboardService;
import com.at.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DashboardService dashoardService;
	
	@GetMapping("/register")
	public String loadRegister(Model model)
	{
		Map<Integer, String> countriesMap =userService.getCountries();
		model.addAttribute("countries",countriesMap);
		
		RegisterFormDTO registerFormDTO=new RegisterFormDTO();
		model.addAttribute("registerForm",registerFormDTO);
		
		return "register";
	}
	
	
	@GetMapping("/states/{countryId}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable Integer countryId)
	{
		Map<Integer, String> statesMap=userService.getStates(countryId);
	
		return statesMap;
	}
	
	@GetMapping("/cities/{stateId}")
	@ResponseBody
	public Map<Integer, String> getCities(@PathVariable Integer stateId)
	{
		return userService.getCities(stateId);
	}
	
	@PostMapping("/register")
	public String handleRegistration(RegisterFormDTO registerFormDTO, Model model)
	{
		boolean status=userService.duplicateEmailCheck(registerFormDTO.getEmail());
		
		if(status)
		{
			model.addAttribute("emsg","Duplicate Email found");
			model.addAttribute("registerForm", registerFormDTO);
		}else
		{
			boolean saveUser=userService.saveUser(registerFormDTO);
			if(saveUser)
			{
				model.addAttribute("smsg","Registration success, please check your email..!!");
				model.addAttribute("registerForm", new RegisterFormDTO());
			}else
			{
				model.addAttribute("emsg","Registation failed!!");
				model.addAttribute("registerForm", registerFormDTO);
			}
		}
		model.addAttribute("countries", userService.getCountries());
		return "register";
	}
	
	@GetMapping("/")
	public String index(Model model)
	{
		LoginFormDTO logingFormDTO=new LoginFormDTO();
		model.addAttribute("loginForm",logingFormDTO);
		
		return "login";
	}
	
	@PostMapping("/login")
	public String handleUserLogin(LoginFormDTO loginFormDTO, Model model)
	{ 
		UserDTO userDTO=userService.login(loginFormDTO);
		
		if(userDTO == null)
		{
			model.addAttribute("emsg", "Invalid Credentials"); 
			model.addAttribute("loginForm", new LoginFormDTO());
		}else
		{
			String passwordUpdated=userDTO.getPwdUpdated();
			if(passwordUpdated.equals("Yes"))
			{
				//display dashboard
				return "redirect:dashboard";
				
			}else
			{
				//display reset pwd page
				return "redirect:reset-pwd-page?email=" +userDTO.getEmail();
				
			}
		}
			
		return "login";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model)
	{
		QuoteApiResponseDTO quoteApiResponseDTO=dashoardService.getQuote();
	    model.addAttribute("quote",quoteApiResponseDTO);
	    return "dashboard";
	}
	
	@GetMapping("/reset-pwd-page")
	public String loadResetPasswordPage(@RequestParam("email") String email, Model model)
	{
		ResetPasswordDTO resetPasswordDTO= new ResetPasswordDTO();
		resetPasswordDTO.setEmail(email); 
		
		model.addAttribute("resetpassword",resetPasswordDTO);
		
		return "resetPassword";
	}
	
	@PostMapping("/resetPwd")
	public String handlePwdReset(ResetPasswordDTO resetPasswordDTO, Model model)
	{
	   boolean resetPwd= userService.resetpassword(resetPasswordDTO);
		
	   if(resetPwd)
	   {
		   return "redirect:dashboard";
	   }
	   
		return "resetPwd";  
	}
	
}
