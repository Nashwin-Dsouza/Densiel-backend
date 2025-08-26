package com.CodeCrafters.complaint_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.CodeCrafters.complaint_management.dto.RegisterRequest;
import com.CodeCrafters.complaint_management.model.User;
import com.CodeCrafters.complaint_management.repository.UserRepository;

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
		
		try {
            System.out.println("Attempting to save user: " + user.getEmail());
            userRepository.save(user);
            System.out.println("Successfully called save for user: " + user.getEmail());
        } catch (Exception e) {
            // This will print the full error to the Render logs
            System.err.println("DATABASE SAVE FAILED for user: " + user.getEmail());
            e.printStackTrace();
            // Re-throw the exception so the controller knows something went wrong
            throw new RuntimeException("Could not save user to the database.", e);
        }
	}
}
