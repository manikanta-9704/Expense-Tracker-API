package com.expenseApi.expenseTracker.entity;
import com.expenseApi.expenseTracker.enums.ExpenseType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
@Entity
@Table(name = "expense_class")
public class Expense {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int expenseId;

	    private String description;

	    private double amount;

	    @Enumerated(EnumType.STRING)
	    private ExpenseType expenseType;

	    public Expense() {
	    }

//	    public Expense(String description, double amount, ExpenseType expenseType) {
//	        this.description = description;
//	        this.amount = amount;
//	        this.expenseType = expenseType;
//	    }

	    public int getExpenseId() {
	        return expenseId;
	    }

	    public void setExpenseId(int expenseId) {
	        this.expenseId = expenseId;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public double getAmount() {
	    
	        return amount;
	    }

	    public void setAmount(double amount) {
	    	if(amount>0) {
	        this.amount = amount;
	    	}
	    }

	    public ExpenseType getExpenseType() {
	        return expenseType;
	    }

	    public void setExpenseType(ExpenseType expenseType) {
	        this.expenseType = expenseType;
	    }
}
