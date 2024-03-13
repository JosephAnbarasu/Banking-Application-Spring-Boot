package com.Banking.mapper;

import com.Banking.dto.UserDto;
import com.Banking.entity.User;

public class UserMapper {

	public static User mapToUser(UserDto userDto) {
		User user = new User(
				userDto.getId(),
				userDto.getFirstName(),
				userDto.getLastName(),
				userDto.getUsername(),
				userDto.getPassword(),
				userDto.getRole(),
				userDto.getAccounts());
		return user;
	}
	
	public static UserDto mapToUserDto(User user) {
		UserDto userDto = new UserDto(
				user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getUsername(),
				user.getPassword(),
				user.getRole(),
				user.getAccounts());
		return userDto;
	}
}
