package com.at.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	JavaMailSender mailsender;
	
	public boolean sendEmail(String subject, String body, String to)
	{
		
		try
		{
			SimpleMailMessage sendMail=new SimpleMailMessage();
			
			sendMail.setSubject(subject);
			sendMail.setText(body);
			sendMail.setTo(to);
			
			mailsender.send(sendMail);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return true;
	}
}
