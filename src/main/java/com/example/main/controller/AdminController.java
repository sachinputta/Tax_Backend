
package com.example.main.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.main.entity.Customer;
import com.example.main.exception.InvalidAdminId;
import com.example.main.exception.InvalidIdException;
import com.example.main.service.AdminService;
import com.example.main.service.CustomerService;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class AdminController {

	@Autowired
	private AdminService adminService;


	@PostConstruct
	public void initRoleAndAdmin() {
		adminService.initRoleAndAdmin();
	}

	@PostMapping("/addAdmin")
	public ResponseEntity<Customer> addAdmin(@RequestBody Customer admin) {
		Customer savedAdmin = adminService.addAdmin(admin);
		return new ResponseEntity<>(savedAdmin, HttpStatus.OK);
	}

	@PostMapping("/addCustomer")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws Exception {
		Customer savedCustomer = adminService.addCustomer(customer);
		return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
	}

	
	
	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/role-customers")
	public Set<Customer> getCustomersWithRoleCustomer() {
		return adminService.getCustomersWithRole("Customer");
	}
}
