package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.demo.entity.OrderProductSelected;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProxyProdList;
import com.example.demo.entity.Store;
import com.example.demo.repository.StoreRepository;

//functionalities same as brand
@Service
public class StoreService {

	
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private EmailSenderService service;
	
	private static List <String> reStockStore = new ArrayList();
	
	
	//Constructor
	public StoreService (StoreRepository storeRepository) {
		
		super();
		this.storeRepository = storeRepository;		
	}

	

	public List <Store> getAllStores() {
			
		return storeRepository.findAll();			
	}
	
	
	public void addStores(Store store) {
		
		store.setStore_remaining( store.getStore_stock() );
		
		storeRepository.save(store);		
	}
	
	
	
	public boolean checkStoreValidity (ProxyProdList proxy, Product prod) {
		
		List <Store> store = storeRepository.findAll();
		
		for (int i= 0; i < store.size(); i++) {
			
			if ( prod.getProd_store().equals( store.get(i).getStore_name() ) ) {
				
				if ( proxy.getProdselected_quantity() <= store.get(i).getStore_remaining() ) {					
					break;
				}
				else {					
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	public boolean UpdateStoreInStock (Product prod, OrderProductSelected selected) {
		
		List <Store> store;
		Store st;
		boolean stockOut = false;
		
		store = storeRepository.findByStorename( prod.getProd_store() );
		
		if (store.get(0) != null) {
			
			if (store.get(0).getStore_remaining() > 0) {
				
				store.get(0).setStore_sold( store.get(0).getStore_sold() + selected.getProdselected_quantity() );
				store.get(0).setStore_remaining( store.get(0).getStore_remaining() - selected.getProdselected_quantity() );
				storeRepository.save(store.get(0));
				
				st = getStoreById ( store.get(0).getStore_id() );
				
				stockOut = checkStoreStock (st);
			}
		}
		return stockOut;
	}
	
	
	public boolean checkStoreStock (Store st) {
		
		boolean isNull = false;
		
		if (st.getStore_remaining() == 0) {
			
			isNull = true;
			reStockStore.add ( st.getStore_email() );
		}
		return isNull;
	}
	
	
	public void sendMailToStore ( HttpSession session ) {
		
		for (int i = 0; reStockStore.size() != 0  &&  i < reStockStore.size(); i++) {
			
			if (reStockStore.get(i) != null) {

				triggerMailS (reStockStore.get(i));
//				System.out.println("-----------------------"+ reStockStore.get(i) + reStockStore.size());
				session.setAttribute("message", "Mail sent...");
			}
			
			else {
				session.setAttribute("message", "Could not find recipants. Something went wrong...");
				break;
			}
		}
			
			reStockStore.clear();
	}
	
	
	@EventListener
	public void triggerMailS (String sendingto) {
		
		service.sendEmail (sendingto, "Products Out of Stock...", "Respected Vendor,\nI would like to"
				+ "draw your attention to the fact that I am out of stock on your products,"
			   + "and am in need of restocking. Please take the necessary steps to send me a supply of the "
			   + "products as per our contractual agreement within 2 days of receiving this message.\n"
			   + "Regards,\n"
			   + "IMS"		);
		
	}
	
	
	
	
	
	public Store getStoreById (int id) {
		
		Optional<Store> store = storeRepository.findById(id);
		
		if (store.isPresent()) {
			return store.get();
		}
		
		return null;		
	}
	
	
	public void delete (int id) {
		
		storeRepository.deleteById(id);		
	}
	
	
	
}
