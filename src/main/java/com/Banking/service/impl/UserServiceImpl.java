package com.Banking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Banking.dto.UserDto;
import com.Banking.entity.Account;
import com.Banking.entity.User;
import com.Banking.mapper.UserMapper;
import com.Banking.repository.UserRepository;
import com.Banking.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	private PasswordEncoder passwordEncoder;
	
	private UserRepository userRepository;

	public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	//instead of using createUser use Login or Register in Authentication Controller

	public UserDto getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("The user is not found"));
		return UserMapper.mapToUserDto(user);
	}
	
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map((user)-> UserMapper.mapToUserDto(user))
				.collect(Collectors.toList());
	}
	
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("The user is not found"));
		userRepository.deleteById(id);
		
	}

	public UserDto modifyUserById(Long id, UserDto userDto) {
		User user = userRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("The user is not found"));
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setUsername(userDto.getUsername());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setRole(userDto.getRole());
		user.setAccounts(userDto.getAccounts());
        List<Account> accounts = userDto.getAccounts();
        if (accounts != null) {
            for (Account account : accounts) {
                account.setUser(user);
            }
            user.setAccounts(accounts);
        }
        User updatedUser=userRepository.save(user);
		return UserMapper.mapToUserDto(updatedUser);
	}

}
