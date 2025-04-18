package com.example.main.security;



import com.example.main.entity.Customer;
import com.example.main.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	
//	private Employee employee;

	private String jwtToken;
	
//	private String employeeId;
	
	private Customer customer;
	
	private String customerId;
	
	

}
