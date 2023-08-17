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

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;



@Controller
public class CategoryController {
	
	
	@Autowired
	private CategoryService categoryService;

	
	//Constructor
	public CategoryController (CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}
	
	
	//home page
	@GetMapping("/")
	public String home (Model model) {
		
		List<Category> cate = categoryService.getAllCategories();
		model.addAttribute ("cate", cate);
		
		return "index";
		
	}
	
	
	@PostMapping("/addCategory")
	public String addCategories (@ModelAttribute Category category, HttpSession session) {
		
		categoryService.addCategories(category);
		session.setAttribute("message", "Category added successfully...");
		
		return "redirect:/";
	}
	
	//edit page
	@GetMapping("/edit/{id}")
	public String edit (@PathVariable int id, Model model) {
		
		List<Category> cate = categoryService.getAllCategories();
		model.addAttribute ("cate", cate);
		
		Category cateidget = categoryService.getCateById(id);
		model.addAttribute ("edit", cateidget);
		
		return "editCategory";
		
	}
	
	//Updates after edit
	@PostMapping("/updateCategory")
	public String update (@ModelAttribute Category category, HttpSession session) {
		
		categoryService.addCategories (category);
		session.setAttribute("message", "Category updated successfully...");
		
		return "redirect:/";
	}
	
	
	@GetMapping("/delete/{id}")
	public String delete (@PathVariable int id, HttpSession session) {
		
		categoryService.delete (id);
		session.setAttribute("message", "Category deleted...");
		
		return "redirect:/";
		
	}
	

	
}
