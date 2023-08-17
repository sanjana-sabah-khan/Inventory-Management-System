package com.example.demo.entity;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//keeps record of all the finalized product ordered
@Entity (name= "proxy")
@Table (name= "proxy")
public class ProxyProdList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private int prodselected_id;
	
	private String prodselected_name;
	private int prodselected_price;
	private int prod_code;
	private int prodselected_quantity;
	private int order_no;
	
	@Column(nullable = false)
	private double discount;
	
	
	public ProxyProdList () {
		super();
	}


	public int getProdselected_id() {
		return prodselected_id;
	}

	public void setProdselected_id(int prodselected_id) {
		this.prodselected_id = prodselected_id;
	}


	public String getProdselected_name() {
		return prodselected_name;
	}

	public void setProdselected_name(String prodselected_name) {
		this.prodselected_name = prodselected_name;
	}


	public int getProdselected_price() {
		return prodselected_price;
	}

	public void setProdselected_price(int prodselected_price) {
		this.prodselected_price = prodselected_price;
	}


	public int getProd_code() {
		return prod_code;
	}

	public void setProd_code(int prod_code) {
		this.prod_code = prod_code;
	}


	public int getProdselected_quantity() {
		return prodselected_quantity;
	}

	public void setProdselected_quantity(int prodselected_quantity) {
		this.prodselected_quantity = prodselected_quantity;
	}


	public int getOrder_no() {
		return order_no;
	}

	public void setOrder_no (int order_no) {
		this.order_no = order_no;
	}

	
	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	
}
	
	

