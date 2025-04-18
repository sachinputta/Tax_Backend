package com.example.main.serviceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.main.entity.Employee;

import com.example.main.repository.EmployeeRepository;

import com.example.main.service.EmployeeService;

@Service
public class EmployeeImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);



	@Override
	public Employee getEmployeeDetails(String employeeId) throws Exception {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new Exception(" This Employee ID " + employeeId + " not found"));
		return employee;
	}

	@Override
	public Employee updateEmployeeDetails(String employeeId, Employee employee) throws Exception {

		Employee existingEmployee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new Exception("Employee ID " + employeeId + " not found"));

		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setAddress(employee.getAddress());
		existingEmployee.setEmployeeEmail(employee.getEmployeeEmail());
		existingEmployee.setPhoneNumber(employee.getPhoneNumber());
		return employeeRepository.save(existingEmployee);
	}

	@Override
	public Employee resetPassword(String employeeId, String currentPassword, String newPassword) throws Exception {
		// Retrieve the employee based on employeeId
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new Exception("Employee Id not found"));

		// Check if the provided current password matches the stored encoded password
		if (passwordEncoder.matches(currentPassword, employee.getEmployeePassword())) {
			// If the password matches, encode the new password and save it
			employee.setEmployeePassword(passwordEncoder.encode(newPassword));
			employeeRepository.save(employee);
			return employee;
		} else {
			// If the current password does not match, throw an exception
			throw new Exception("Current password is incorrect");
		}
	}


	@Override
	public Employee findById(String employeeId) {
		return employeeRepository.findById(employeeId).orElse(null);
	}







	
}