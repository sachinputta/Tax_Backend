package com.example.main.dto;

import lombok.Data;

@Data
public class InvoicePDFRequest {
	private String from;
	  private String toEmail;
	  private String subject;
	  private String text;
	  private String pdfBase64;
	  private String filename;
}
