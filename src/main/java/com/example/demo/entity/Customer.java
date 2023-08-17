package com.example.demo.entity;


import javax.persistence.Entity;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table (name= "customer")
public class Customer {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customer_id;
	private String name;
	private String address;
	private String phone_no;
	private String paid_status;
	
	private String entry_date;
	
	
	public Customer() {
		super();
	}
	
	public Customer(int customer_id, String name, String address, String phone_no, String paid_status, String entry_date) {
		super();
		this.customer_id = customer_id;
		this.name = name;
		this.address = address;
		this.phone_no = phone_no;
		this.paid_status = paid_status;
		this.entry_date = entry_date;
	}
	
	
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	
	public String getPaid_status() {
		return paid_status;
	}

	public void setPaid_status(String paid_status) {
		this.paid_status = paid_status;
	}
	
	public String getEntry_date() {
		return entry_date;
	}
	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	
}