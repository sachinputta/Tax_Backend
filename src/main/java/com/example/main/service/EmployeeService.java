package com.example.main.service;
import com.example.main.entity.Employee;



public interface EmployeeService {

	public Employee getEmployeeDetails(String employeeId) throws Exception;

	public Employee updateEmployeeDetails(String employeeId, Employee employee) throws Exception;

	public Employee resetPassword(String employeeId, String currentPassword, String newPassword) throws Exception;


	public Employee findById(String employeeId);

}
