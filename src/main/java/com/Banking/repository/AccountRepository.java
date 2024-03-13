package com.Banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Long> {
	List<Account> findByUserId(Long id);
}
