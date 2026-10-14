package com.expenseApi.expenseTracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expenseApi.expenseTracker.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    List<Expense> findByUser_Id(Integer userId);

    Optional<Expense> findByExpenseIdAndUser_Id(
            Integer expenseId,
            Integer userId
    );

    void deleteByUser_Id(Integer userId);
}