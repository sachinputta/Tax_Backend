//package com.example.main.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.example.main.entity.Employee;
//import com.example.main.repository.EmployeeRepository;
//
//@Service
//public class JwtServiceImpl implements UserDetailsService {
//
//	@Autowired
//	private EmployeeRepository employeeRepository;
//
//	@Autowired
//	@Lazy
//	private AuthenticationManager authenticationManager;
//
//	@Autowired
//	private JwtService jwtService;
//
//	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
//		String email = jwtRequest.getEmployeeId();
//		String password = jwtRequest.getEmployeePassword();
//		authenticate(email, password);
//
//		loadUserByUsername(email);
//		Employee employee = employeeRepository.findByEmployeeId(email);
//		String generatedToken = jwtService.generateToken(employee.getEmployeeId());
//		String employeeId = employee.getEmployeeId();
//		return new JwtResponse(employee, generatedToken, employeeId);
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Employee user = employeeRepository.findByEmployeeId(email);
//		if (user == null) {
//			System.out.println("User Not Found");
//			throw new UsernameNotFoundException("user not found");
//		}
//
//		return user;
//	}
//
//	private void authenticate(String email, String password) throws Exception {
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//		} catch (DisabledException e) {
//			throw new Exception("USER_DISABLED", e);
//		} catch (BadCredentialsException e) {
//			throw new Exception("INVALID_CREDENTIALS", e);
//		}
//	}
//
//}







package com.example.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.main.entity.Customer;
import com.example.main.repository.CustomerRepository;
//import com.example.main.dto.JwtRequest;
//import com.example.main.dto.JwtResponse;

@Service
public class JwtServiceImpl implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String customerId = jwtRequest.getCustomerId();
		String password = jwtRequest.getCustomerPassword();
		authenticate(customerId, password);

		loadUserByUsername(customerId);
		Customer customer = customerRepository.findByCustomerId(customerId);
		String generatedToken = jwtService.generateToken(customer.getCustomerId());

		return new JwtResponse(generatedToken, customer, customer.getCustomerId());
	}

	@Override
	public UserDetails loadUserByUsername(String customerId) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByCustomerId(customerId);
		if (customer == null) {
			System.out.println("User Not Found");
			throw new UsernameNotFoundException("Customer not found");
		}

		return customer;
	}
	
	private void authenticate(String customerId, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customerId, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}












