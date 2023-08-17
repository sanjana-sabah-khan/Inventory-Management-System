package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.controller.CustomRegDetails;
import com.example.demo.entity.Registration;
import com.example.demo.repository.RegistrationRepository;


@Service
public class CustomRegDetailsService implements UserDetailsService {

	
	@Autowired
	private RegistrationRepository regRepo;
	
	
	//checks and fetches info from database by email and sends to class implementing user details 
	@Override
	public UserDetails loadUserByUsername (String admin_email) throws UsernameNotFoundException {
		
		Registration registration = regRepo.findByEmail(admin_email);
		
		if (registration == null) {
			throw new UsernameNotFoundException ("Email not found");
		}
		
		return new CustomRegDetails (registration);
	}

}
