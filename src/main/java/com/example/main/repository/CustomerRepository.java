package com.example.main.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.main.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

	Customer findByCustomerId(String customerId);

//	List<Customer> findByRoles_RoleName(String roleName);

    Set<Customer> findByRoles_RoleName(String roleName);

}
