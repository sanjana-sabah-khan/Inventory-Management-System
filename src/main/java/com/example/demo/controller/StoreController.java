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
import com.example.demo.entity.Store;
import com.example.demo.service.StoreService;


@Controller
public class StoreController {
	
	
	@Autowired
	private StoreService storeService;

	
	//Constructor
	public StoreController (StoreService storeService) {
		super();
		this.storeService = storeService;
	}
	
	//store page
	@GetMapping("/storesOpen")
	public String stores (Model model) {
		
		List<Store> st = storeService.getAllStores();
		model.addAttribute ("st", st);
		
		return "stores";
		
	}
	
	
	@PostMapping("/addstore")
	public String addStores (@ModelAttribute Store store, HttpSession session) {
		
		storeService.addStores(store);
		session.setAttribute("message", "Store added successfully...");
		
		return "redirect:/storesOpen";
	}
	
	//emails if store stock empty
	@PostMapping("/emailstore")
	public String emailStores (HttpSession session) {		
		
		storeService.sendMailToStore (session);
		
		return "redirect:/storeOpen";
	}
	
	
	@GetMapping("/editstore/{id}")
	public String edit (@PathVariable int id, Model model) {
		
		List<Store> st = storeService.getAllStores();
		model.addAttribute ("st", st);
		
		Store stidget = storeService.getStoreById(id);
		model.addAttribute ("edit", stidget);
		
		return "editStore";
		
	}
	
	
	@PostMapping("/updatestore")
	public String update (@ModelAttribute Store store, HttpSession session) {
		
		storeService.addStores(store);
		session.setAttribute("message", "Store updated successfully...");
		
		return "redirect:/storesOpen";
	}
	
	
	@GetMapping("/deletestore/{id}")
	public String delete (@PathVariable int id, HttpSession session) {
		
		storeService.delete (id);
		session.setAttribute("message", "Store deleted...");
		
		return "redirect:/storesOpen";
		
	}
	
	

}
