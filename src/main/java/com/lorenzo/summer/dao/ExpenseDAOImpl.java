package com.lorenzo.summer.dao;

import com.lorenzo.summer.exception.RepositoryException;
import com.lorenzo.summer.model.Expense;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

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
            Session session = sessionFactory.getCurrentSession();
            String getExpenseById = "SELECT * FROM expense E WHERE E.id = :expenseId";
            NativeQuery<Expense> query = session.createNativeQuery(getExpenseById, Expense.class);
            query.setParameter("expenseId", expenseId);
            return query.getSingleResult();
        } catch (Exception exception) {
            throw new RepositoryException(ExceptionUtils.getRootCause(exception), ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    @Override
    public Expense saveExpense(Expense expense) {
        try {
            Session session = sessionFactory.getCurrentSession();
            int savedExpensePrimaryKey = (int) session.save(expense);
            return getExpenseById(savedExpensePrimaryKey);
        } catch (Exception exception) {
            throw new RepositoryException(ExceptionUtils.getRootCause(exception), ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    @Override
    public Expense updateExpense(Expense updatedExpense) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(updatedExpense);
            return getExpenseById(updatedExpense.getId());
        } catch (Exception exception) {
            throw new RepositoryException(ExceptionUtils.getRootCause(exception), ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    @Override
    public void deleteExpense(int expenseId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(getExpenseById(expenseId));
        } catch (Exception exception) {
            throw new RepositoryException(ExceptionUtils.getRootCause(exception), ExceptionUtils.getRootCauseMessage(exception));
        }
    }

    @Override
    public List<Expense> getAllExpenses() {
        Session session = sessionFactory.getCurrentSession();
        final String fromExpenseTable = "FROM Expense";
        TypedQuery<Expense> expensesQuery = session.createQuery(fromExpenseTable, Expense.class);

        return expensesQuery.getResultList();
    }
}
