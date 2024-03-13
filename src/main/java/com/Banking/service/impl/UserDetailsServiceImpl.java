package com.Banking.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Banking.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


	private UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("user not found"));
	}

}
