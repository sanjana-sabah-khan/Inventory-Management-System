package com.example.demo.entity;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table (name= "product")
public class Product {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prod_id;
	
	private String prod_name;
	private String prod_desc;
	private int prod_price;
	private int prod_retail;
	private int prod_quantity;
	private int prod_barcode;
	private String prod_cate;
	private String prod_brand;
	private String prod_store;
	
	
	public Product() {
		super();
	}
	
	

	public int getProd_id() {
		return prod_id;
	}

	public void setProd_id(int prod_id) {
		this.prod_id = prod_id;
	}


	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}


	public String getProd_desc() {
		return prod_desc;
	}

	public void setProd_desc(String prod_desc) {
		this.prod_desc = prod_desc;
	}


	public int getProd_price() {
		return prod_price;
	}

	public void setProd_price(int prod_price) {
		this.prod_price = prod_price;
	}


	public int getProd_retail() {
		return prod_retail;
	}

	public void setProd_retail(int prod_retail) {
		this.prod_retail = prod_retail;
	}


	public int getProd_quantity() {
		return prod_quantity;
	}

	public void setProd_quantity(int prod_quantity) {
		this.prod_quantity = prod_quantity;
	}


	public int getProd_barcode() {
		return prod_barcode;
	}

	public void setProd_barcode(int prod_barcode) {
		this.prod_barcode = prod_barcode;
	}


	public String getProd_cate() {
		return prod_cate;
	}

	public void setProd_cate(String prod_cate) {
		this.prod_cate = prod_cate;
	}


	public String getProd_brand() {
		return prod_brand;
	}

	public void setProd_brand(String prod_brand) {
		this.prod_brand = prod_brand;
	}


	public String getProd_store() {
		return prod_store;
	}

	public void setProd_store(String prod_store) {
		this.prod_store = prod_store;
	}
	
	
}
	
	
	
