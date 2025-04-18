package com.example.main.service;

import java.util.List;

import com.example.main.entity.Customer;
import com.example.main.entity.CustomerRegistration;
import com.example.main.entity.ItemsList;
import com.example.main.entity.PasswordChange;

public interface CustomerService {

	public Customer getCustomerDetails(String customerId) throws Exception;

	public Customer updateCustomerDetails(String customerId, Customer customer) throws Exception;

	public Customer resetPassword(String customerId, String currentPassword, String newPassword) throws Exception;

	public Customer findById(String customerId);

	List<ItemsList> getAllItems();

	ItemsList getItemById(Long id);

	ItemsList createItem(ItemsList item);

	ItemsList updateItem(Long id, ItemsList updatedItem);

	void deleteItem(Long id);

	public String changePassword(PasswordChange request);

	Customer getCustomerById(String customerId);

	Customer updateCustomer(String customerId, Customer updatedCustomer);

	CustomerRegistration saveCustomer(CustomerRegistration customer);
	
	public List<CustomerRegistration> getAllRegisterCustomers();
	
	public void deleteRegisterCustomerById(String customerId);

}
