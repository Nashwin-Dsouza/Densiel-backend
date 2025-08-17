package com.CodeCrafters.complaint_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

record RegisterRequest(String fullName, String email, String phoneNumber, String address, String password ) {}

@RestController
@RequestMapping("/api/auth")

public class AuthController {
	
	@Autowired
	private UserRepositioy userRepository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
		
		if(userRepository.findByEmail(registerRequest.email()).isPresent()) {
			return ResponseEntity.badRequest().body("Error: Email is already in use!");
		}
		
		User user = new User();
		user.setFullName(registerRequest.fullName());
		user.setEmail(registerRequest.email());
		user.setPhoneNumber(registerRequest.phoneNumber());
		user.setAddress(registerRequest.address());
		
		user.setPassword(passwordEncoder.encode(registerRequest.password()));
		
		userRepository.save(user);
		
		return ResponseEntity.ok("User Registered Sucessfully!");
		
	}
}
