package com.expenseApi.expenseTracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.expenseApi.expenseTracker.entity.Expense;
import com.expenseApi.expenseTracker.service.ExpenseService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Add expense for a user
    @PostMapping("/addexpense")
    public ResponseEntity<Expense> addExpense(
            @RequestHeader("X-User-Id") Integer userId,
            @RequestBody Expense expense) {

        Expense savedExpense = expenseService.addExpense(expense, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }

    // View all expenses belonging to a user
    @GetMapping("/viewallexpenses")
    public List<Expense> viewAllExpenses(
            @RequestHeader("X-User-Id") Integer userId) {

        return expenseService.viewAllExpenses(userId);
    }

    // View a specific expense belonging to a user
    @GetMapping("/viewexpense/{id}")
    public ResponseEntity<Expense> findExpenseById(
            @PathVariable int id,
            @RequestHeader("X-User-Id") Integer userId) {

        Expense expense = expenseService.findExpenseById(id, userId);

        if (expense == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(expense);
    }

    // Update an expense belonging to a user
    @PutMapping("/modifyexpense/{id}")
    public ResponseEntity<Expense> modifyExpense(
            @PathVariable int id,
            @RequestHeader("X-User-Id") Integer userId,
            @RequestBody Expense expense) {

        Expense updatedExpense =
                expenseService.modifyExpense(expense, id, userId);

        if (updatedExpense == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedExpense);
    }

    // Delete a specific expense belonging to a user
    @DeleteMapping("/deleteexpense/{id}")
    public ResponseEntity<String> deleteExpense(
            @PathVariable int id,
            @RequestHeader("X-User-Id") Integer userId) {

        boolean deleted = expenseService.deleteExpense(id, userId);

        if (deleted) {
            return ResponseEntity.ok("Expense deleted successfully.");
        }

        return ResponseEntity.notFound()
                .build();
    }

    // Delete all expenses belonging to a user
    @DeleteMapping("/deleteallexpenses")
    public ResponseEntity<String> deleteAllExpenses(
            @RequestHeader("X-User-Id") Integer userId) {

        expenseService.deleteAllExpenses(userId);

        return ResponseEntity.ok("All your expenses have been deleted.");
    }
}