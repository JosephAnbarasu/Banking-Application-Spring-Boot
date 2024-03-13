package com.Banking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.dto.AccountDto;
import com.Banking.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private AccountService accountService;

	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<AccountDto> addAccountforUserById(@PathVariable Long id,@RequestBody AccountDto accountDto){
		return new ResponseEntity<>(accountService.createAccountForUserById(id, accountDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
		AccountDto accountDto = accountService.getAccountById(id);
		return ResponseEntity.ok(accountDto);
	}
	
	@PutMapping("/{id}/deposit")
	public ResponseEntity<AccountDto> deposit(@PathVariable Long id,@RequestBody Map<String, Double> request){
		Double amount =request.get("amount");
		AccountDto accountDto = accountService.deposit(id, amount);
		return ResponseEntity.ok(accountDto);
	}
	
	@PutMapping("/{id}/withdraw")
	@PreAuthorize("#id == authentication.principal.id")
	public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,@RequestBody Map<String, Double> request){
		Double amount =request.get("amount");
		AccountDto accountDto = accountService.withdraw(id, amount);
		return ResponseEntity.ok(accountDto);
	}
	
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAllAccounts(){
		List<AccountDto> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}
	
	@DeleteMapping("/{id}/deleteAccount")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id){
		accountService.deleteAccount(id);
		return ResponseEntity.ok("Account is deleted successfully");
	}
	@GetMapping("/{id}/getaccounts")
	public ResponseEntity<List<AccountDto>> getAccountsByUserId(@PathVariable Long id){
		List<AccountDto> accounts =accountService.getAccountsByUserId(id);
		return ResponseEntity.ok(accounts);
	}
}