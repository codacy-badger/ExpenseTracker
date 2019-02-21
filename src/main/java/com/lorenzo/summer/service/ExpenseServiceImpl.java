package com.lorenzo.summer.service;

import com.lorenzo.summer.dao.IExpenseDAO;
import com.lorenzo.summer.model.Expense;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ExpenseServiceImpl implements IExpenseService {

    private IExpenseDAO expenseDAO;

    public ExpenseServiceImpl(IExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }

    @Override
    @Transactional
    public List<Expense> getAllExpenses() {
        return expenseDAO.getAllExpenses();
    }

    @Override
    @Transactional
    public Expense createExpense(Expense expense) {
        return expenseDAO.createExpense(expense);
    }

    @Override
    @Transactional
    public Expense saveExpense(Date date, String vendor, Double price, int pay_method, byte[] scan) {
        Expense expense = new Expense(date, vendor, price, pay_method, scan);
        return expenseDAO.createExpense(expense);
    }

    @Override
    @Transactional
    public Expense updateExpense(Expense toUpdate) {
        return expenseDAO.updateExpense(toUpdate);
    }

    @Override
    @Transactional
    public Expense getExpenseById(int expenseId) {
        return expenseDAO.getExpenseById(expenseId);
    }

    @Override
    @Transactional
    public int deleteExpense(int expenseId) {
        return expenseDAO.deleteExpense(expenseId);
    }

}
