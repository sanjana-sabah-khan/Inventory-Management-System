package com.example.demo.service;

import java.util.List;


import java.util.Optional;
import java.util.SplittableRandom;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.demo.entity.OTPNumber;
import com.example.demo.entity.Registration;
import com.example.demo.repository.OTPNumRepository;
import com.example.demo.repository.RegistrationRepository;

@Service
public class RegistrationService {

	@Autowired
	private RegistrationRepository regRepo;
	@Autowired
	private OTPNumRepository otpRepo;
	@Autowired
	private EmailSenderService service;
	
	
	//Constructor
	public RegistrationService (RegistrationRepository regRepo) {
		
		super();
		this.regRepo = regRepo;		
	}


	public List<Registration> getAllRegistries() {
		
		return regRepo.findAll();		
	}
	
	
	public Registration getRegById (int id) {
		
		Optional<Registration> reg = regRepo.findById(id);
		
		if (reg.isPresent()) {
			return reg.get();
		}
		
		return null;		
	}
	
	//gets row info by email from register
	public Registration getRegByEmail (String email) {
		
		Optional<Registration> reg = Optional.ofNullable(regRepo.findByEmail(email));
		
		if (reg.isPresent()) {
			return reg.get();
		}
		
		return null;		
	}
	
	//in otp table gets otp info by email
	public List <OTPNumber> getOTPByEmail (String email) {
		
		Optional<List <OTPNumber> > otp = Optional.ofNullable(otpRepo.findOTPByEmail(email));
		
		if (otp.isPresent()) {
			return otp.get();
		}
		
		return null;		
	}
	
	
	public void addRegistries (Registration registration) {
		
		regRepo.save (registration);		
	}
	
	
	//generates otp number and triggers email
	public void generateOTP (OTPNumber otpnum) {
		
		SplittableRandom splittableRandom = new SplittableRandom();
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0 ; i < 4; i++) {
			sb.append( splittableRandom.nextInt(0, 10) );
		}
		
		if (otpnum != null) {

			otpnum.setOtp_number( sb.toString() );
			otpRepo.save (otpnum);
			
			triggerMailR ( otpnum.getOtp_email(), otpnum.getOtp_number() );
		}
		
		else {
			System.out.println ("Couldnot find otp-------");
		}		
	}
	
	//mail content
	public void triggerMailR (String sendingto, String otpNum) {
		
		service.sendEmail (sendingto, "OTP Number for email verfication - IMS", "Your OTP Number is: " + otpNum);		
	}
	
	
	//check if otp entered is valid
	public Registration checkOTP (Registration registration, OTPNumber otpnum) {
		
		registration = getRegByEmail ( otpnum.getOtp_email() );
		
		List <OTPNumber> otpprev = getOTPByEmail ( otpnum.getOtp_email() );
		
		if (otpprev.get(0) != null  &&  registration != null) {
			
			if ( otpnum.getOtp_number().equals( otpprev.get(0).getOtp_number() ) ) {
				
				return registration;
			}
		}
		
		return null;	
	}
	
	
	//updates new password and confirm password in database after valid otp check 
	public void editReg (Registration registration, int id) {
		
		Registration reg = getRegById (id);
		
		if (reg != null) {
			
			reg.setAdmin_password( registration.getAdmin_password() );			
			reg.setAdmin_confirmPassword( registration.getAdmin_confirmPassword() );
			
			regRepo.save (reg);
		}
		
		else {
			System.out.println ("Couldnot Update password-------");
		}
	}
	
	//checks if there is an duplicate username or email already existing in register. will not allow duplicate
	public boolean checkDuplicate (Registration registration, HttpSession session) {
		
		List<Registration> reg = getAllRegistries();
		
		for (int i = 0; i < reg.size(); i++) {
			
			if ( reg.get(i).getAdmin_email().equals( registration.getAdmin_email() ) ) {

				session.setAttribute("message", "Registration failed. This Email Address already exists...");
				return true;
			}
			
			else if ( reg.get(i).getAdmin_name().equalsIgnoreCase( registration.getAdmin_name() ) ) {
				
				session.setAttribute("message", "Registration failed. This Username already exists...");
				return true;
			}
			
		}
		
		return false;	
	}
	
	//checks if password matches confirm password
	public boolean checkConfirmPassword (Registration registration, HttpSession session) {
		
		boolean check = false;
		
		if ( registration.getAdmin_confirmPassword().equals( registration.getAdmin_password() ) ) {
			check = true;
		}
		
		else {
			session.setAttribute("message", "Registration failed. Confirm Password does not match Password...");
		}
		
		return check;	
	}
	
	
	public void delete (int id) {
		
		otpRepo.deleteAll();
		
	}
	
	
}

