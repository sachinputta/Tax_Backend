package com.example.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.CustomerRegistration;

public interface CustomerRegistrationRepository extends JpaRepository<CustomerRegistration, Long> {
	 List<CustomerRegistration> findByCustomerId(String customerId);
	 
	 Optional<CustomerRegistration> findByCustomerEmail(String customerEmail);
}
