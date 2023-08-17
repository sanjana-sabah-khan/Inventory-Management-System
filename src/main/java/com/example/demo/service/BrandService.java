package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Brand;
import com.example.demo.entity.OrderProductSelected;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProxyProdList;
import com.example.demo.repository.BrandRepository;


@Service
public class BrandService {

	
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private EmailSenderService service;
	
	private static List <String> reStockBrand = new ArrayList ();
	
	
	//Constructor
	public BrandService (BrandRepository brandRepository) {
		
		super();
		this.brandRepository = brandRepository;		
	}


	
	public List <Brand> getAllBrands() {
		
		return brandRepository.findAll();		
	}
	
	
	public Brand getBrandById (int id) {
		
		Optional<Brand> brand = brandRepository.findById(id);
		
		if (brand.isPresent()) {
			return brand.get();
		}
		
		return null;		
	}
	
	
	//checks if brand is valid
	public boolean checkBrandValidity (ProxyProdList proxy, Product prod) {
		
		List <Brand> brand = brandRepository.findAll();
		
		for (int j = 0; j < brand.size(); j++) {
			
			if ( prod.getProd_brand().equals( brand.get(j).getBrand_name() ) ) {
				
				if ( proxy.getProdselected_quantity() <= brand.get(j).getBrand_remaining() ) {					
					break;
				}
				else {					
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	public boolean UpdateBrandInStock (Product prod, OrderProductSelected selected) {
		
		List <Brand> brand;
		Brand b;
		boolean stockOut = false;
				
		brand = brandRepository.findByBrandname( prod.getProd_brand() );
		
		if (brand.get(0) != null) {
			
			if (brand.get(0).getBrand_remaining() > 0) {
				
				brand.get(0).setBrand_sold( brand.get(0).getBrand_sold() + selected.getProdselected_quantity() );
				brand.get(0).setBrand_remaining( brand.get(0).getBrand_remaining() - selected.getProdselected_quantity() );
				
				brandRepository.save(brand.get(0));
				
				b = getBrandById ( brand.get(0).getBrand_id() );
				
				stockOut = checkBrandStock (b);
			}
		}
		return stockOut;
	}
	
	//checks if brand stock is empty
	public boolean checkBrandStock (Brand b) {
	
		boolean isNull = false;
		
		if (b.getBrand_remaining() == 0) {
			
			isNull = true;
			reStockBrand.add ( b.getBrand_email() );
		}
		return isNull;
	}
	
	//sends mail to out of stock brand vendors
	public void sendMailToBrand ( HttpSession session ) {
		
		for (int i = 0; reStockBrand.size() != 0  &&  i < reStockBrand.size(); i++) {
			
			if (reStockBrand.get(i) != null) {

				triggerMailB (reStockBrand.get(i));
//				System.out.println("-----------------------"+ reStockBrand.get(i) + reStockBrand.size());
				session.setAttribute("message", "Mail sent...");
			}
			
			else {
				session.setAttribute("message", "Could not find recipants. Something went wrong...");
				break;
			}
		}
			
			reStockBrand.clear();

	}
	
	//what the email consists of
	@EventListener
	public void triggerMailB (String sendingto) {
		
		service.sendEmail (sendingto, "Products Out of Stock...", "Respected Vendor,\nI would like to"
				+ "draw your attention to the fact that I am out of stock on your products,"
			   + "and am in need of restocking. Please take the necessary steps to send me a supply of the "
			   + "products as per our contractual agreement within 2 days of receiving this message.\n"
			   + "Regards,\n"
			   + "IMS"		);
		
	}
	
		
			
	public void addBrands(Brand brand) {
		
		brand.setBrand_remaining( brand.getBrand_stock() );
		brandRepository.save(brand);		
	}
		
	
	public void delete (int id) {
		
		brandRepository.deleteById(id);		
	}

	
}
