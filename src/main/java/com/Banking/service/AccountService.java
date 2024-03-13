package com.Banking.service;

import java.util.List;


import com.Banking.dto.AccountDto;

public interface AccountService {

	AccountDto createAccountForUserById(Long id,AccountDto accountDto);
	AccountDto getAccountById(Long id);
	AccountDto deposit(Long id, Double amount);
	AccountDto withdraw(Long id,Double amount);
	List<AccountDto> getAllAccounts();
	void deleteAccount(Long id);
	List<AccountDto> getAccountsByUserId(Long id);
}
