//package com.example.main.service;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//
//import org.springframework.web.multipart.MultipartFile;
//
//
//import com.example.main.entity.Employee;
//
//
//import com.example.main.exception.InvalidAdminId;
//import com.example.main.exception.InvalidIdException;
//import com.example.main.exception.InvalidPassword;
//
//import jakarta.mail.MessagingException;
//
//public interface AdminService {
//
//	String initRoleAndAdmin();
//
//	public Employee addEmployee(Employee employee, String roleName) throws Exception;
//
//	public Employee getAdmin(String employeeId) throws InvalidAdminId;
//
//	public Employee updateAdmin(String employeeId, Employee employee) throws InvalidAdminId;
//
////	public Employee resetPassword(String employeeId,String currentPassword,String  newPassword) throws InvalidAdminId, InvalidPassword;
//	public List<Employee> getAllEmployee();
//
//	public List<Employee> getEmployeesNotAdmin();
//
//	public List<Employee> getEmployeesByRole(String roleName);
//
//
//	public int getEmployeesNoByRole(String roleName);
//
//	public int getTotalCourses();
//
//
//	public int getTotalTeams();
//
//
//
//	public byte[] getProfilePicture(String employeeId) throws IOException;
//
//	public void updatePhoto(String employeeId, MultipartFile photo) throws Exception;
//
//	public void uploadPhoto(String employeeId, MultipartFile file) throws Exception;
//
//	public Boolean checkEmail(String email);
//
//	public Boolean checkWebMail(String webMail);
//
//	public Boolean checkPhoneNumber(long phoneNumber);
//
//	List<String> getAllEmployeeEmails();
//
//	List<String> getAllWebMails();
//
//	List<Long> getAllPhoneNumbers();
//
//	Boolean checkEmailToUpdate(String employeeId, String email);
//
//	Boolean checkPhoneNumberToUpdate(String employeeId, long phoneNumber);
//
//
//
//	public String sendRequestReply(int ticketId, String employeeId, String replyMsg)
//			throws InvalidIdException, UnsupportedEncodingException, MessagingException;
//
//	
//
//	Employee addAdmin(String roleName, Employee admin);
//
//}

package com.example.main.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.main.entity.Customer;

import com.example.main.exception.InvalidAdminId;
import com.example.main.exception.InvalidIdException;

import jakarta.mail.MessagingException;

public interface AdminService {

	String initRoleAndAdmin();

//    Customer addCustomer(Customer customer, String roleName) throws Exception;

	Customer addAdmin(Customer admin);

	Customer addCustomer(Customer customer) throws Exception;

	Customer getAdmin(String customerId) throws InvalidAdminId;

	Customer updateAdmin(String customerId, Customer customer) throws InvalidAdminId;

	List<Customer> getAllCustomers();

	List<Customer> getCustomersNotAdmin();

	List<Customer> getCustomersByRole(String roleName);

	int getCustomersCountByRole(String roleName);

	int getTotalCourses();

	int getTotalTeams();

	byte[] getProfilePicture(String customerId) throws IOException;

	void updatePhoto(String customerId, MultipartFile photo) throws Exception;

	void uploadPhoto(String customerId, MultipartFile file) throws Exception;

	boolean checkEmail(String email);

	boolean checkWebMail(String webMail);

	boolean checkPhoneNumber(long phoneNumber);

	List<String> getAllCustomerEmails();

	List<String> getAllWebMails();

	List<Long> getAllPhoneNumbers();

	boolean checkEmailToUpdate(String customerId, String email);

	boolean checkPhoneNumberToUpdate(String customerId, long phoneNumber);

	String sendRequestReply(int ticketId, String customerId, String replyMsg)
			throws InvalidIdException, UnsupportedEncodingException, MessagingException;

//    Customer addAdmin(String roleName, Customer admin);

}
