package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.service.PDFService;

import net.sf.jasperreports.engine.JRException;


//generates pdf (BY JASPERREPORT) for order info for download on hitting print button in order page
@Controller
public class PDFController {

	
	@Autowired
	private PDFService pdfService;
	
	
	@GetMapping ("/pdf")
	public ResponseEntity<byte[]> generateReport () throws Exception, JRException {
		
		return pdfService.exportReport();
	}

	
	
	
}
