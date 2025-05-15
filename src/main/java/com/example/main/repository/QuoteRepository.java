package com.example.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.Quote;

public interface QuoteRepository extends JpaRepository<Quote, String>  {
	   List<Quote> findByCustomerId(String customerId);


}
