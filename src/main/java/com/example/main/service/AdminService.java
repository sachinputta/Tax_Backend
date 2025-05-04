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


import java.util.Set;


import com.example.main.entity.Customer;

import com.example.main.exception.InvalidAdminId;


public interface AdminService {

	String initRoleAndAdmin();

	Customer addAdmin(Customer admin);

	Customer addCustomer(Customer customer) throws Exception;

	Customer getAdmin(String customerId) throws InvalidAdminId;

	
	
	public Set<Customer> getCustomersWithRole(String roleName);

}
