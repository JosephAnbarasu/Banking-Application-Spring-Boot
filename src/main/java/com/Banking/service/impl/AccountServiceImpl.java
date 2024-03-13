package com.Banking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Banking.dto.AccountDto;
import com.Banking.entity.Account;
import com.Banking.entity.User;
import com.Banking.mapper.AccountMapper;
import com.Banking.repository.AccountRepository;
import com.Banking.repository.UserRepository;
import com.Banking.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private UserRepository userRepository;
	
	private AccountRepository accountRepository;

	public AccountServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
		super();
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
	}

	public AccountDto createAccountForUserById(Long id,AccountDto accountDto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User with id not found"));
		Account account = AccountMapper.mapToAccount(accountDto);
		account.setUser(user);
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	public AccountDto getAccountById(Long id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("The account is not found"));
		return AccountMapper.mapToAccountDto(account);
	}

	public AccountDto deposit(Long id, Double amount) {
		Account account = accountRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("The account is not found"));
		Double total = account.getBalance()+ amount;
		account.setBalance(total);
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	public AccountDto withdraw(Long id, Double amount) {
	    
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();

	    // Retrieve the user associated with the authenticated username
	    User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // Retrieve the account associated with the provided account ID from the user's accounts
	    Account account = user.getAccounts().stream()
	            .filter(acc -> acc.getId().equals(id))
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("Account not found"));

	    
	    if (account.getBalance() < amount) {
	        throw new RuntimeException("Insufficient balance");
	    }
	    Double newBalance = account.getBalance() - amount;
	    account.setBalance(newBalance);
	    Account savedAccount = accountRepository.save(account);
	    return AccountMapper.mapToAccountDto(savedAccount);
	}


	public List<AccountDto> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		return accounts.stream().map((account)-> AccountMapper.mapToAccountDto(account))
				.collect(Collectors.toList());
	}

	public void deleteAccount(Long id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("The account is not found"));
		accountRepository.deleteById(id);
		
	}



	public List<AccountDto> getAccountsByUserId(Long id) {
	    User user = userRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("User with id not found"));
	    List<Account> accounts = user.getAccounts();
	    List<AccountDto> accountDtos = accounts.stream()
	            .map(AccountMapper::mapToAccountDto)
	            .collect(Collectors.toList());

	    return accountDtos;
	}

}
