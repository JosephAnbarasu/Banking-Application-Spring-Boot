package com.Banking.service;

import java.util.List;

import com.Banking.dto.UserDto;

public interface UserService {

	UserDto getUserById(Long id);
	List<UserDto> getAllUsers();
	void deleteUser(Long id);
	UserDto modifyUserById(Long id, UserDto userDto);
}
