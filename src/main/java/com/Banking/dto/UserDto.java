package com.Banking.dto;

import java.util.List;

import com.Banking.entity.Account;
import com.Banking.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class UserDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private Role role;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@JsonIgnore
	private List<Account> accounts;
	
	public UserDto() {
		super();
	}

	public UserDto(Long id, String firstName, String lastName, String username, String password, Role role,
			List<Account> accounts) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
		this.accounts = accounts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
		
}
