package com.lorenzo.summer.service;

import com.lorenzo.summer.dao.IExpenseDAO;
import com.lorenzo.summer.exception.*;
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
    public Expense saveExpense(Expense expense) {
        return doSaveExpense(expense);
    }

    @Override
    @Transactional
    public Expense saveExpense(Date date, String vendor, Double price, int pay_method, byte[] scan) {
        Expense expense = new Expense(date, vendor, price, pay_method, scan);
        return doSaveExpense(expense);
    }

    private Expense doSaveExpense(Expense expense) {
        try {
            return expenseDAO.saveExpense(expense);
        } catch (RepositoryException repositoryException) {
            throw new SaveExpenseException();
        }
    }

    @Override
    @Transactional
    public Expense updateExpense(Expense toUpdate) {
        try {
            return expenseDAO.updateExpense(toUpdate);
        } catch (RepositoryException repositoryException) {
            throw new UpdateExpenseException();
        }
    }

    @Override
    @Transactional
    public Expense getExpenseById(int expenseId) {
        try {
            return expenseDAO.getExpenseById(expenseId);
        } catch (RepositoryException repositoryException) {
            throw new ExpenseNotFoundException();
        }
    }

    @Override
    @Transactional
    public void deleteExpense(int expenseId) {
        try {
            expenseDAO.deleteExpense(expenseId);
        } catch (RepositoryException repositoryException) {
            throw new DeleteExpenseException();
        }
    }

}
