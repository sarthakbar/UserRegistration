package com.at.service;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.at.dto.LoginFormDTO;
import com.at.dto.RegisterFormDTO;
import com.at.dto.ResetPasswordDTO;
import com.at.dto.UserDTO;
import com.at.entity.CityEntity;
import com.at.entity.CountryEntity;
import com.at.entity.StateEntity;
import com.at.entity.UserEntity;
import com.at.repo.CityRepository;
import com.at.repo.CountryRepository;
import com.at.repo.StateRepository;
import com.at.repo.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	private CountryRepository countryRepo;
	
	private StateRepository stateRepo;
	private CityRepository cityRepo;
	private UserRepository userRepo;
	private EmailService emailService;
	
	
	public UserServiceImpl(CountryRepository countryRepo, StateRepository stateRepo, CityRepository cityRepo,UserRepository userRepo, EmailService emailService) {
		this.countryRepo = countryRepo;
		this.stateRepo = stateRepo;
		this.cityRepo = cityRepo;
		this.userRepo=userRepo;
		this.emailService=emailService;
	}
	
	Random random=new Random();
	
	@Override
	public Map<Integer, String> getCountries() {
		
		return countryRepo.findAll()
				                       .stream()
				                       .collect(Collectors.toMap(
				                    		    country-> country.getCountryId(),
				                    		    country->country.getCountryName()));
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		
		Map<Integer, String> states=stateRepo.findByCountryId(countryId)
				                    .stream()
				                    .collect(Collectors.toMap(
	                                         state->state.getStateId(),
				                    		 state->state.getStateName()
				                    		 ));
		
		
		return states;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		
		return cityRepo.findByStateId(stateId)
				                   .stream()
				                   .collect(Collectors.toMap(
                              	            city->city.getCityId(),
                              	            city->city.getCityName()
                              	            ));
	
	}

	@Override
	public boolean duplicateEmailCheck(String email) {
		      
		return userRepo.existsByEmail(email);
		
		
	}

	
	@Override
	public boolean saveUser(RegisterFormDTO regFormDTO) {
		
		UserEntity entity=new UserEntity();
		
		BeanUtils.copyProperties(regFormDTO, entity);
		
		CountryEntity country=countryRepo.findById(regFormDTO.getCountryId()).orElse(null);
		entity.setCountry(country);
		
		StateEntity state=stateRepo.findById(regFormDTO.getStateId()).orElse(null);
		entity.setState(state);
		
		CityEntity city=cityRepo.findById(regFormDTO.getCityId()).orElse(null);
		entity.setCity(city);
		
		String randomPassword=generateRandomPassword();
		
		
		entity.setPassword(randomPassword);
		entity.setPwdUpdated("no");
		
		UserEntity savedUser=userRepo.save(entity);
		
	 if(null!=savedUser.getUserId())
	 {
		 String subject="Your account created";
		 String body="Your password to login: "+randomPassword;
		 String to=regFormDTO.getEmail();
		 
		 emailService.sendEmail(subject, body, to);
		 return true;
	 }
		
		return false;
	}
	

	@Override
	public UserDTO login(LoginFormDTO loginFormDTO) {
		
		UserEntity userEntity=userRepo.findByEmailAndPassword(loginFormDTO.getEmail(), loginFormDTO.getPassword());
		
		if(userEntity!=null)
		{
			UserDTO userDTO=new UserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
			return userDTO;
		}
		
		return null;
	}

	@Override
	public boolean resetpassword(ResetPasswordDTO resetPwdDTO) {
		
		
		UserEntity userEnt=userRepo.findByEmail(resetPwdDTO.getEmail());
		
		if(userEnt==null)
		{
			return false;
		}
		
		userEnt.setPassword(resetPwdDTO.getNewpwd());
		userEnt.setPwdUpdated("Yes");
		userRepo.save(userEnt);  //UPSERT
		
		
		return true;
	}
	
	private String generateRandomPassword()
	{
		String upperCase="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCase="abcdefghijklmnopqrstuvwxyz";
		
		String alphabet=upperCase+lowerCase;
		
		
		
		StringBuilder generatedPassword=new StringBuilder();
		
		for(int i=0;i<5;i++)
		{
			int randomIndex=random.nextInt(alphabet.length());
			generatedPassword.append(alphabet.charAt(randomIndex));
		}
		
		return generatedPassword.toString();
		
	}

}
