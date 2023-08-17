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

import com.example.demo.entity.Brand;
import com.example.demo.service.BrandService;


@Controller
public class BrandController {
	
	
	@Autowired
	private BrandService brandService;
	
	
	//Constructor
	public BrandController (BrandService brandService) {
		super();
		this.brandService = brandService;
	}
	
	//brand page
	@GetMapping("/brandOpen")
	public String brand (Model model) {
		
		List<Brand> br = brandService.getAllBrands();
		model.addAttribute ("viewbr", br);
		
		return "brand";
		
	}
	
	
	@PostMapping("/addbrand")
	public String addBrands (@ModelAttribute Brand brand, HttpSession session) {
		
		brandService.addBrands(brand);
		session.setAttribute("message", "Brand added successfully...");
		
		return "redirect:/brandOpen";
	}
	
	
	//sends email if brand stock is empty
	@PostMapping("/emailbrand")
	public String emailBrands (HttpSession session) {		
		
		brandService.sendMailToBrand (session);
		
		return "redirect:/brandOpen";
	}
	
	
	@GetMapping("/editbrand/{id}")
	public String edit (@PathVariable int id, Model model) {
		
		List<Brand> br = brandService.getAllBrands();
		model.addAttribute ("viewbr", br);
		
		Brand bridget = brandService.getBrandById(id);
		model.addAttribute ("edit", bridget);
		
		return "editBrand";
		
	}
	
	
	@PostMapping("/updatebrand")
	public String update (@ModelAttribute Brand brand, HttpSession session) {
		
		brandService.addBrands (brand);
		session.setAttribute("message", "Brand updated successfully...");
		
		return "redirect:/brandOpen";
	}
	
	
	@GetMapping("/deletebrand/{id}")
	public String delete (@PathVariable int id, HttpSession session) {
		
		brandService.delete (id);
		session.setAttribute("message", "Brand deleted...");
		
		return "redirect:/brandOpen";
		
	}
	
	

}
