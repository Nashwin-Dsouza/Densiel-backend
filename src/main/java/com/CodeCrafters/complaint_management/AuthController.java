package com.CodeCrafters.complaint_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


record RegisterRequest(String fullName, String email, String phoneNumber, String address, String password ) {}
record LoginRequest(String email,String password) {}

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
