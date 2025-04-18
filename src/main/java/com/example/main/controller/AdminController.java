
package com.example.main.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.main.entity.Customer;
import com.example.main.exception.InvalidAdminId;
import com.example.main.exception.InvalidIdException;
import com.example.main.service.AdminService;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostConstruct
	public void initRoleAndAdmin() {
		adminService.initRoleAndAdmin();
	}

//	@PostMapping("/addAdmin")
//	public ResponseEntity<Customer> addAdmin(@RequestParam String roleName, @RequestBody Customer admin) {
//		Customer addAdmin = adminService.addAdmin(roleName, admin);
//		return new ResponseEntity<>(addAdmin, HttpStatus.OK);
//	}

	@PostMapping("/addAdmin")
	public ResponseEntity<Customer> addAdmin(@RequestBody Customer admin) {
		Customer savedAdmin = adminService.addAdmin(admin);
		return new ResponseEntity<>(savedAdmin, HttpStatus.OK);
	}

//	@PostMapping("/addCustomer")
//	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer, @RequestParam String roleName)
//			throws Exception {
//		Customer addCustomer = adminService.addCustomer(customer, roleName);
//		return new ResponseEntity<>(addCustomer, HttpStatus.OK);
//	}

	@PostMapping("/addCustomer")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws Exception {
		Customer savedCustomer = adminService.addCustomer(customer);
		return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/checkEmail/{email}")
	public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
		return ResponseEntity.ok(adminService.checkEmail(email));
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/checkWebMail/{webMail}")
	public ResponseEntity<Boolean> checkWebMail(@PathVariable String webMail) {
		return ResponseEntity.ok(adminService.checkWebMail(webMail));
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/checkPhoneNumber/{phoneNumber}")
	public ResponseEntity<Boolean> checkPhoneNumber(@PathVariable long phoneNumber) {
		return ResponseEntity.ok(adminService.checkPhoneNumber(phoneNumber));
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/checkEmailToUpdate/{customerId}/{email}")
	public ResponseEntity<Boolean> checkEmailToUpdate(@PathVariable String customerId, @PathVariable String email) {
		return ResponseEntity.ok(adminService.checkEmailToUpdate(customerId, email));
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/checkPhoneNumberToUpdate/{customerId}/{phoneNumber}")
	public ResponseEntity<Boolean> checkPhoneNumberToUpdate(@PathVariable String customerId,
			@PathVariable long phoneNumber) {
		return ResponseEntity.ok(adminService.checkPhoneNumberToUpdate(customerId, phoneNumber));
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/AllEmails")
	public List<String> getAllCustomerEmails() {
		return adminService.getAllCustomerEmails();
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/AllWebMails")
	public List<String> getAllWebMails() {
		return adminService.getAllWebMails();
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/AllPhoneNumbers")
	public List<Long> getAllPhoneNumbers() {
		return adminService.getAllPhoneNumbers();
	}

	@PreAuthorize("hasAnyRole('LMS Admin','RMS Admin')")
	@GetMapping("/getAdmin/{customerId}")
	public ResponseEntity<Customer> getAdmin(@PathVariable String customerId) throws InvalidAdminId {
		Customer admin = adminService.getAdmin(customerId);
		return new ResponseEntity<>(admin, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@PutMapping("/updateAdmin/{customerId}")
	public ResponseEntity<Customer> updateAdmin(@PathVariable String customerId, @RequestBody Customer customer)
			throws InvalidAdminId {
		Customer admin = adminService.updateAdmin(customerId, customer);
		return new ResponseEntity<>(admin, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('LMS Admin','RMS Admin')")
	@GetMapping("/getCustomerList")
	public ResponseEntity<List<Customer>> getAllCustomers() throws InvalidAdminId {
		List<Customer> customers = adminService.getAllCustomers();
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/customers/byRole")
	public ResponseEntity<List<Customer>> getCustomersByRole(@RequestParam String roleName) {
		return ResponseEntity.ok(adminService.getCustomersByRole(roleName));
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/customers/notAdmin")
	public ResponseEntity<List<Customer>> getCustomersNotAdmin() {
		return ResponseEntity.ok(adminService.getCustomersNotAdmin());
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/getTotalCourses")
	public ResponseEntity<Integer> getTotalCourses() {
		return ResponseEntity.ok(adminService.getTotalCourses());
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/customersNumber/byRole/{roleName}")
	public ResponseEntity<Integer> getCustomersNoByRole(@PathVariable String roleName) {
		return ResponseEntity.ok(adminService.getCustomersCountByRole(roleName));
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/getTotalTeams")
	public ResponseEntity<Integer> getTotalTeams() {
		return ResponseEntity.ok(adminService.getTotalTeams());
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@PostMapping("/uploadPhotoAdmin/{customerId}")
	public ResponseEntity<String> uploadPhoto(@PathVariable String customerId, @RequestParam("file") MultipartFile file)
			throws Exception {
		try {
			adminService.uploadPhoto(customerId, file);
			return ResponseEntity.ok("Photo uploaded successfully for user with customerId " + customerId);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error uploading photo: " + e.getMessage());
		}
	}

	@PreAuthorize("hasRole('LMS Admin')")
	@GetMapping("/getPhotoAdmin/{customerId}")
	public ResponseEntity<ByteArrayResource> getPhoto(@PathVariable String customerId) {
		try {
			byte[] photoBytes = adminService.getProfilePicture(customerId);
			ByteArrayResource resource = new ByteArrayResource(photoBytes);
			return ResponseEntity.ok().header("Content-Type", "image/jpeg").body(resource);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
