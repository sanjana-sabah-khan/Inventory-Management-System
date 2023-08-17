package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cate_id;
	
	private String cate_name;
	private String cate_status;
	
	
	public Category (String cate_name, String cate_status) {
		super();
		this.cate_name = cate_name;
		this.cate_status = cate_status;
	}
	
	public Category () {
		super();
	}
	
	
	public int getCate_id () {
		return cate_id;
	}
	public void setCate_id (int cate_id) {
		this.cate_id = cate_id;
	}
	public String getCate_name () {
		return cate_name;
	}
	public void setCate_name (String cate_name) {
		this.cate_name = cate_name;
	}
	public String getCate_status () {
		return cate_status;
	}
	public void setCate_status (String cate_status) {
		this.cate_status = cate_status;
	}
	
	
	
	
	

}
