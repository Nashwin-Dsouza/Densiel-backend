package com.CodeCrafters.complaint_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CodeCrafters.complaint_management.dto.LoginRequest;
import com.CodeCrafters.complaint_management.dto.RegisterRequest;
import com.CodeCrafters.complaint_management.service.LoginService;
import com.CodeCrafters.complaint_management.service.RegisterService;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired 
	private LoginService loginService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
		try {
			registerService.registerUser(registerRequest);
			return ResponseEntity.ok("User registered successfully!");
		}catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
		try {
			String message=loginService.loginUser(loginRequest);
			return ResponseEntity.ok(message);
		}catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage())	;
	}
	}
}
