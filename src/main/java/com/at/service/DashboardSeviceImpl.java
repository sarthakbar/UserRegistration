package com.at.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.at.dto.QuoteApiResponseDTO;


@Service
public class DashboardSeviceImpl implements DashboardService{

	private String quoteApiURL="https://dummyjson.com/quotes/random";
	
	@Override
	public QuoteApiResponseDTO getQuote() {
		
		RestTemplate rt=new RestTemplate();
		
		ResponseEntity<QuoteApiResponseDTO> forEntity=
				rt.getForEntity(quoteApiURL, QuoteApiResponseDTO.class);
		
		return forEntity.getBody();
		
	}

}
