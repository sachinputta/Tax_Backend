package com.example.main.serviceImplementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.main.dto.CustomerRegistrationDto;
import com.example.main.entity.Customer;
import com.example.main.entity.CustomerRegistration;
import com.example.main.entity.Item;
import com.example.main.entity.ItemsList;
import com.example.main.entity.PasswordChange;
import com.example.main.entity.ProformaInvoice;
import com.example.main.entity.ProformaItem;
import com.example.main.entity.Quote;
import com.example.main.exception.ItemNotFoundException;
import com.example.main.repository.CustomerRegistrationRepository;
import com.example.main.repository.CustomerRepository;
import com.example.main.repository.ItemsListRepository;
import com.example.main.repository.ProformaInvoiceRepository;
import com.example.main.repository.QuoteRepository;
import com.example.main.service.CustomerService;

@Service
public class CustomerImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ItemsListRepository itemsListRepository;

	@Autowired
	private QuoteRepository quoteRepository;
	

	@Autowired
	private ProformaInvoiceRepository proformaInvoiceRepository;


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
	public List<ItemsList> getItemsByCustomerId(String customerId) {
		// Fetch the customer by customerId
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		// Fetch the list of items based on the customer
		return itemsListRepository.findByCustomer(customer);
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
			existingCustomer.setPan(updatedCustomer.getPan());
			existingCustomer.setGstin(updatedCustomer.getGstin());
			existingCustomer.setCin(updatedCustomer.getCin());
			existingCustomer.setState(updatedCustomer.getState());
			existingCustomer.setCountry(updatedCustomer.getCountry());

			return customerRepository.save(existingCustomer);
		} else {
			throw new RuntimeException("Customer not found with ID: " + customerId);
		}
	}

	@Override
	public CustomerRegistration saveCustomerRegistration(CustomerRegistrationDto dto, String customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

		CustomerRegistration registration = mapDtoToEntity(dto);
		registration.setCustomerId(customerId);
		return customerRegistrationRepository.save(registration);
	}

	@Override
	public CustomerRegistration updateCustomerRegistration(Long registrationId, CustomerRegistrationDto dto) {
		CustomerRegistration existingRegistration = customerRegistrationRepository.findById(registrationId)
				.orElseThrow(() -> new RuntimeException("Registration not found with ID: " + registrationId));

		// Update fields
		updateEntityFromDto(existingRegistration, dto);
		return customerRegistrationRepository.save(existingRegistration);
	}

	@Override
	public void deleteCustomerRegistration(Long registrationId) {
		if (!customerRegistrationRepository.existsById(registrationId)) {
			throw new RuntimeException("Registration not found with ID: " + registrationId);
		}
		customerRegistrationRepository.deleteById(registrationId);
	}

	// Utility Methods

	private CustomerRegistration mapDtoToEntity(CustomerRegistrationDto dto) {
		CustomerRegistration registration = new CustomerRegistration();

		registration.setCustomerType(dto.getCustomerType());
		registration.setSalutation(dto.getSalutation());
		registration.setFirstName(dto.getFirstName());
		registration.setLastName(dto.getLastName());
		registration.setCompanyName(dto.getCompanyName());
		registration.setCustomerEmail(dto.getCustomerEmail());
//        registration.setCustomerPassword(dto.getCustomerPassword());
		registration.setPhoneNumber(dto.getPhoneNumber());
		registration.setMobileNumber(dto.getMobileNumber());

		registration.setBillingStreet(dto.getBillingStreet());
		registration.setBillingCity(dto.getBillingCity());
		registration.setBillingState(dto.getBillingState());
		registration.setBillingCountry(dto.getBillingCountry());
		registration.setBillingZip(dto.getBillingZip());

		registration.setShippingStreet(dto.getShippingStreet());
		registration.setShippingCity(dto.getShippingCity());
		registration.setShippingState(dto.getShippingState());
		registration.setShippingCountry(dto.getShippingCountry());
		registration.setShippingZip(dto.getShippingZip());

		registration.setTaxId(dto.getTaxId());
		registration.setPan(dto.getPan());
		registration.setSupplyState(dto.getSupplyState());
		registration.setCurrency(dto.getCurrency());

		registration.setPaymentTerms(dto.getPaymentTerms());
		registration.setCreditLimit(dto.getCreditLimit());
		registration.setBankName(dto.getBankName());
		registration.setAccountNumber(dto.getAccountNumber());
		registration.setIfscCode(dto.getIfscCode());
		registration.setPreferredPaymentMethod(dto.getPreferredPaymentMethod());

		registration.setWebsiteUrl(dto.getWebsiteUrl());
		registration.setContactPersonName(dto.getContactPersonName());
		registration.setContactPersonEmail(dto.getContactPersonEmail());
		registration.setContactPersonPhone(dto.getContactPersonPhone());
		registration.setNotes(dto.getNotes());

		return registration;
	}

	private void updateEntityFromDto(CustomerRegistration entity, CustomerRegistrationDto dto) {
		entity.setCustomerType(dto.getCustomerType());
		entity.setSalutation(dto.getSalutation());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setCompanyName(dto.getCompanyName());
		entity.setCustomerEmail(dto.getCustomerEmail());
//        entity.setCustomerPassword(dto.getCustomerPassword());
		entity.setPhoneNumber(dto.getPhoneNumber());
		entity.setMobileNumber(dto.getMobileNumber());

		entity.setBillingStreet(dto.getBillingStreet());
		entity.setBillingCity(dto.getBillingCity());
		entity.setBillingState(dto.getBillingState());
		entity.setBillingCountry(dto.getBillingCountry());
		entity.setBillingZip(dto.getBillingZip());

		entity.setShippingStreet(dto.getShippingStreet());
		entity.setShippingCity(dto.getShippingCity());
		entity.setShippingState(dto.getShippingState());
		entity.setShippingCountry(dto.getShippingCountry());
		entity.setShippingZip(dto.getShippingZip());

		entity.setTaxId(dto.getTaxId());
		entity.setPan(dto.getPan());
		entity.setSupplyState(dto.getSupplyState());
		entity.setCurrency(dto.getCurrency());

		entity.setPaymentTerms(dto.getPaymentTerms());
		entity.setCreditLimit(dto.getCreditLimit());
		entity.setBankName(dto.getBankName());
		entity.setAccountNumber(dto.getAccountNumber());
		entity.setIfscCode(dto.getIfscCode());
		entity.setPreferredPaymentMethod(dto.getPreferredPaymentMethod());

		entity.setWebsiteUrl(dto.getWebsiteUrl());
		entity.setContactPersonName(dto.getContactPersonName());
		entity.setContactPersonEmail(dto.getContactPersonEmail());
		entity.setContactPersonPhone(dto.getContactPersonPhone());
		entity.setNotes(dto.getNotes());
	}

	@Override
	public List<CustomerRegistration> getRegistrationsByCustomerId(String customerId) {
		return customerRegistrationRepository.findByCustomerId(customerId);
	}

	@Override
	public Optional<CustomerRegistration> viewCustomerRegistrationById(Long registrationId) {
		return customerRegistrationRepository.findById(registrationId);
	}


	@Override
	public Quote saveQuote(Quote quote) {
	    // Fetch customer by email from the Quote
	    String customerEmail = quote.getCustomerEmail();

	    // Fetch customer entity
	    CustomerRegistration customer = customerRegistrationRepository.findByCustomerEmail(customerEmail)
	            .orElseThrow(() -> new RuntimeException("Customer with email " + customerEmail + " not found"));

	    // Set customer info
	    quote.setCustomer(customer);
	    quote.setCustomerId(customer.getCustomerId());

	    // Set today's date
	    quote.setDate(LocalDate.now());

	    // Generate quoteCode
	    long count = quoteRepository.count(); 
	    String quoteCode = String.format("QT-%05d", count + 1);
	    quote.setQuoteCode(quoteCode);

	    // Associate each item with the quote
	    for (Item item : quote.getItems()) {
	        item.setQuote(quote);
	    }

	    return quoteRepository.save(quote);
	}


	
	@Override
	public List<Quote> getQuotesByCustomerId(String customerId) {
	    return quoteRepository.findByCustomerId(customerId);
	}
	
	
	@Override
	public ProformaInvoice saveProformaInvoice(ProformaInvoice invoice) {
	    // 1. Lookup customer by email
	    String customerEmail = invoice.getCustomerEmail();
	    CustomerRegistration customer = customerRegistrationRepository.findByCustomerEmail(customerEmail)
	            .orElseThrow(() -> new RuntimeException("Customer with email " + customerEmail + " not found"));

	    // 2. Set customer reference and ID
	    invoice.setCustomer(customer);
	    invoice.setCustomerId(customer.getCustomerId());

	    // 3. Set current date
	    invoice.setDate(LocalDate.now());

	    // 4. Generate proformaCode like PI-00001
	    long count = proformaInvoiceRepository.count();
	    String proformaCode = String.format("PI-%05d", count + 1);
	    invoice.setProformaCode(proformaCode);

	    // 5. Set up each item and associate with invoice
	    List<ProformaItem> items = invoice.getItems();
	    for (int i = 0; i < items.size(); i++) {
	        ProformaItem item = items.get(i);
	        item.setProformaInvoice(invoice);

	        // Generate unique piId like PI-00001-01
	        item.setPiId(proformaCode + "-" + String.format("%02d", i + 1));

	        // Calculate netCharges
	        if (item.getCharges() == null) item.setCharges(0.0);
	        if (item.getDiscount() == null) item.setDiscount(0.0);
	        item.setNetCharges(item.getCharges() - item.getDiscount());
	    }

	    // 6. Save to DB
	    return proformaInvoiceRepository.save(invoice);
	}
	
	
	@Override
	public List<ProformaInvoice> getProformaInvoicesByCustomerId(String customerId) {
	    return proformaInvoiceRepository.findByCustomerId(customerId);
	}


}
