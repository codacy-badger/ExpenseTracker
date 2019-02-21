package com.lorenzo.summer.dao;

import com.lorenzo.summer.model.Expense;
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
            throw new RuntimeException("Error while retrieving expense " + expenseId
                    + " stacktrace: " + exception.getMessage());
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
    public List<Expense> getAllExpenses() {
        Session currentSession = sessionFactory.getCurrentSession();
        TypedQuery<Expense> expensesQuery = currentSession.createQuery("FROM Expense", Expense.class);
        return expensesQuery.getResultList();
    }

    @Override
    public Expense createExpense(Expense expense) {
        Session currentSession = sessionFactory.getCurrentSession();
        return getExpenseById((int) currentSession.save(expense));
    }

    @Override
    public Expense updateExpense(Expense updatedExpense) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(updatedExpense);
        return getExpenseById(updatedExpense.getId());
    }

    @Override
    public int deleteExpense(int expenseId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query deleteExpenseQuery = currentSession.createQuery("delete from Expense where id = :id");
        deleteExpenseQuery.setParameter("id", expenseId);
        return deleteExpenseQuery.executeUpdate();
    }

}
