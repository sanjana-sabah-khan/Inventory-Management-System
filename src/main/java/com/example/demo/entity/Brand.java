package com.example.demo.entity;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity (name= "brand")
@Table (name= "brand")
public class Brand {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int brand_id;
	
	private String brand_name;
	private String brand_contactNum;
	private String brand_email;
	private String brand_location;
	private String brand_status;
	private int brand_stock;
	private int brand_sold;
	private int brand_remaining;
	
	
	public Brand() {
		super();
		
	}
	
	public Brand(int brand_id, String brand_name, String brand_status, int brand_stock) {
		super();
		this.brand_id = brand_id;
		this.brand_name = brand_name;
		this.brand_status = brand_status;
		this.brand_stock = brand_stock;
	}
	
	
	public int getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}
	
	
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	
	
	public String getBrand_contactNum() {
		return brand_contactNum;
	}

	public void setBrand_contactNum(String brand_contactNum) {
		this.brand_contactNum = brand_contactNum;
	}

	
	public String getBrand_email() {
		return brand_email;
	}

	public void setBrand_email(String brand_email) {
		this.brand_email = brand_email;
	}

	
	public String getBrand_location() {
		return brand_location;
	}

	public void setBrand_location(String brand_location) {
		this.brand_location = brand_location;
	}

	
	public String getBrand_status() {
		return brand_status;
	}
	public void setBrand_status(String brand_status) {
		this.brand_status = brand_status;
	}
	
	
	public int getBrand_stock() {
		return brand_stock;
	}
	public void setBrand_stock(int brand_stock) {
		this.brand_stock = brand_stock;
	}
	
	
	public int getBrand_sold() {
		return brand_sold;
	}
	public void setBrand_sold(int brand_sold) {
		this.brand_sold = brand_sold;
	}
	
	
	public int getBrand_remaining() {
		return brand_remaining;
	}
	public void setBrand_remaining(int brand_remaining) {
		this.brand_remaining = brand_remaining;
	}
	
	
	

}
