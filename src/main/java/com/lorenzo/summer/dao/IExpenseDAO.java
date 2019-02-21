package com.lorenzo.summer.dao;

import com.lorenzo.summer.model.Expense;

import java.util.List;

public interface IExpenseDAO {
    Expense getExpenseById(int expenseId);

    List<Expense> getAllExpenses();

    Expense createExpense(Expense expense);

    Expense updateExpense(Expense updatedExpense);

    int deleteExpense(int expenseId);
}
