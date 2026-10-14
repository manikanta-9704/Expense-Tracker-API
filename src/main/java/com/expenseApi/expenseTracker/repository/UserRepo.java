package com.expenseApi.expenseTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expenseApi.expenseTracker.entity.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	 User findByEmailIgnoreCase(String email);

	    boolean existsByEmailIgnoreCase(String email);

}
