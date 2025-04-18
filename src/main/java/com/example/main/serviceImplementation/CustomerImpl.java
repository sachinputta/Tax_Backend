package com.example.main.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.main.entity.Customer;
import com.example.main.entity.CustomerRegistration;
import com.example.main.entity.ItemsList;
import com.example.main.entity.PasswordChange;
import com.example.main.exception.ItemNotFoundException;
import com.example.main.repository.CustomerRegistrationRepository;
import com.example.main.repository.CustomerRepository;
import com.example.main.repository.ItemsListRepository;
import com.example.main.service.CustomerService;

@Service
public class CustomerImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ItemsListRepository itemsListRepository;

	@Autowired
	private CustomerRegistrationRepository customerRegistrationRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	public String changePassword(PasswordChange request) {
		Customer customer = customerRepository.findByCustomerId(request.getUsername());

		if (customer == null) {
			return "Username or Email not found.";
		}

		if (!passwordEncoder.matches(request.getCurrentPassword(), customer.getCustomerPassword())) {
			return "Current password is incorrect.";
		}

		if (!request.getNewPassword().equals(request.getConfirmPassword())) {
			return "New password and confirm password do not match.";
		}

		customer.setCustomerPassword(passwordEncoder.encode(request.getNewPassword()));
		customerRepository.save(customer);

		return "Password updated successfully.";
	}

	@Override
	public Customer getCustomerDetails(String customerId) throws Exception {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new Exception("This Customer ID " + customerId + " not found"));
	}

	@Override
	public Customer updateCustomerDetails(String customerId, Customer customer) throws Exception {
		Customer existingCustomer = customerRepository.findById(customerId)
				.orElseThrow(() -> new Exception("Customer ID " + customerId + " not found"));

		existingCustomer.setCompanyName(customer.getCompanyName());
		existingCustomer.setState(customer.getState());
		existingCustomer.setCountry(customer.getCountry());
		existingCustomer.setCustomerEmail(customer.getCustomerEmail());
		existingCustomer.setPhoneNumber(customer.getPhoneNumber());
		return customerRepository.save(existingCustomer);
	}

	@Override
	public Customer resetPassword(String customerId, String currentPassword, String newPassword) throws Exception {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new Exception("Customer ID not found"));

		if (passwordEncoder.matches(currentPassword, customer.getCustomerPassword())) {
			customer.setCustomerPassword(passwordEncoder.encode(newPassword));
			return customerRepository.save(customer);
		} else {
			throw new Exception("Current password is incorrect");
		}
	}

	@Override
	public Customer findById(String customerId) {
		return customerRepository.findById(customerId).orElse(null);
	}

	@Override
	public List<ItemsList> getAllItems() {
		return itemsListRepository.findAll();
	}

	@Override
	public ItemsList getItemById(Long id) {
		return itemsListRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("Item with ID " + id + " not found."));
	}

	@Override
	public ItemsList createItem(ItemsList item) {
		return itemsListRepository.save(item);
	}

	@Override
	public ItemsList updateItem(Long id, ItemsList updatedItem) {
		ItemsList existingItem = itemsListRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("Cannot update. Item with ID " + id + " not found."));

		existingItem.setName(updatedItem.getName());
		existingItem.setPurchaseDescription(updatedItem.getPurchaseDescription());
		existingItem.setPurchaseRate(updatedItem.getPurchaseRate());
		existingItem.setDescription(updatedItem.getDescription());
		existingItem.setRate(updatedItem.getRate());
		existingItem.setUsageUnit(updatedItem.getUsageUnit());

		return itemsListRepository.save(existingItem);
	}

	@Override
	public void deleteItem(Long id) {
		if (!itemsListRepository.existsById(id)) {
			throw new ItemNotFoundException("Cannot delete. Item with ID " + id + " not found.");
		}
		itemsListRepository.deleteById(id);
	}

	@Override
	public Customer getCustomerById(String customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
	}

	@Override
	public Customer updateCustomer(String customerId, Customer updatedCustomer) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (optionalCustomer.isPresent()) {
			Customer existingCustomer = optionalCustomer.get();

			existingCustomer.setCompanyName(updatedCustomer.getCompanyName());
			existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
			existingCustomer.setImagePath(updatedCustomer.getImagePath());
			existingCustomer.setState(updatedCustomer.getState());
			existingCustomer.setCountry(updatedCustomer.getCountry());

			return customerRepository.save(existingCustomer);
		} else {
			throw new RuntimeException("Customer not found with ID: " + customerId);
		}
	}

	@Override
	public CustomerRegistration saveCustomer(CustomerRegistration customer) {
		return customerRegistrationRepository.save(customer);
	}

	@Override
	public List<CustomerRegistration> getAllRegisterCustomers() {
		return customerRegistrationRepository.findAll();
	}

	@Override
	public void deleteRegisterCustomerById(String customerId) {
		customerRegistrationRepository.deleteById(customerId);
	}

}
