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
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.service.BrandService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.StoreService;


@Controller
public class ProductController {
	
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private BrandService brandService;

	
	//Constructor
	public ProductController (ProductService productService) {
		super();
		this.productService = productService;
	}
	
	
	@GetMapping("/productOpen")
	public String product (Model model) {
		
		List<Product> prod = productService.getAllProducts();
		model.addAttribute ("prod", prod);
		
		List<Category> cate = categoryService.getAllCategories();
		model.addAttribute ("cate", cate);
		
		List<Brand> br = brandService.getAllBrands();
		model.addAttribute ("viewbr", br);
		
		List<Store> st = storeService.getAllStores();
		model.addAttribute ("st", st);
		
		
		return "product";
		
	}
	
	
	@PostMapping("/addproduct")
	public String addProducts (@ModelAttribute Product product, HttpSession session) {
		
		productService.addProducts(product);
		session.setAttribute("message", "Product added successfully...");
		
		return "redirect:/productOpen";
	}
	
	
	@GetMapping("/editproduct/{id}")
	public String edit (@PathVariable int id, Model model) {
		
		List<Product> prod = productService.getAllProducts();
		model.addAttribute ("prod", prod);
		
		List<Category> cate = categoryService.getAllCategories();
		model.addAttribute ("cate", cate);
		
		List<Brand> br = brandService.getAllBrands();
		model.addAttribute ("viewbr", br);
		
		List<Store> st = storeService.getAllStores();
		model.addAttribute ("st", st);
		
		Product prodidget = productService.getProductById(id);
		model.addAttribute ("edit", prodidget);
		
		return "editProduct";
		
	}
	
	
	@PostMapping("/updateproduct")
	public String update (@ModelAttribute Product product, HttpSession session) {
		
		productService.addProducts (product);
		session.setAttribute("message", "Product updated successfully...");
		
		return "redirect:/productOpen";
	}
	
	//generates bar code
	@PostMapping("/barcode/{id}")
	public String barcode (@PathVariable int id, HttpSession session) {
		
		productService.generateBarcode (id);
		session.setAttribute("message", "Barcode generated successfully...");
		
		return "redirect:/productOpen";
	}
	
	
	@GetMapping("/deleteproduct/{id}")
	public String delete (@PathVariable int id, HttpSession session) {
		
		productService.delete (id);
		session.setAttribute("message", "Product deleted...");
		
		return "redirect:/productOpen";
		
	}
	
	

}
