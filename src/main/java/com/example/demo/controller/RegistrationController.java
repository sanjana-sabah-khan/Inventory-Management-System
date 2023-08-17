package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.OTPNumber;
import com.example.demo.entity.Registration;
import com.example.demo.service.EmailSenderService;
import com.example.demo.service.RegistrationService;


@Controller
public class RegistrationController {
	
	@Autowired
	private RegistrationService registrationService;

	private int id= 0;
	
	
	//Constructor
	public RegistrationController (RegistrationService registrationService) {
		super();
		this.registrationService = registrationService;
	}
	
	//registration page
	@GetMapping("/registrationOpen")
	public String registration (Model model) {
		
		model.addAttribute ("reg", new Registration());
		
		return "registration";
		
	}
	
	//registration info added to database
	@PostMapping("/addRegistration")
	public String addRegister (@ModelAttribute Registration registration, HttpSession session) {
		
		boolean duplicateCheck = false;
		boolean check = false;
		
		duplicateCheck = registrationService.checkDuplicate(registration, session);
		check = registrationService.checkConfirmPassword(registration, session);
		
		if (!duplicateCheck  &&  check) {
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			String encodedPassword = encoder.encode (registration.getAdmin_password());
			registration.setAdmin_password(encodedPassword);
			
			session.setAttribute("message", "Registration successful...");
			registrationService.addRegistries (registration);
		}
		
		return "redirect:/registrationOpen";
	}
	
	
	//when user forgets password during login
	@GetMapping("/forgotPass")
	public String ForgotPass (Model model) {
		
		model.addAttribute ("proxyemail", new OTPNumber());
		
		return "forgotpassword";
	}
	
	//OTP code send in email
	@PostMapping("/emailforotp")
	public String OTPGenerator (@ModelAttribute OTPNumber otpnum, Model model) {
		
		model.addAttribute ("otp", otpnum);
		registrationService.generateOTP(otpnum);
		
		return "forgotpassword";
	}
		
	//submit otp to change password
	@PostMapping("/submitotp")
	public String SubmitOTP (@ModelAttribute OTPNumber otpnum, @ModelAttribute Registration registration, Model model, HttpSession session) {
		
		registration = registrationService.checkOTP (registration, otpnum);
		
		if (registration == null) {
			
			session.setAttribute("message", "OTP Number did not match or does not exist...");
		}
		
		else {
			
			id = registration.getAdmin_id();
			model.addAttribute("resubmitPass", registration);
		}
		
		registrationService.delete( otpnum.getId() );
		
		return "forgotpassword";
	}
	
	//new password and confirm password is added in register
	@PostMapping("/editReg")
	public String editRegister (@ModelAttribute Registration registration, HttpSession session) {
		
		boolean check = false;
		
		check = registrationService.checkConfirmPassword(registration, session);
		
		if (check &&  id != 0) {
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			String encodedPassword = encoder.encode (registration.getAdmin_password());
			registration.setAdmin_password(encodedPassword);
			
			registrationService.editReg (registration, id);
			
			session.setAttribute("message", "Password changed successfully...");
		}
		else {
			session.setAttribute("message", "Operation failed...");
		}
		
		return "login";
	}
	
	//checks login. if successful redirects to home page else returns tologin page
	@GetMapping("/login")
	public String Login ( HttpSession session ) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null  ||  authentication instanceof AnonymousAuthenticationToken) {
			
			return "login";
		}
		
		session.setAttribute("message", "Logged in successfully...");
		return "redirect:/";		
	}
	
	//logout
	@GetMapping("/logout")
	public String Logout () {
		
		return "login";		
	}
	
}
