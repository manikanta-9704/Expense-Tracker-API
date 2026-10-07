package com.expenseApi.expenseTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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


//initially recieves the request
@RestController
@RequestMapping("/api")
public class ExpenseController {
     @Autowired
     ExpenseService expenseService;
//no need to create an constructor just by using @autowired we can achieve this
//    public ExpenseController(ExpenseService expenseService) {
//        this.expenseService = expenseService;
//    }

    // Add Expense
    //direct entity acceptance (no dto)
    @PostMapping("/addexpense")
    public Expense addExpense(@RequestBody Expense expense) {

        return expenseService.addExpense(expense);
    }

    // View All Expenses
    @GetMapping
    public List<Expense> viewAllExpenses() {

        return expenseService.viewAllExpenses();
    }

    // View Expense By ID
    @GetMapping("expense/{id}")
    public Expense findExpenseById(@PathVariable int id) {

        return expenseService.findExpenseById(id);
    }

    // Modify Expense
    @PutMapping("/{id}")
    public Expense modifyExpense(
            @PathVariable int id,
            @RequestBody Expense expense) {

        return expenseService.modifyExpense(expense,id);
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
