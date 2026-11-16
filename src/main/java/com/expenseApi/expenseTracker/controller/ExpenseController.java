package com.expenseApi.expenseTracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.expenseApi.expenseTracker.entity.Expense;
import com.expenseApi.expenseTracker.entity.User;
import com.expenseApi.expenseTracker.repository.UserRepo;
import com.expenseApi.expenseTracker.service.ExpenseService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "https://expense-tracker-frontend-seven-pied.vercel.app"
})
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserRepo userRepo;

    public ExpenseController(
            ExpenseService expenseService,
            UserRepo userRepo) {
        this.expenseService = expenseService;
        this.userRepo = userRepo;
    }

    // Get authenticated user's database ID
    private Integer getAuthenticatedUserId(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                    new RuntimeException("Authenticated user not found"));

        return user.getId();
    }

    // Add expense
    @PostMapping("/addexpense")
    public ResponseEntity<Expense> addExpense(
            Authentication authentication,
            @RequestBody Expense expense) {

        Integer userId = getAuthenticatedUserId(authentication);

        Expense savedExpense =
                expenseService.addExpense(expense, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }

    // View all expenses
    @GetMapping("/viewallexpenses")
    public List<Expense> viewAllExpenses(
            Authentication authentication) {

        Integer userId = getAuthenticatedUserId(authentication);

        return expenseService.viewAllExpenses(userId);
    }

    // View one expense
    @GetMapping("/viewexpense/{id}")
    public ResponseEntity<Expense> findExpenseById(
            @PathVariable int id,
            Authentication authentication) {

        Integer userId = getAuthenticatedUserId(authentication);

        Expense expense = expenseService.findExpenseById(id, userId);

        if (expense == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(expense);
    }

    // Update expense
    @PutMapping("/modifyexpense/{id}")
    public ResponseEntity<Expense> modifyExpense(
            @PathVariable int id,
            Authentication authentication,
            @RequestBody Expense expense) {

        Integer userId = getAuthenticatedUserId(authentication);

        Expense updatedExpense =
                expenseService.modifyExpense(expense, id, userId);

        if (updatedExpense == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedExpense);
    }

    // Delete one expense
    @DeleteMapping("/deleteexpense/{id}")
    public ResponseEntity<String> deleteExpense(
            @PathVariable int id,
            Authentication authentication) {

        Integer userId = getAuthenticatedUserId(authentication);

        boolean deleted = expenseService.deleteExpense(id, userId);

        if (deleted) {
            return ResponseEntity.ok("Expense deleted successfully.");
        }

        return ResponseEntity.notFound().build();
    }

    // Delete all expenses
    @DeleteMapping("/deleteallexpenses")
    public ResponseEntity<String> deleteAllExpenses(
            Authentication authentication) {

        Integer userId = getAuthenticatedUserId(authentication);

        expenseService.deleteAllExpenses(userId);

        return ResponseEntity.ok("All your expenses have been deleted.");
    }
}