package com.example.main.dto;

import lombok.Data;

@Data
public class CreateItemRequest {
	private String name;
	private String purchaseDescription;
	private Double purchaseRate;
	private String description;
	private Double rate;
	private String usageUnit;
	private String customerId;
}
