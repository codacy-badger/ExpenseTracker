package com.lorenzo.summer.dao;

import com.lorenzo.summer.SummerApplication;
import com.lorenzo.summer.exception.DeleteExpenseException;
import com.lorenzo.summer.exception.RepositoryException;
import com.lorenzo.summer.model.Expense;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest(classes = SummerApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
//WE WANT TESTS TO BE ISOLATED, DirtiesContext RELOAD SPRING CONTEXT AFTER EACH TESTS, SO THAT THE HSQLDB STARTS CLEAN
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ExpenseDAOImplTest {

    private static final Expense EXPENSE_1 = new Expense(new Date(), "VENDOR_1", 1d, 1, new byte[1]);
    private static final Expense EXPENSE_2 = new Expense(new Date(), "VENDOR_2", 2d, 2, new byte[2]);
    private static final Expense EXPENSE_3 = new Expense(new Date(), "VENDOR_3", 3d, 3, new byte[3]);

    @Autowired
    private IExpenseDAO sut;

    @Test
    @Transactional
    public void saveSomeExpenses_getExistingExpenseByItsId_expenseRetrievedSuccessfully() {
        //GIVEN
        final Expense EXPENSE = sut.saveExpense(EXPENSE_1);
        sut.saveExpense(EXPENSE_2);
        sut.saveExpense(EXPENSE_3);

        //WHEN
        Expense EXPENSE_1_READ = sut.getExpenseById(EXPENSE.getId());

        //THEN
        Assert.assertEquals(EXPENSE, EXPENSE_1_READ);
    }

    @Test(expected = RepositoryException.class)
    @Transactional
    public void getExpenseById_noExpensesWithSuchIdExist_expenseRetrieveExceptionIsThrown() {
        //GIVEN
        sut.saveExpense(EXPENSE_2);
        sut.saveExpense(EXPENSE_3);

        //WHEN
        final int UNEXISTING_EXPENSE_ID = 7;
        sut.getExpenseById(UNEXISTING_EXPENSE_ID);
    }

    @Test
    @Transactional
    public void saveSomeExpenses_getAllSavedExpenses_allExpensesRetrievedSuccessfully() {
        //GIVEN
        sut.saveExpense(EXPENSE_1);
        sut.saveExpense(EXPENSE_2);
        sut.saveExpense(EXPENSE_3);

        //WHEN
        List res = sut.getAllExpenses();

        //THEN
        Assert.assertTrue(res.containsAll(Arrays.asList(EXPENSE_1, EXPENSE_2, EXPENSE_3)));
    }

    @Test
    @Transactional
    public void noExpensesAreSaved_getAllSavedExpenses_anEmptyListIsReturned() {
        //WHEN
        List retrievedExpenses = sut.getAllExpenses();

        //THEN
        Assert.assertEquals(retrievedExpenses, Collections.emptyList());
    }

    @Test
    @Transactional
    public void saveAnExpense_saveTheExpense_savedExpenseIdIsCorrectlyGenerated() {
        //WHEN
        final Expense AN_EXPENSE = sut.saveExpense(EXPENSE_1);

        //THEN
        Assert.assertEquals(1, AN_EXPENSE.getId());
    }

    @Test
    @Transactional
    public void saveTwoExpenses_saveTheExpenses_expensesIdsAreCorrectlyOrdered() {
        //WHEN
        sut.saveExpense(EXPENSE_1);
        final Expense ANOTHER_EXPENSE = sut.saveExpense(EXPENSE_2);

        //THEN
        Assert.assertEquals(2, ANOTHER_EXPENSE.getId());

    }

    @Test(expected = RepositoryException.class)
    @Transactional
    public void saveTwoExpenses_alterIdOfASavedExpenseAndSave_expenseRetrieveExceptionIsThrown() {
        /*
        you cannot modify an Entity's ID in the same transaction
         */

        //GIVEN
        final Expense ID_ONE_EXPENSE = sut.saveExpense(EXPENSE_1);
        final Expense ID_TWO_EXPENSE = sut.saveExpense(EXPENSE_2);

        //WHEN
        ID_TWO_EXPENSE.setId(ID_ONE_EXPENSE.getId());
        sut.saveExpense(ID_TWO_EXPENSE);
    }

    @Test
    @Transactional
    public void saveAnExpense_retrieveModifyAndSaveUpdatedExpense_expenseIsCorrectlyUpdated() {
        //GIVEN
        final Expense AN_EXPENSE = sut.saveExpense(EXPENSE_1);
        Expense AN_EXPENSE_READ = sut.getExpenseById(AN_EXPENSE.getId());

        //WHEN
        AN_EXPENSE_READ.setVendor("UPDATED_VENDOR");
        AN_EXPENSE_READ.setPrice(5d);
        AN_EXPENSE_READ.setPay_method(3);
        sut.updateExpense(AN_EXPENSE_READ);

        //THEN
        Assert.assertEquals(AN_EXPENSE_READ, sut.getExpenseById(AN_EXPENSE.getId()));
    }

    @Test(expected = RepositoryException.class)
    @Transactional
    public void saveAnExpense_deleteThatExpense_tryGettingThatExpenseResultsInExpenseRetrieveException() {
        //GIVEN
        sut.saveExpense(EXPENSE_1);
        final Expense EXPENSE_TO_BE_DELETED = sut.saveExpense(EXPENSE_2);
        sut.saveExpense(EXPENSE_3);

        //WHEN
        sut.deleteExpense(EXPENSE_TO_BE_DELETED.getId());

        //THEN
        sut.getExpenseById(EXPENSE_TO_BE_DELETED.getId());
    }

    @Test
    @Transactional
    public void deleteExpense_expenseExists_expenseIsDeleted() {
        //GIVEN
        sut.saveExpense(EXPENSE_1);

        //WHEN
        sut.deleteExpense(EXPENSE_1.getId());

        //THEN
        Collection expensesRemained = sut.getAllExpenses();
        Assert.assertTrue(expensesRemained.isEmpty());
    }

    @Test(expected = RepositoryException.class)
    @Transactional
    public void deleteExpense_expenseDoesNotExists_deleteExpenseExceptionIsThrown() {

        final int NOT_EXISTING_EXPENSE_ID = 23;

        //WHEN
        sut.deleteExpense(NOT_EXISTING_EXPENSE_ID);
    }

    @Test(expected = RepositoryException.class)
    @Transactional
    public void anExpenseIsNotSaved_updateThatNonExistingExpense_expenseUpdateExceptionIsThrown() {
        EXPENSE_1.setId(1);
        //WHEN
        sut.updateExpense(EXPENSE_1);
    }

}