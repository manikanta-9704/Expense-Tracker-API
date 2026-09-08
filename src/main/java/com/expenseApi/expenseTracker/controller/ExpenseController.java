package com.expenseApi.expenseTracker.controller;

import java.util.List;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenseApi.expenseTracker.entity.Expense;
import com.expenseApi.expenseTracker.service.ExpenseService;



@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Add Expense
    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {

        return expenseService.addExpense(
                expense.getDescription(),
                expense.getAmount(),
                expense.getExpenseType()
        );
    }

    // View All Expenses
    @GetMapping
    public List<Expense> viewAllExpenses() {

        return expenseService.viewAllExpenses();
    }

    // View Expense By ID
    @GetMapping("/{id}")
    public Expense findExpenseById(@PathVariable int id) {

        return expenseService.findExpenseById(id);
    }

    // Modify Expense
    @PutMapping("/{id}")
    public Expense modifyExpense(
            @PathVariable int id,
            @RequestBody Expense expense) {

        return expenseService.modifyExpense(
                id,
                expense.getDescription(),
                expense.getAmount(),
                expense.getExpenseType()
        );
    }

    // Delete Expense
    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable int id) {

        boolean deleted = expenseService.deleteExpense(id);

        if (deleted) {
            return "Expense deleted successfully.";
        }

        return "Expense not found.";
    }
}
