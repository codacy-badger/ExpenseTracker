package com.lorenzo.summer.service;

import com.lorenzo.summer.model.Expense;

import java.util.Date;
import java.util.List;

public interface IExpenseService {
    List<Expense> getAllExpenses();

    Expense createExpense(Expense expense);

    Expense saveExpense(Date date, String vendor, Double price, int pay_method, byte[] scan);

    Expense updateExpense(Expense toUpdate);

    Expense getExpenseById(int expenseId);

    int deleteExpense(int expenseId);
}
