package com.Banking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.entity.AuthenticationResponse;
import com.Banking.entity.User;
import com.Banking.service.impl.AuthenticationService;

@RestController
public class AuthenticationController {

	private AuthenticationService authService;

	public AuthenticationController(AuthenticationService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody User request){
		return ResponseEntity.ok(authService.authenticate(request));
	}
}
