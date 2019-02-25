package com.lorenzo.summer.dao;

import com.lorenzo.summer.exception.RepositoryException;
import com.lorenzo.summer.model.Expense;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ExpenseDAOImpl implements IExpenseDAO {

    private SessionFactory sessionFactory;

    public ExpenseDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Expense getExpenseById(int expenseId) {
        try {
            return doGetExpenseById(expenseId);
        } catch (Exception exception) {
            throw new RepositoryException(ExceptionUtils.getRootCause(exception), ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    private Expense doGetExpenseById(int expenseId) {
        Session currentSession = sessionFactory.getCurrentSession();
        final String getExpenseById = "FROM Expense E WHERE E.id = :expenseId";
        TypedQuery<Expense> expenseByIdQuery = currentSession.createQuery(getExpenseById, Expense.class);
        expenseByIdQuery.setParameter("expenseId", expenseId);

        return expenseByIdQuery.getSingleResult();
    }

    @Override
    public Expense saveExpense(Expense expense) {
        try {
            return doGetExpense(expense);
        } catch (Exception exception) {
            throw new RepositoryException(ExceptionUtils.getRootCause(exception), ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    private Expense doGetExpense(Expense expense) {
        Session currentSession = sessionFactory.getCurrentSession();
        final int savedExpensePrimaryKey = (int) currentSession.save(expense);

        return getExpenseById(savedExpensePrimaryKey);
    }

    @Override
    public Expense updateExpense(Expense updatedExpense) {
        try {
            return doUpdateExpense(updatedExpense);
        } catch (Exception exception) {
            throw new RepositoryException(ExceptionUtils.getRootCause(exception), ExceptionUtils.getRootCauseMessage(exception));
        }

    }

    private Expense doUpdateExpense(Expense updatedExpense) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(updatedExpense);
        return getExpenseById(updatedExpense.getId());
    }

    @Override
    public int deleteExpense(int expenseId) {
        try {
            return doDeleteExpense(expenseId);
        } catch (Exception exception) {
            throw new RepositoryException(ExceptionUtils.getRootCause(exception), ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    private int doDeleteExpense(int expenseId) {
        Session currentSession = sessionFactory.getCurrentSession();
        final String deleteFrom = "delete FROM Expense WHERE id = :id";
        Query deleteExpenseQuery = currentSession.createQuery(deleteFrom);
        deleteExpenseQuery.setParameter("id", expenseId);

        return deleteExpenseQuery.executeUpdate();
    }

    @Override
    public List<Expense> getAllExpenses() {
        Session currentSession = sessionFactory.getCurrentSession();
        final String fromExpenseTable = "FROM Expense";
        TypedQuery<Expense> expensesQuery = currentSession.createQuery(fromExpenseTable, Expense.class);

        return expensesQuery.getResultList();
    }
}
