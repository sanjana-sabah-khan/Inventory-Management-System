package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderProductSelected;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProxyProdList;
import com.example.demo.repository.OrderProductSelectedRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProxyProdListRepository;


@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;	
	@Autowired
	private ProxyProdListRepository proxylistRepo;	
	@Autowired
	private OrderProductSelectedRepository selectedRepo;
	@Autowired
	private ProductRepository prodRepo;
	@Autowired
	private BrandService brandService;
	@Autowired
	private StoreService storeService;
	
	private static List <Integer> proxyid =  new ArrayList <Integer>();
	
	
	//Constructor
	public OrderService (OrderRepository orderRepository) {
		
		super();
		this.orderRepository = orderRepository;		
	}

	
	
	public List<ProxyProdList> getAllProxy () {
		
		return proxylistRepo.findAll();		
	}
	
	
	public List <OrderProductSelected> getAllSelected () {
		
		return selectedRepo.findAll();		
	}
	

	public List<Order> getAllOrders() {
		
		return orderRepository.findAll();
	}	
	
	//find valid product from product table by product code from orders (product bar code)
	public Product findProdByProdcode (ProxyProdList proxy, List<Product> prodlist, OrderProductSelected selected) {
		
		prodlist = prodRepo.findAll();
		
		if (selected != null) {
			
			for (int i = 0; i < prodlist.size(); i++) {
				
				if (selected.getProd_code() == prodlist.get(i).getProd_barcode()) {
					return prodlist.get(i);
				}
			}
			return null;
		}
		
		
		for (int i = 0; i < prodlist.size(); i++) {
			
			if (proxy.getProd_code() == prodlist.get(i).getProd_barcode()) {
				return prodlist.get(i);
			}
		}
		return null;
	}
	
	//update sold and remaining info after the product from this brand is taken
	public boolean UpdateBrands(ProxyProdList proxy, OrderProductSelected selected, List<Product> prodlist, Product prod) {
				
		int [] arr = traceListedProducts();
		boolean stockOut = false;
	
		for (int i = 0; i < arr.length; i++) {
			
			selected = getSelectedById ( arr[i] );
			
			if (selected != null) {
				
				prod = findProdByProdcode(proxy, prodlist, selected);
				
				if (prod != null) {
				
					stockOut = brandService.UpdateBrandInStock (prod, selected);					
				}
			}
			
		}
		return stockOut;
	}
	
	//update sold and remaining info after the product from this store is taken
	public boolean UpdateStores(ProxyProdList proxy, OrderProductSelected selected, List<Product> prodlist, Product prod) {
		
		int [] arr = traceListedProducts();
		boolean stockOut = false;
	
		for (int i = 0; i < arr.length; i++) {
			
			selected = getSelectedById ( arr[i] );
			
			if (selected != null) {
				
				prod = findProdByProdcode(proxy, prodlist, selected);
				
				if (prod != null) {
	
					stockOut = storeService.UpdateStoreInStock (prod, selected);
				}
			}
			
		}
		return stockOut;
	}
	
	
	
	public void addProdcode (ProxyProdList proxy) {
		
		proxylistRepo.save(proxy);
	}
	
	//checks if request is valid before brand & store update
	public boolean checkBrandStoreValidity (ProxyProdList proxy, List <Product> prodlist, OrderProductSelected selected, HttpSession session) {
	
		Product prod = findProdByProdcode (proxy, prodlist, selected);
		boolean isValid = true;
				
		isValid = brandService.checkBrandValidity(proxy, prod);		
		if (isValid == false) {
			
			session.setAttribute("message", "Request Failed. Selected quantity is greater than remaining items in Brand...");
			return isValid;
		}
		
		
		isValid = storeService.checkStoreValidity(proxy, prod);		
		if (isValid == false) {
			
			session.setAttribute("message", "Request Failed. Selected quantity is greater than remaining items in Store...");
			return isValid;
		}
		
		addSelectedProd (proxy, selected);
		return isValid;	
	}
	
	
	public void addSelectedProd (ProxyProdList proxy, OrderProductSelected selected) {
		
		proxyid.add (proxy.getProdselected_id());
		
		proxylistRepo.save(proxy);
		saveInselected (proxy, selected);		
	}
	
	//after proxy table is filled from input, temporary data is stored in prodselected table auto-incrementally  
	public void saveInselected (ProxyProdList proxy, OrderProductSelected selected) {
			
		selected = new OrderProductSelected();
		
		selected.setProdselected_name( proxy.getProdselected_name() );
		selected.setProdselected_price( proxy.getProdselected_price() );
		selected.setProdselected_quantity( proxy.getProdselected_quantity() );
		selected.setProd_code( proxy.getProd_code() );
		selected.setDiscount( proxy.getDiscount() );
		
		selectedRepo.save(selected);		
	}
	
	//adds order code and calls for calculation of sumtotal, vat, discount, total amount. can also be used for editing a row based on ordercode
	public int addOrderCode (Order order, ProxyProdList proxy, OrderProductSelected selected) {
		
		Order orderprev = getOrderByOrder_code( order.getOrder_code() );
		
		if (orderprev != null) {
			int j = orderprev.getOrder_id();
			orderprev = order;
			orderprev.setOrder_id (j);
		}
		else {
			orderprev = order;
		}
		
		orderprev.setSub_total( subTotal (selected) );
		orderprev.setTotal_amount( totalAmount (selected, order) );		
		orderRepository.save (orderprev);
		
		UpdateOrderForList (orderprev, proxy);
		
		selectedRepo.deleteAll();
		proxyid.clear();
		
		return orderprev.getOrder_id();	
	}

	//ids of listed products in prodseleceted table is stored in array to be used in calculations 
	public int[] traceListedProducts() {
		
		List <OrderProductSelected> duplicate = new ArrayList <OrderProductSelected>();
		duplicate = selectedRepo.findAll();
		
		int[] array = new int [ duplicate.size() ];
		
		for (int i = 0; i < duplicate.size(); i++) {
			
			if (duplicate.get(i) != null)
				array[i] = duplicate.get(i).getProdselected_id();
        }		
		return array;		
	}
	
	
	public float subTotal (OrderProductSelected selected) {
		
		float sum = 0;
		float discount;
		
		int [] arr = traceListedProducts(); 
		
		for (int i = 0; i < arr.length; i++) {
			
			selected = getSelectedById ( arr[i] );
			
			if (selected != null) {
				sum = sum + ( selected.getProdselected_price() * selected.getProdselected_quantity() );
				discount = sum * (float) selected.getDiscount()/100;
				sum = sum - discount;
			}
		}		
		return sum;
	}
	
	
	public float totalAmount (OrderProductSelected selected, Order order) {
		
		float subtotal = subTotal(selected);
		float vat;
		float total;
		
		vat = subtotal * ( (float) order.getVat()/100 );
		total = subtotal + vat;
		
		return total;
	}
	
	//updates the order number in proxy table after order number is inputted
	public void UpdateOrderForList (Order order, ProxyProdList proxy) {
		
		for (int i = 0; i < proxyid.size(); i++) {
			
			proxy = getProxyById ( proxyid.get(i) );
			
			if (proxy != null) {
				proxy.setOrder_no( order.getOrder_code() );
				proxylistRepo.save(proxy);
			}
		}
	}
	

	public void addPayandCus (Order order) {
		
		orderRepository.save(order);	
	}
	
	

	public ProxyProdList getProxyById (int id) {
		
		Optional<ProxyProdList> proxy = proxylistRepo.findById(id);
		
		if (proxy.isPresent()) {
			return proxy.get();
		}
		
		return null;		
	}
	
	
	public OrderProductSelected getSelectedById (int id) {
		
		Optional<OrderProductSelected> selected = selectedRepo.findById(id);
		
		if (selected.isPresent()) {
			return selected.get();
		}
		
		return null;		
	}
	
	
	public Order getOrderById (int id) {
		
		Optional<Order> order = orderRepository.findById(id);
		
		if (order.isPresent()) {
			return order.get();
		}
		
		return null;		
	}
	
	//gets a row in order by order code
	public Order getOrderByOrder_code (int ordercode) {
		
		Optional<Order> order = Optional.ofNullable(orderRepository.findByOrder_code(ordercode));
		
		if (order.isPresent()) {
			return order.get();
		}
		
		return null;		
	}
	
	
	
	public void deleteProxy (int id) {
		
		proxylistRepo.deleteById(id);	
	}
	
	
	public void deleteSelected (int id) {

		selectedRepo.deleteById(id);
	}
	
	
	public void deleteOrder (int id) {
		
		orderRepository.deleteById(id);
	}
		
	
}
