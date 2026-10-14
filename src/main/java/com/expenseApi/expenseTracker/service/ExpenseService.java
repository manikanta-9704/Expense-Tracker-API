package com.expenseApi.expenseTracker.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expenseApi.expenseTracker.entity.Expense;
import com.expenseApi.expenseTracker.entity.User;
import com.expenseApi.expenseTracker.repository.ExpenseRepository;
import com.expenseApi.expenseTracker.repository.UserRepo;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepo userRepo;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            UserRepo userRepo) {
        this.expenseRepository = expenseRepository;
        this.userRepo = userRepo;
    }

    // Add expense for a specific user
    public Expense addExpense(Expense expense, Integer userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        expense.setUser(user);

        return expenseRepository.save(expense);
    }

    // Retrieve expenses for a specific user
    public List<Expense> viewAllExpenses(Integer userId) {
        return expenseRepository.findByUser_Id(userId);
    }

    // Retrieve one expense belonging to a specific user
    public Expense findExpenseById(
            int expenseId,
            Integer userId) {

        return expenseRepository
                .findByExpenseIdAndUser_Id(expenseId, userId)
                .orElse(null);
    }

    // Update an expense belonging to a specific user
    @Transactional
    public Expense modifyExpense(
            Expense updatedExpense,
            Integer expenseId,
            Integer userId) {

        Expense existingExpense = expenseRepository
                .findByExpenseIdAndUser_Id(expenseId, userId)
                .orElse(null);

        if (existingExpense == null) {
            return null;
        }

        existingExpense.setDescription(
                updatedExpense.getDescription());

        existingExpense.setAmount(
                updatedExpense.getAmount());

        existingExpense.setExpenseType(
                updatedExpense.getExpenseType());

        return expenseRepository.save(existingExpense);
    }

    // Delete one expense belonging to a specific user
    public boolean deleteExpense(
            int expenseId,
            Integer userId) {

        Expense expense = expenseRepository
                .findByExpenseIdAndUser_Id(expenseId, userId)
                .orElse(null);

        if (expense == null) {
            return false;
        }

        expenseRepository.delete(expense);

        return true;
    }

    // Delete all expenses belonging to a specific user
    @Transactional
    public void deleteAllExpenses(Integer userId) {
        expenseRepository.deleteByUser_Id(userId);
    }
}