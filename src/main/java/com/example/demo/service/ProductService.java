package com.example.demo.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;



@Service
public class ProductService {

	
	@Autowired
	private ProductRepository productRepository;
	
	
	//Constructor
	public ProductService (ProductRepository productRepository) {
		
		super();
		this.productRepository = productRepository;
		
	}


	public List <Product> getAllProducts() {
		
		return productRepository.findAll();
		
	}
	
	
	public void addProducts (Product product) {
		
		productRepository.save(product);
		
	}
	
	//generates unique bar code from product id
	public void generateBarcode (int id) {
		
		Product get;
		
		Optional<Product> product = productRepository.findById(id);
				
		if (product.isPresent()) {
			get = product.get();
			get.setProd_barcode ( Integer.parseInt( "1000" + get.getProd_id() ) );
			productRepository.save (get);
		}
		
	}
	
	
	public Product getProductById (int id) {
		
		Optional<Product> product = productRepository.findById(id);
		
		if (product.isPresent()) {
			return product.get();
		}
		
		return null;
		
	}
	
	
	public void delete (int id) {
		
		productRepository.deleteById(id);
		
	}
	
	
	
}
