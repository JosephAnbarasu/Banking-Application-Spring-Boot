package com.Banking.service.impl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Banking.entity.Account;
import com.Banking.entity.AuthenticationResponse;
import com.Banking.entity.User;
import com.Banking.repository.UserRepository;

@Service
public class AuthenticationService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtService jwtService;
	private AuthenticationManager authenticationManager;
	
	public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public AuthenticationResponse register(User request) {
		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		
        List<Account> accounts = request.getAccounts();
        if (accounts != null) {
            for (Account account : accounts) {
                account.setUser(user);
            }
            user.setAccounts(accounts);
        }

		user=userRepository.save(user);
		String token= jwtService.generateToken(user);
		return new AuthenticationResponse(token);
	}
	
	public AuthenticationResponse authenticate(User request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
		User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		String token = jwtService.generateToken(user);
		
		return new AuthenticationResponse(token);
	}
}
