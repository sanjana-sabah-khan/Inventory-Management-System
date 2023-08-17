package com.example.demo.entity;

import javax.persistence.Column;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity (name= "orders")
@Table (name= "orders")
public class Order {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private int order_id;
	
	private int order_code;
	
	@Column(nullable = false)
	private double vat;
	private float sub_total;
	private float total_amount;
	private int customer_unique;
	private float pay;
	
	
	public Order() {
		super();
	}


	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}


	public int getOrder_code() {
		return order_code;
	}

	public void setOrder_code(int order_code) {
		this.order_code = order_code;
	}


	public int getCustomer_unique() {
		return customer_unique;
	}

	public void setCustomer_unique(int customer_unique) {
		this.customer_unique = customer_unique;
	}


	public float getSub_total() {
		return sub_total;
	}

	public void setSub_total(float sub_total) {
		this.sub_total = sub_total;
	}


	public float getPay() {
		return pay;
	}

	public void setPay(float pay) {
		this.pay = pay;
	}


	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}


	public float getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(float total_amount) {
		this.total_amount = total_amount;
	}
		
	
}
	