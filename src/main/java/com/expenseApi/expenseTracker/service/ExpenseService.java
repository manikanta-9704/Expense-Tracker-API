package com.expenseApi.expenseTracker.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expenseApi.expenseTracker.entity.Expense;
//import com.expenseApi.expenseTracker.enums.ExpenseType;
import com.expenseApi.expenseTracker.repository.ExpenseRepository;

@Service
public class ExpenseService {
@Autowired
    ExpenseRepository expenseRepository;

//    public ExpenseService(ExpenseRepository expenseRepository) {
//        this.expenseRepository = expenseRepository;
//    }

    // Add Expense
    public Expense addExpense(Expense expense) {

//        Expense expense = new Expense(
//                description,
//                amount,
//                expenseType
//        );

        return expenseRepository.save(expense);
    }

    // View All Expenses
    public List<Expense> viewAllExpenses() {

        return expenseRepository.findAll();
    }

    // Find Expense By ID
    public Expense findExpenseById(int expenseId) {

        return expenseRepository.findById(expenseId)
                .orElse(null);
    }

    // Modify Expense
    public Expense modifyExpense(Expense expense,Integer expenseId) {

        Expense exp = expenseRepository
                .findById(expenseId)
                .orElse(null);

        if (expense == null) {
            return null;
        }

        exp.setAmount(expense.getAmount());
        exp.setDescription(expense.getDescription());
        exp.setExpenseType(expense.getExpenseType());

        return expenseRepository.save(expense);
    }

    // Delete Expense
    public boolean deleteExpense(int expenseId) {

        Expense expense = expenseRepository
                .findById(expenseId)
                .orElse(null);

        if (expense == null) {
            return false;
        }

        expenseRepository.deleteById(expenseId);

        return true;
    }
}
