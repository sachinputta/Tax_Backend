//package com.example.main.serviceImplementation;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.example.main.entity.Employee;
//
//import com.example.main.entity.Role;
//
//
//import com.example.main.exception.InvalidAdminId;
//import com.example.main.exception.InvalidIdException;
//
//import com.example.main.repository.EmployeeRepository;
//
//import com.example.main.repository.RoleRepository;
//
//import com.example.main.service.AdminService;
//
//
//import jakarta.mail.MessagingException;
//import jakarta.transaction.Transactional;
//
//@Service
//public class AdminImpl implements AdminService {
//
//	@Autowired
//	private RoleRepository roleRepository;
//
//	@Autowired
//	private EmployeeRepository employeeRepository;
//
//
//	@SuppressWarnings("unused")
//	private static final int MAX_IMAGE_SIZE = 1024 * 1024; // Example: 1 MB
//	String uploadDir = "C:\\Users\\HP\\OneDrive\\Desktop\\Lms_Picture";
//
//	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
//
//	@Override
//	public String initRoleAndAdmin() {
//		Role adminRole = new Role();
//		adminRole.setRoleName("Admin");
//		roleRepository.save(adminRole);
//
//		Role customerRole = new Role();
//		customerRole.setRoleName("Customer");
//		roleRepository.save(customerRole);
//
//		return "Success";
//	}
//
//	@Override
//	public Employee addAdmin(String roleName, Employee admin) {
//		Role role = roleRepository.findById(roleName).get();
//		Set<Role> adminRole = new HashSet<>();
//		adminRole.add(role);
//		admin.setRoles(adminRole);
//		String encodedPassword = encoder.encode(admin.getEmployeePassword());
//		admin.setEmployeePassword(encodedPassword);
//		return employeeRepository.save(admin);
//	}
//
//	@Override
//	public Employee addEmployee(Employee employee, String roleName) throws Exception {
//		Role role = roleRepository.findById(roleName).get();
//		Set<Role> employeeRole = new HashSet<>();
//		employeeRole.add(role);
//		employee.setRoles(employeeRole);
//
//		// Store the original password before encoding
//		String originalPassword = employee.getEmployeePassword();
//
//		// Encode the password and set it to the employee object
//		String encodedPassword = encoder.encode(originalPassword);
//		employee.setEmployeePassword(encodedPassword);
//
//
//		// Save the employee with the encoded password
//		return employeeRepository.save(employee);
//	}
//
//	@Override
//	public Employee getAdmin(String employeeId) throws InvalidAdminId {
//
//		Optional<Employee> employee = employeeRepository.findById(employeeId);
//		if (employee.isEmpty()) {
//			throw new InvalidAdminId("give Valid AdminId");
//		} else {
//			return employee.get();
//		}
//
//	}
//
//	@Override
//	public Employee updateAdmin(String employeeId, Employee employee) throws InvalidAdminId {
//		Employee employee1 = employeeRepository.findById(employeeId)
//				.orElseThrow(() -> new InvalidAdminId("Give valid AdminId"));
//
//		if (employee.getAddress() != null) {
//			employee1.setAddress(employee.getAddress());
//		}
//		if (employee.getEmployeeEmail() != null) {
//			employee1.setEmployeeEmail(employee.getEmployeeEmail());
//		}
//		if (employee.getEmployeePassword() != null) {
//			String encodedPassword = encoder.encode(employee.getEmployeePassword());
//			employee1.setEmployeePassword(encodedPassword);
//		}
//		if (employee.getWebMail() != null) {
//			employee1.setWebMail(employee.getWebMail());
//		}
//		if (employee.getWebMailPassword() != null) {
//			employee1.setWebMailPassword(employee.getWebMailPassword());
//		}
//		if (employee.getFirstName() != null) {
//			employee1.setFirstName(employee.getFirstName());
//		}
//		if (employee.getLastName() != null) {
//			employee1.setLastName(employee.getLastName());
//		}
//		if (employee.getPhoneNumber() != 0) { // Assuming 0 is the default invalid value for phoneNumber
//			employee1.setPhoneNumber(employee.getPhoneNumber());
//		}
//
//		return employeeRepository.save(employee1);
//	}
//
//
//
//	@Override
//	public List<Employee> getAllEmployee() {
//
//		List<Employee> list = employeeRepository.findAll();
//
//		return list;
//	}
//
//	@Override
//	public List<Employee> getEmployeesByRole(String roleName) {
//
//		List<Employee> filteredEmployees = employeeRepository.findAll().stream()
//				.filter(emp -> emp.getRoles().stream().anyMatch(role -> role.getRoleName().equalsIgnoreCase(roleName)))
//				.collect(Collectors.toList());
//		Collections.sort(filteredEmployees, (e1, e2) -> e1.getFirstName().compareTo(e2.getFirstName()));
//		// sortEmployeesDescending(filteredEmployees);
//		return filteredEmployees;
//	}
//
//	@Override
//	public List<Employee> getEmployeesNotAdmin() {
//		List<String> roles = new ArrayList<String>();
//		roles.add("TeamLead");
//		roles.add("Developer");
//		roles.add("Tester");
//		List<Employee> list = employeeRepository.findEmployeesByRoles(roles);
//		// Collections.sort(list, (e1, e2) ->
//		// e1.getFirstName().compareTo(e2.getFirstName()));
//		// sortEmployeesDescending(list);
//		return list;
//	}
//
//	@Override
//	public int getEmployeesNoByRole(String roleName) {
//		List<Employee> filteredEmployees = employeeRepository.findAll().stream()
//				.filter(emp -> emp.getRoles().stream().anyMatch(role -> role.getRoleName().equalsIgnoreCase(roleName)))
//				.collect(Collectors.toList());
//		// sortEmployeesDescending(filteredEmployees);
//		return filteredEmployees.size();
//	}
//
//
//
//	public void sortEmployeesDescending(List<Employee> employees) {
//		Collections.sort(employees, Comparator.comparing(Employee::getDateOfJoin).reversed());
//	}
//
//
//
//	@Override
//	public byte[] getProfilePicture(String employeeId) throws IOException {
//		// Fetch the user entity by email
//		Employee employee = employeeRepository.findByEmployeeId(employeeId);
//		if (employee == null) {
//			throw new IllegalArgumentException("User with employeeId does not exist.");
//		}
//
//		// Get the image path from the user object
//		String imagePath = employee.getImagePath();
//		if (imagePath == null || imagePath.isEmpty()) {
//			throw new IllegalArgumentException("User with employeeId does not have a photo.");
//		}
//
//		// Read the photo bytes from the file
//		Path photoPath = Paths.get(imagePath);
//		return Files.readAllBytes(photoPath);
//	}
//
//	@Override
//	@Transactional
//	public void updatePhoto(String employeeId, MultipartFile photo) throws Exception {
//		// Fetch the user from the database
//		Employee employee = employeeRepository.findById(employeeId)
//				.orElseThrow(() -> new Exception("employee not found"));
//		if (employee != null) {
//
//			// Delete the old photo from the folder
//			deletePhotoFromFileSystem(employee.getImagePath());
//
//			// Save the new photo to the folder and update the user's photo path in the
//			// database
//
//			String imagePath = savePhotoToFileSystem(photo);
//			employee.setImagePath(imagePath);
//			employeeRepository.save(employee);
//		} else {
//			throw new IllegalArgumentException("User with employee does not exist.");
//		}
//
//	}
//
//	private void deletePhotoFromFileSystem(String imagePath) throws IOException {
//		if (imagePath != null) {
//			Path path = Paths.get(imagePath);
//			Files.deleteIfExists(path);
//		}
//	}
//
//	private String savePhotoToFileSystem(MultipartFile photo) throws IOException {
//		// Generate a unique filename for the new photo
//		String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
//		// Define the upload directory
//		// Create the directory if it doesn't exist
//		Path directoryPath = Paths.get(uploadDir);
//		Files.createDirectories(directoryPath);
//		// Save the photo to the upload directory
//		Path filePath = Paths.get(uploadDir, fileName);
//		Files.write(filePath, photo.getBytes());
//		return filePath.toString();
//	}
//
//	@Override
//	@Transactional
//	public void uploadPhoto(String employeeId, MultipartFile file) throws Exception {
//		// Generate a unique filename
//		String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//
//		if (file.isEmpty()) {
//			throw new IllegalArgumentException("File is empty");
//		}
//
//		// Save the image file to a local directory
//
//		Path directoryPath = Paths.get(uploadDir);
//		Files.createDirectories(directoryPath);
//
//		String filePath = Paths.get(uploadDir, uniqueFileName).toString();
//		Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
//
//		// Store the image path in the database
//		Employee employee = employeeRepository.findById(employeeId)
//				.orElseThrow(() -> new Exception("Employee not found"));
//		;
//		if (employee != null) {
//			employee.setImagePath(filePath);
//			employeeRepository.save(employee);
//		} else {
//			throw new IllegalArgumentException("User with employee id does not exist.");
//		}
//
//	}
//
//	@Override
//	public Boolean checkEmail(String email) {
//		boolean result = employeeRepository.existsByEmployeeEmail(email);
//		return result;
//	}
//
//	@Override
//	public Boolean checkWebMail(String webMail) {
//		boolean result = employeeRepository.existsByWebMail(webMail);
//		return result;
//	}
//
//	@Override
//	public Boolean checkPhoneNumber(long phoneNumber) {
//		boolean result = employeeRepository.existsByPhoneNumber(phoneNumber);
//		return result;
//	}
//
//	@Override
//	public List<String> getAllEmployeeEmails() {
//		return employeeRepository.findAllEmployeeEmails();
//	}
//
//	@Override
//	public List<String> getAllWebMails() {
//		return employeeRepository.findAllWebMails();
//	}
//
//	@Override
//	public List<Long> getAllPhoneNumbers() {
//		return employeeRepository.findAllPhoneNumbers();
//	}
//
//	@Override
//	public Boolean checkEmailToUpdate(String employeeId, String email) {
//		return employeeRepository.existsByEmailAndNotEmployeeId(email, employeeId);
//
//	}
//
//	@Override
//	public Boolean checkPhoneNumberToUpdate(String employeeId, long phoneNumber) {
//		return employeeRepository.existsByPhoneNumberAndNotEmployeeId(phoneNumber, employeeId);
//
//	}
//
//
//
//	@Override
//	public String sendRequestReply(int ticketId, String employeeId, String replyMsg)
//			throws InvalidIdException, UnsupportedEncodingException, MessagingException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//
//	@Override
//	public int getTotalTeams() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getTotalCourses() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//}

