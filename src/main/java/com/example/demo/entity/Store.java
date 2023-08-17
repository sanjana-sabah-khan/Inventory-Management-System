package com.example.demo.entity;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity (name= "store")
@Table (name= "store")
public class Store {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int store_id;
	
	private String store_name;
	private String store_contactNum;
	private String store_email;
	private String store_location;
	private String store_status;
	private int store_stock;
	private int store_sold;
	private int store_remaining;
	
	
	public Store() {
		super();	
	}
	
	public Store(int store_id, String store_name, String store_status, int store_stock) {
		super();
		this.store_id = store_id;
		this.store_name = store_name;
		this.store_status = store_status;
		this.store_stock = store_stock;
	}


	public int getStore_id() {
		return store_id;
	}


	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}


	public String getStore_name() {
		return store_name;
	}


	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	
	public String getStore_contactNum() {
		return store_contactNum;
	}

	public void setStore_contactNum(String store_contactNum) {
		this.store_contactNum = store_contactNum;
	}

	
	public String getStore_email() {
		return store_email;
	}

	public void setStore_email(String store_email) {
		this.store_email = store_email;
	}

	
	public String getStore_location() {
		return store_location;
	}

	public void setStore_location(String store_location) {
		this.store_location = store_location;
	}

	
	public String getStore_status() {
		return store_status;
	}


	public void setStore_status(String store_status) {
		this.store_status = store_status;
	}


	public int getStore_stock() {
		return store_stock;
	}


	public void setStore_stock(int store_stock) {
		this.store_stock = store_stock;
	}


	public int getStore_sold() {
		return store_sold;
	}


	public void setStore_sold(int store_sold) {
		this.store_sold = store_sold;
	}


	public int getStore_remaining() {
		return store_remaining;
	}


	public void setStore_remaining(int store_remaining) {
		this.store_remaining = store_remaining;
	}
	
		

}
