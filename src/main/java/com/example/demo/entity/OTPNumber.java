package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity (name= "otp")
@Table (name= "otp")
public class OTPNumber {

		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String otp_email;
	private String otp_number;
	
	
	public OTPNumber() {
		super();
	}
	
	
	public OTPNumber(int id, String otp_email, String otp_number) {
		super();
		this.id = id;
		this.otp_email = otp_email;
		this.otp_number = otp_number;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getOtp_email() {
		return otp_email;
	}

	public void setOtp_email(String otp_email) {
		this.otp_email = otp_email;
	}


	public String getOtp_number() {
		return otp_number;
	}

	public void setOtp_number(String otp_number) {
		this.otp_number = otp_number;
	}


	


	
}
