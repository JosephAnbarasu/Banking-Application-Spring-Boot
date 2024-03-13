package com.Banking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Banking.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
	Optional<User> findByUsername(String username);
}
