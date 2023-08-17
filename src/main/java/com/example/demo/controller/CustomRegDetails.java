package com.example.demo.controller;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.Registration;

//passes data from database to check login info
public class CustomRegDetails implements UserDetails {

	
	private Registration registration;
	
	
	public CustomRegDetails (Registration registration) {
		
		this.registration = registration;		
	}
	
		
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}

	
	@Override
	public String getPassword() {
		
		return registration.getAdmin_password();
	}

	
	@Override
	public String getUsername() {
		
		return registration.getAdmin_email();
	}

	
	@Override
	public boolean isAccountNonExpired() {
	
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
