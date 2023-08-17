package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Customer;
import com.example.demo.service.CusService;



@Controller
public class CusController {
	
	
	@Autowired
	private CusService cusService;

	
	//Constructor
	public CusController (CusService cusService) {
		super();
		this.cusService = cusService;
	}
	
	//customer page
	@GetMapping("/customerOpen")
	public String customer (Model model) {
		
		List<Customer> cus = cusService.getAllCustomers();
		model.addAttribute ("cus", cus);
		
		return "customer";
		
	}
	
	
	@PostMapping("/addcustomer")
	public String addCustomers (@ModelAttribute Customer customer, HttpSession session) {
		
		cusService.addCustomers (customer);
		session.setAttribute("message", "Customer added successfully...");
		
		return "redirect:/customerOpen";
	}
	
	
	@GetMapping("/editcustomer/{id}")
	public String edit (@PathVariable int id, Model model) {
		
		List<Customer> cus = cusService.getAllCustomers();
		model.addAttribute ("cus", cus);
		
		Customer cusidget = cusService.getCustomerById(id);
		model.addAttribute ("edit", cusidget);
		
		return "editCustomer";
		
	}
	
	
	@PostMapping("/updatecustomer")
	public String update (@ModelAttribute Customer customer, HttpSession session) {
		
		cusService.addCustomers(customer);
		session.setAttribute("message", "Customer updated successfully...");
		
		return "redirect:/customerOpen";
	}
	
	
	@GetMapping("/deletecustomer/{id}")
	public String delete (@PathVariable int id, HttpSession session) {
		
		cusService.delete (id);
		session.setAttribute("message", "Customer deleted...");
		
		return "redirect:/customerOpen";
		
	}
	
	

}

