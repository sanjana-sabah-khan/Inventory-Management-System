package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity (name= "registration")
@Table (name= "registration")
public class Registration {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int admin_id;
	
	@Column(nullable = false, unique = true)
	private String admin_name;
	
	private String admin_phone;
	
	@Column(nullable = false, unique = true)
	private String admin_email;
	
	@Column(nullable = false, length = 64)
	private String admin_password;	
	
	@Column(length = 64)
	private String admin_confirmPassword;
	
	
	public Registration() {
		super();
	}


	public Registration (int admin_id, String admin_name, String admin_phone, String admin_email, String admin_password, String admin_confirmPassword) {
		super();
		this.admin_id = admin_id;
		this.admin_name = admin_name;
		this.admin_phone = admin_phone;
		this.admin_email = admin_email;
		this.admin_password = admin_password;
		this.admin_confirmPassword = admin_confirmPassword;
	}


	public int getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}


	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}


	public String getAdmin_phone() {
		return admin_phone;
	}

	public void setAdmin_phone(String admin_phone) {
		this.admin_phone = admin_phone;
	}


	public String getAdmin_email() {
		return admin_email;
	}

	public void setAdmin_email(String admin_email) {
		this.admin_email = admin_email;
	}


	public String getAdmin_password() {
		return admin_password;
	}

	public void setAdmin_password(String admin_password) {
		this.admin_password = admin_password;
	}


	public String getAdmin_confirmPassword() {
		return admin_confirmPassword;
	}

	public void setAdmin_confirmPassword(String admin_confirmPassword) {
		this.admin_confirmPassword = admin_confirmPassword;
	}
	
	
	
}
	
	
	