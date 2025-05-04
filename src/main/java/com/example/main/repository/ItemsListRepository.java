package com.example.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.Customer;
import com.example.main.entity.ItemsList;

public interface ItemsListRepository extends JpaRepository<ItemsList, Long> {
	 List<ItemsList> findByCustomerCustomerId(String customerId);
	  List<ItemsList> findByCustomer(Customer customer);
}
