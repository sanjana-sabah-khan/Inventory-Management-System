package com.example.demo.controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderProductSelected;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProxyProdList;
import com.example.demo.service.OrderService;


@Controller
public class OrderController {	
	
	@Autowired
	private OrderService orderService;
	
	
	private Product product;
	private List<Product> prodlist = new ArrayList<Product>();
	int countOrder, id= 0;

	
	//Constructor
	public OrderController (OrderService orderService) {
		super();
		this.orderService = orderService;
	}
	
	
	//order page
	@GetMapping("/orderOpen")
	public String order (Model model) {
		
		List<ProxyProdList> prodselected = orderService.getAllProxy ();
		model.addAttribute ("prodselected", prodselected);
		
		List<OrderProductSelected> listselected = orderService.getAllSelected ();
		model.addAttribute ("selected", listselected);
		
		model.addAttribute ("prod", product);
		model.addAttribute ("prodcodeid", id);
		
		List<Order> ord = orderService.getAllOrders();
		model.addAttribute ("ord", ord);
		
		Order ordidget = orderService.getOrderById (countOrder);
		model.addAttribute ("ordcode", ordidget);
		
		countOrder = 0;
		
		return "order";		
	}
	
	
	//adds product bar code to database - displays product info in input
	@PostMapping("/addproductcode")
	public String addProdCode (@ModelAttribute ProxyProdList proxy, HttpSession session) {
		
		OrderProductSelected selected = null;
		product = orderService.findProdByProdcode (proxy, prodlist, selected);
		
		if (product != null) {
			orderService.addProdcode (proxy);
			id = proxy.getProdselected_id();
		}
		else {
			session.setAttribute("message", "Request Failed. Product Barcode not found. Please enter an existing Barcode...");
		}
		
		return "redirect:/orderOpen";
	}
	
	
	//add product info to both proxy (all ordered products listed) and selected product list table (acts like a shopping cart)
	@PostMapping("/addlistedprod/{id}")
	public String addListProd (@PathVariable int id, @ModelAttribute ProxyProdList proxy, HttpSession session) {
		
		proxy.setProdselected_id (id);
		
		OrderProductSelected selected = null;
		boolean check = orderService.checkBrandStoreValidity (proxy, prodlist, selected, session);
		
		if (check == false) {
			orderService.deleteProxy (id);
		}
		
		return "redirect:/orderOpen";
	}
	
	
	//adds order and vat and displays calculation to input, also determines stock activities
	@PostMapping("/addordercode")
	public String addOrders (@ModelAttribute Order order, @ModelAttribute ProxyProdList proxy, OrderProductSelected selected, HttpSession session) {
		
		prodlist = null;
		product = null;
		boolean brandStockout = orderService.UpdateBrands (proxy, selected, prodlist, product);
		boolean storeStockout = orderService.UpdateStores (proxy, selected, prodlist, product);
		
		countOrder = orderService.addOrderCode (order, proxy, selected);
		
		if (brandStockout) {
			session.setAttribute("message", "Stock for this Brand has ended. Send email for restock?");
		}
		else if (storeStockout) {
			session.setAttribute("message", "Stock for this Store has ended. Send email for restock?");
		}
		else if ( brandStockout  &&  storeStockout ) {
			session.setAttribute("message", "Stock for both this Brand and Store has ended. Send email for restock?");			
		}
		else {
			session.setAttribute("message", "Order added successfully...");
		}
		
		return "redirect:/orderOpen";
	}
	
	
	//adds customer id and payment
	@PostMapping("/addpayandcus")
	public String addPay (@ModelAttribute Order order, HttpSession session) {
		
		orderService.addPayandCus (order);
		session.setAttribute("message", "Order saved successfully...");
		
		return "redirect:/orderOpen";
	}
	
	
	//edit order page
	@GetMapping("/editproxyprodlist/{id}")
	public String editProdlist (@PathVariable int id, Model model) {
		
		List<ProxyProdList> prodselected = orderService.getAllProxy ();
		model.addAttribute ("prodselected", prodselected);
		
		ProxyProdList proxyidget = orderService.getProxyById(id);
		model.addAttribute ("edit", proxyidget);
		
		List<Order> ord = orderService.getAllOrders();
		model.addAttribute ("ord", ord);
		
		Order ordidget = orderService.getOrderById (id);
		model.addAttribute ("editord", ordidget);
		

		return "editOrderProdList";		
	}
	
	//updates the product list
	@PostMapping("/updateprodlist")
	public String update (@ModelAttribute ProxyProdList proxy, OrderProductSelected selected, HttpSession session) {
		
		orderService.addSelectedProd (proxy, selected);
		
		session.setAttribute("message", "Order updated successfully...");
		
		return "redirect:/orderOpen";
	}
	
	//updates the order info
	@PostMapping("/updateorder")
	public String updateOrder (@ModelAttribute Order order, HttpSession session) {
		
		orderService.addPayandCus (order);
		
		session.setAttribute("message", "Order updated successfully...");
		
		return "redirect:/orderOpen";
	}
		

	
	@GetMapping("/deleteproxyprodlist/{id}")
	public String deleteProxyList (@PathVariable int id, HttpSession session) {
		
		orderService.deleteProxy (id);
		session.setAttribute("message", "Order deleted...");
		
		return "redirect:/orderOpen";		
	}
	
	//deletes from selected product list
	@GetMapping("/deleteprodlist/{id}")
	public String deleteSelectedList (@PathVariable int id, HttpSession session) {
		
		orderService.deleteSelected (id);
		session.setAttribute("message", "Order deleted...");
		
		return "redirect:/orderOpen";		
	}
	
	
	@GetMapping("/deleteorder/{id}")
	public String deleteOrder (@PathVariable int id, HttpSession session) {
		
		orderService.deleteOrder (id);
		session.setAttribute("message", "Order deleted...");
		
		return "redirect:/orderOpen";		
	}
		

}
