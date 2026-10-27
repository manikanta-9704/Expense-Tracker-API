package com.expenseApi.expenseTracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expenseApi.expenseTracker.entity.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	Optional< User> findByEmailIgnoreCase(String email);

	    boolean existsByEmailIgnoreCase(String email);

}
