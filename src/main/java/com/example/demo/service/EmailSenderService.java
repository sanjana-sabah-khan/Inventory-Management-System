package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderService {

	
	@Autowired
	private JavaMailSender mailsender;
	

	public void sendEmail (String sendTo, String subject, String body) {
		
		SimpleMailMessage message = new SimpleMailMessage();

			message.setFrom ("tsujisanjo1999@gmail.com");
			message.setTo ( sendTo );
			message.setSubject (subject);
			message.setText (body);
		
			mailsender.send (message);
			
		
		System.out.println ("Mail Sent...");
		
	}
	
	
	

	
	
}
