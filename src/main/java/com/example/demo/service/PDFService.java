package com.example.demo.service;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Service
public class PDFService {

	
	@Autowired
	private OrderRepository orderRepository;
	
	//generates pdf (BY JASPER) for order info for download on hitting print button in order page
	public ResponseEntity<byte[]> exportReport() throws Exception, JRException {
	
		List <Order> orderlist = orderRepository.findAll();
		
		JasperReport jasperReport = JasperCompileManager.compileReport ( new FileInputStream ("src/main/resources/IMS_receipt.jrxml") );
		
		JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource (orderlist);
		
		Map <String, Object> parameters = new HashMap<>(); 
		parameters.put("createdBy", "IMS");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport (jasperReport, parameters, datasource);
		
//		JasperExportManager.exportReportToPdfFile(jasperPrint, "IMS_OrderReceipt.pdf");
		
		byte [] data = JasperExportManager.exportReportToPdf (jasperPrint);
				
		HttpHeaders headers = new HttpHeaders();		
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename= IMS_receipt.pdf");
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}


}



