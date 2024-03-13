package com.Banking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.dto.UserDto;
import com.Banking.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
		UserDto userDto = userService.getUserById(id);
		return ResponseEntity.ok(userDto);
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@DeleteMapping("/{id}/deleteUser")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return ResponseEntity.ok("User is deleted successfully");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> modifyUserById(@PathVariable Long id, @RequestBody UserDto userDto){
		userService.modifyUserById(id, userDto);
		return ResponseEntity.ok(userDto);
	}
}
