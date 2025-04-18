package com.example.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JwtController {

	@Autowired
	private JwtServiceImpl jwtServiceImplementation;

	@PostMapping("/login")
	public JwtResponse generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {

		return jwtServiceImplementation.createJwtToken(jwtRequest);
	}
}

