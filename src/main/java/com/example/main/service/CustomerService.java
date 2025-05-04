package com.example.main.service;

import java.util.List;
import java.util.Optional;

import com.example.main.dto.CustomerRegistrationDto;
import com.example.main.entity.Customer;
import com.example.main.entity.CustomerRegistration;
import com.example.main.entity.ItemsList;
import com.example.main.entity.PasswordChange;
import com.example.main.entity.ProformaInvoice;
import com.example.main.entity.Quote;

public interface CustomerService {

	public Customer getCustomerDetails(String customerId) throws Exception;

	public Customer updateCustomerDetails(String customerId, Customer customer) throws Exception;

	public Customer resetPassword(String customerId, String currentPassword, String newPassword) throws Exception;

	public Customer findById(String customerId);

	List<ItemsList> getAllItems();

	public List<ItemsList> getItemsByCustomerId(String customerId);

	ItemsList createItem(ItemsList item);

	ItemsList updateItem(Long id, ItemsList updatedItem);

	void deleteItem(Long id);

	public String changePassword(PasswordChange request);

	Customer getCustomerById(String customerId);

	Customer updateCustomer(String customerId, Customer updatedCustomer);

	CustomerRegistration saveCustomerRegistration(CustomerRegistrationDto dto, String customerId);

	CustomerRegistration updateCustomerRegistration(Long registrationId, CustomerRegistrationDto dto);

	void deleteCustomerRegistration(Long registrationId);

	List<CustomerRegistration> getRegistrationsByCustomerId(String customerId);

	public Optional<CustomerRegistration> viewCustomerRegistrationById(Long registrationId);
	
	public Quote saveQuote(Quote quote);
	
	public List<Quote> getQuotesByCustomerId(String customerId);
	
	public ProformaInvoice saveProformaInvoice(ProformaInvoice invoice);
	
	public List<ProformaInvoice> getProformaInvoicesByCustomerId(String customerId);
}
