package com.CodeCrafters.complaint_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String loginUser(LoginRequest loginRequest) {
		
		var userOptional = userRepository.findByEmail(loginRequest.email());
		
		if(userOptional.isPresent()) {
			User user=userOptional.get();
			if(passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
				return "User Logged in successfully!!";
			}
		}
		throw new RuntimeException("Error:Invalid email or password");
	}
}
