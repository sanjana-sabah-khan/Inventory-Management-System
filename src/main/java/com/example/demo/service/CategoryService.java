package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryService {
	
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	//Constructor
	public CategoryService (CategoryRepository categoryRepository) {
		
		super();
		this.categoryRepository = categoryRepository;
		
	}

	//gives list of all categories store in database
	public List<Category> getAllCategories() {
		
		return categoryRepository.findAll();		
	}
	
	//adds to database
	public void addCategories(Category category) {
		
		categoryRepository.save(category);		
	}
	
	//gets row by id
	public Category getCateById (int id) {
		
		Optional<Category> category = categoryRepository.findById(id);
		
		if (category.isPresent()) {
			return category.get();
		}
		
		return null;		
	}
	
	//deletes row by id
	public void delete (int id) {
		
		categoryRepository.deleteById(id);		
	}
	
	
}
