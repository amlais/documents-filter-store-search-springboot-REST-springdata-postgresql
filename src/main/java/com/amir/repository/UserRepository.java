package com.amir.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amir.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
