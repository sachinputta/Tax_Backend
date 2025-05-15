package com.example.main.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.main.dto.CreateItemRequest;
import com.example.main.dto.CustomerRegistrationDto;
import com.example.main.dto.InvoicePDFRequest;
import com.example.main.entity.Customer;
import com.example.main.entity.CustomerRegistration;
import com.example.main.entity.ItemsList;
import com.example.main.entity.PasswordChange;
import com.example.main.entity.ProformaInvoice;
import com.example.main.entity.Quote;
import com.example.main.entity.pdfRequest;
import com.example.main.repository.CustomerRepository;
import com.example.main.repository.ProformaInvoiceRepository;
import com.example.main.repository.QuoteRepository;
import com.example.main.service.CustomerService;
import com.example.main.service.EmailService;
import com.example.main.util.ProformaInvoicePdfGenerator;
import com.example.main.util.QuotePdfGenerator;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private EmailService emailService;

	@PreAuthorize("hasAnyRole('Customer')")
	@PostMapping("/invoice/send/{quoteCode}")
	public String sendInvoiceEmail(@PathVariable String quoteCode) {
		Quote quote = quoteRepository.findById(quoteCode).orElse(null);

		if (quote == null) {
			return "Quote not found!";
		}

		byte[] pdfBytes = QuotePdfGenerator.generateQuotePdf(quote);

		String senderName = quote.getCustomer().getCompanyName();
		// Email message content
		String messageText = "Dear " + quote.getCustomerName() + ",\n\n" + "Please find attached your quote.\n\n"
				+ "Regards,\n" + senderName;

		try {
			emailService.sendQuoteEmailWithAttachment(quote.getCustomerId(), // From: customerId (e.g., tcs@gmail.com)
					quote.getCustomerEmail(), // To: customerEmail
					"Quotation - " + quoteCode, messageText, pdfBytes, "Quote_" + quoteCode + ".pdf");
			return "Quote emailed successfully.";
		} catch (MessagingException e) {
			return "Failed to send email.";
		}
	}

	
	@PreAuthorize("hasAnyRole('Customer')")
	@PostMapping("/quotes/send/{quoteCode}")
	public String sendQuoteEmail(@PathVariable String quoteCode) {
		Quote quote = quoteRepository.findById(quoteCode).orElse(null);

		if (quote == null) {
			return "Quote not found!";
		}

		byte[] pdfBytes = QuotePdfGenerator.generateQuotePdf(quote);

		String senderName = quote.getCustomer().getCompanyName();
		// Email message content
		String messageText = "Dear " + quote.getCustomerName() + ",\n\n" + "Please find attached your quote.\n\n"
				+ "Regards,\n" + senderName;

		try {
			emailService.sendQuoteEmailWithAttachment(quote.getCustomerId(), // From: customerId (e.g., tcs@gmail.com)
					quote.getCustomerEmail(), // To: customerEmail
					"Quotation - " + quoteCode, messageText, pdfBytes, "Quote_" + quoteCode + ".pdf");
			return "Quote emailed successfully.";
		} catch (MessagingException e) {
			return "Failed to send email.";
		}
	}

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
	@GetMapping("/getItemsByCustomer/{customerId}")
	public List<ItemsList> getItemsByCustomerId(@PathVariable String customerId) {
		return customerService.getItemsByCustomerId(customerId);
	}

	@PostMapping("/createItem")
	public ItemsList createItem(@RequestBody CreateItemRequest request) {
		Customer customer = customerRepository.findById(request.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		ItemsList item = new ItemsList();
		item.setName(request.getName());
		item.setPurchaseDescription(request.getPurchaseDescription());
		item.setPurchaseRate(request.getPurchaseRate());
		item.setDescription(request.getDescription());
		item.setRate(request.getRate());
		item.setUsageUnit(request.getUsageUnit());
		item.setCustomer(customer);

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

	@PreAuthorize("hasAnyRole('Customer', 'Admin')")
	@PostMapping("/change-password")
	public ResponseEntity<Map<String, String>> changePassword(@RequestBody PasswordChange request) {
		String resultMessage = customerService.changePassword(request);
		// Wrap the message in a JSON object
		Map<String, String> response = new HashMap<>();
		response.put("message", resultMessage);

		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAnyRole('Customer', 'Admin')")
	@PostMapping("/get-profile-details")
	public ResponseEntity<Customer> getCustomer(@RequestBody Map<String, String> request) {
		String customerId = request.get("customerId");
		Customer customer = customerService.getCustomerById(customerId);
		return ResponseEntity.ok(customer);
	}

	@PreAuthorize("hasAnyRole('Customer', 'Admin')")
	@PutMapping("/update-profile/{customerId}")
	public Customer updateCustomer(@PathVariable String customerId, @RequestBody Customer customer) {
		return customerService.updateCustomer(customerId, customer);
	}

	// Create Register
	@PreAuthorize("hasAnyRole('Customer')")
	@PostMapping("/registerCustomer/{customerId}")
	public CustomerRegistration registerCustomer(@PathVariable String customerId,
			@RequestBody CustomerRegistrationDto registrationDto) {

		return customerService.saveCustomerRegistration(registrationDto, customerId);
	}

	// Update Registered Customer
	@PreAuthorize("hasAnyRole('Customer')")
	@PutMapping("/updateRegisterCustomer/{registrationId}")
	public CustomerRegistration updateRegisteredCustomer(@PathVariable Long registrationId,
			@RequestBody CustomerRegistrationDto registrationDto) {

		return customerService.updateCustomerRegistration(registrationId, registrationDto);
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@DeleteMapping("/deleteRegisterCustomer/{registrationId}")
	public ResponseEntity<Map<String, String>> deleteRegisteredCustomer(@PathVariable Long registrationId) {
		customerService.deleteCustomerRegistration(registrationId);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Customer Registration with ID " + registrationId + " deleted successfully!");
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAnyRole('Customer', 'Admin')")
	@GetMapping("/getCustomerRegistration/{customerId}")
	public List<CustomerRegistration> getRegistrationsByCustomerId(@PathVariable String customerId) {
		return customerService.getRegistrationsByCustomerId(customerId);
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@GetMapping("/viewRegisterCustomer/{registrationId}")
	public ResponseEntity<CustomerRegistration> getCustomerRegistration(@PathVariable Long registrationId) {
		Optional<CustomerRegistration> customerRegistration = customerService
				.viewCustomerRegistrationById(registrationId);

		if (customerRegistration.isPresent()) {
			return ResponseEntity.ok(customerRegistration.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@PostMapping("/createQuote")
	public ResponseEntity<Quote> createQuote(@RequestBody Quote quote) {
		try {
			Quote savedQuote = customerService.saveQuote(quote);
			return new ResponseEntity<>(savedQuote, HttpStatus.CREATED); // Return full saved object
		} catch (RuntimeException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@GetMapping("/quotes/customer/{customerId}")
	public ResponseEntity<List<Quote>> getQuotesByCustomerId(@PathVariable String customerId) {
		List<Quote> quotes = customerService.getQuotesByCustomerId(customerId);

		if (quotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(quotes);
		}
		return ResponseEntity.ok(quotes);
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@PostMapping("/proforma-Invoice/create")
	public ResponseEntity<ProformaInvoice> createProformaInvoice(@RequestBody ProformaInvoice invoice) {
		ProformaInvoice savedInvoice = customerService.saveProformaInvoice(invoice);
		return ResponseEntity.ok(savedInvoice);
	}

	@PreAuthorize("hasAnyRole('Customer')")
	@GetMapping("/performa-invoice/customer/{customerId}")
	public ResponseEntity<List<ProformaInvoice>> getInvoicesByCustomerId(@PathVariable String customerId) {
		List<ProformaInvoice> invoices = customerService.getProformaInvoicesByCustomerId(customerId);
		return ResponseEntity.ok(invoices);
	}

}
