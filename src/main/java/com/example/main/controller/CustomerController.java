package com.example.main.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.main.entity.Customer;
import com.example.main.entity.CustomerRegistration;
import com.example.main.entity.ItemsList;
import com.example.main.entity.PasswordChange;
import com.example.main.service.CustomerService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PreAuthorize("hasAnyRole('Customer')")
	@GetMapping("/getCustomerDetails/{customerId}")
	public ResponseEntity<Customer> getCustomerDetails(@PathVariable String customerId) throws Exception {
		Customer customerDetails = customerService.getCustomerDetails(customerId);
		return new ResponseEntity<>(customerDetails, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@PutMapping("/updateCustomerDetails/{customerId}")
	public ResponseEntity<Customer> updateCustomerDetails(@PathVariable String customerId,
			@RequestBody Customer customer) throws Exception {
		Customer updatedCustomer = customerService.updateCustomerDetails(customerId, customer);
		return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@PutMapping("/resetCustomerPassword/{customerId}/{currentPassword}/{newPassword}")
	public ResponseEntity<Customer> resetCustomerPassword(@PathVariable String customerId,
			@PathVariable String currentPassword, @PathVariable String newPassword) throws Exception {
		Customer resetCustomer = customerService.resetPassword(customerId, currentPassword, newPassword);
		return new ResponseEntity<>(resetCustomer, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@GetMapping("/getAllItems")
	public List<ItemsList> getAllItems() {
		return customerService.getAllItems();
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@GetMapping("/getItemById/{id}")
	public ItemsList getItemById(@PathVariable Long id) {
		return customerService.getItemById(id);
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@PostMapping("/createItem")
	public ItemsList createItem(@RequestBody ItemsList item) {
		return customerService.createItem(item);
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@PutMapping("/updateItem/{id}")
	public ItemsList updateItem(@PathVariable Long id, @RequestBody ItemsList item) {
		return customerService.updateItem(id, item);
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@DeleteMapping("/deleteItem/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
		customerService.deleteItem(id);
		return ResponseEntity.noContent().build(); // 204 No Content
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@PostMapping("/change-password")
	public ResponseEntity<Map<String, String>> changePassword(@RequestBody PasswordChange request) {
		String resultMessage = customerService.changePassword(request);
		// Wrap the message in a JSON object
		Map<String, String> response = new HashMap<>();
		response.put("message", resultMessage);

		return ResponseEntity.ok(response);
	}

//	@PreAuthorize("hasAnyRole('Customer')")
	@PostMapping("/get-profile-details")
	public ResponseEntity<Customer> getCustomer(@RequestBody Map<String, String> request) {
	    String customerId = request.get("customerId");
	    Customer customer = customerService.getCustomerById(customerId);
	    return ResponseEntity.ok(customer);
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@PutMapping("/update-profile/{customerId}")
	public Customer updateCustomer(@PathVariable String customerId, @RequestBody Customer customer) {
		return customerService.updateCustomer(customerId, customer);
	}
	

	@PreAuthorize("hasAnyRole('Customer')")
    @PostMapping("/customer-register")
    public CustomerRegistration registerCustomer(@RequestBody CustomerRegistration customer) {
        if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
            customer.setCustomerId(customer.getCustomerEmail());
        }
        return customerService.saveCustomer(customer);
    }
	
	@PreAuthorize("hasAnyRole('Customer')")
	@GetMapping("/get-All-Register-customers")
	public List<CustomerRegistration> getAllCustomerRegistrations() {
	    return customerService.getAllRegisterCustomers();
	}

    @PreAuthorize("hasAnyRole('Customer')")
    @DeleteMapping("/deleteRegisterCustomer/{id}")
    public ResponseEntity<Void> deleteRegisterCustomerById(@PathVariable String id) {
        customerService.deleteRegisterCustomerById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
