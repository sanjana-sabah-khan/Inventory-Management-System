package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CusRepository;


@Service
public class CusService {

	
	@Autowired
	private CusRepository cusRepository;
	
	
	//Constructor
	public CusService (CusRepository cusRepository) {
		
		super();
		this.cusRepository = cusRepository;
		
	}


	public List <Customer> getAllCustomers() {
			
		return cusRepository.findAll();
			
	}
	
	
	public void addCustomers(Customer customer) {
		
		cusRepository.save(customer);
		
	}
	
	
	public Customer getCustomerById (int id) {
		
		Optional<Customer> customer = cusRepository.findById(id);
		
		if (customer.isPresent()) {
			return customer.get();
		}
		
		return null;
		
	}
	
	
	public void delete (int id) {
		
		cusRepository.deleteById(id);
		
	}
	
	
	
}

