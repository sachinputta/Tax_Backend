package com.example.main.controller;

import java.time.LocalDateTime;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.main.entity.Employee;



import com.example.main.service.EmployeeService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@PreAuthorize("hasAnyRole('Developer', 'Tester', 'TeamLead','LMS Admin','BDM')")

	@GetMapping("/getEmployeeDetails/{employeeId}")
	public ResponseEntity<Employee> getEmployeeDetails(@PathVariable String employeeId) throws Exception {

		Employee employeeDetails = employeeService.getEmployeeDetails(employeeId);
		return new ResponseEntity<Employee>(employeeDetails, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Developer', 'Tester', 'TeamLead','LMS Admin','BDM')")
	@PutMapping("/updateEmployeeDetails/{employeeId}")
	public ResponseEntity<Employee> updateEmployeeDetails(@PathVariable String employeeId,
			@RequestBody Employee employee) throws Exception {
		Employee updateEmployeeDetails = employeeService.updateEmployeeDetails(employeeId, employee);
		return new ResponseEntity<Employee>(updateEmployeeDetails, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Developer', 'Tester','TeamLead','LMS Admin','BDM')")
	@PutMapping("/resetPassword/{employeeId}/{currentPassword}/{newPassword}")
	public ResponseEntity<Employee> resetPassword(@PathVariable String employeeId, @PathVariable String currentPassword,
			@PathVariable String newPassword) throws Exception {

		Employee resetPassword = employeeService.resetPassword(employeeId, currentPassword, newPassword);
		return new ResponseEntity<Employee>(resetPassword, HttpStatus.OK);
	}



	class StartSessionRequest {
		private LocalDateTime startTime;
		private int sessionNumber;

		// Getters and setters
		public LocalDateTime getStartTime() {
			return startTime;
		}

		public void setStartTime(LocalDateTime startTime) {
			this.startTime = startTime;
		}

		public int getSessionNumber() {
			return sessionNumber;
		}

		public void setSessionNumber(int sessionNumber) {
			this.sessionNumber = sessionNumber;
		}
	}

	

}