package com.example.main.serviceImplementation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.main.entity.Customer;
import com.example.main.entity.Role;
import com.example.main.exception.InvalidAdminId;
import com.example.main.exception.InvalidIdException;
import com.example.main.repository.CustomerRepository;
import com.example.main.repository.RoleRepository;
import com.example.main.service.AdminService;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class AdminImpl implements AdminService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CustomerRepository customerRepository;

	String uploadDir = "C:\\Users\\HP\\OneDrive\\Desktop\\Lms_Picture";

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Override
	public String initRoleAndAdmin() {
		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		roleRepository.save(adminRole);

		Role customerRole = new Role();
		customerRole.setRoleName("Customer");
		roleRepository.save(customerRole);

		return "Success";
	}



	@Override
	public Customer addAdmin(Customer admin) {
		Role role = roleRepository.findById("Admin").orElseThrow(() -> new RuntimeException("Role not found"));
		Set<Role> adminRole = new HashSet<>();
		adminRole.add(role);
		admin.setRoles(adminRole);

		String encodedPassword = encoder.encode(admin.getCustomerPassword());
		admin.setCustomerPassword(encodedPassword);

		return customerRepository.save(admin);
	}



	@Override
	public Customer addCustomer(Customer customer) throws Exception {
		// Fixed role as "Customer"
		Role role = roleRepository.findById("Customer")
				.orElseThrow(() -> new RuntimeException("Role 'Customer' not found"));

		Set<Role> customerRole = new HashSet<>();
		customerRole.add(role);
		customer.setRoles(customerRole);

		// Encrypt password
		String encodedPassword = encoder.encode(customer.getCustomerPassword());
		customer.setCustomerPassword(encodedPassword);

		// Save and return customer
		return customerRepository.save(customer);
	}

	@Override
	public Customer getAdmin(String customerId) throws InvalidAdminId {
		return customerRepository.findById(customerId).orElseThrow(() -> new InvalidAdminId("Give valid AdminId"));
	}

	@Override
	 public Set<Customer> getCustomersWithRole(String roleName) {
	        Set<Customer> customers = customerRepository.findByRoles_RoleName(roleName);
	        System.out.println("Customers with role " + roleName + ": " + customers);
	        return customers;
	    }
}
