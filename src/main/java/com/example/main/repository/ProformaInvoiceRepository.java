package com.example.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.ProformaInvoice;

public interface ProformaInvoiceRepository extends JpaRepository<ProformaInvoice, String>{
	  List<ProformaInvoice> findByCustomerId(String customerId);
}
