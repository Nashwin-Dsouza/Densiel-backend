package com.CodeCrafters.complaint_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void registerUser(RegisterRequest registerRequest) {
		
		if(userRepository.findByEmail(registerRequest.email()).isPresent()) {
			throw new RuntimeException("Error: Email is already is use!!");
		}
		if(userRepository.findByPhoneNumber(registerRequest.phoneNumber()).isPresent()) {
			throw new RuntimeException("Error:Phone number is already in Use!!");
		}
		
		User user = new User();
		user.setFullName(registerRequest.fullName());
		user.setEmail(registerRequest.email());
		user.setPhoneNumber(registerRequest.phoneNumber());
		user.setAddress(registerRequest.address());
		user.setPassword(passwordEncoder.encode(registerRequest.password()));
	}
}
