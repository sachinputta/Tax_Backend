package com.example.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.CustomerRegistration;

public interface CustomerRegistrationRepository extends JpaRepository<CustomerRegistration, String> {

}
