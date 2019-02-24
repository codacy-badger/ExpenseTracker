package com.lorenzo.summer.dao;

import com.lorenzo.summer.SummerApplication;
import com.lorenzo.summer.exception.ExpenseNotFoundException;
import com.lorenzo.summer.model.Expense;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = SummerApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
//WE WANT TESTS TO BE ISOLATED, DirtiesContext RELOAD SPRING CONTEXT AFTER EACH TESTS, SO THAT THE HSQLDB STARTS CLEAN
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ExpenseDAOImplTest {

    private static final Expense EXPENSE_1 = new Expense(new Date(), "VENDOR_1", 1d, 1, new byte[1]);
    private static final Expense EXPENSE_2 = new Expense(new Date(), "VENDOR_2", 2d, 2, new byte[2]);
    private static final Expense EXPENSE_3 = new Expense(new Date(), "VENDOR_3", 3d, 3, new byte[3]);

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Autowired
    private IExpenseDAO sut;

    @Test
    @Transactional
    public void saveSomeExpenses_getExistingExpenseByItsId_expenseRetrievedSuccessfully() {
        //GIVEN
        final Expense EXPENSE = sut.createExpense(EXPENSE_1);
        sut.createExpense(EXPENSE_2);
        sut.createExpense(EXPENSE_3);

        //WHEN
        Expense EXPENSE_1_READ = sut.getExpenseById(EXPENSE.getId());

        //THEN
        Assert.assertEquals(EXPENSE, EXPENSE_1_READ);
    }

    @Test(expected = ExpenseNotFoundException.class)
    @Transactional
    public void getExpenseById_noExpensesWithSuchIdExist_expenseNotFoundExceptionIsThrown() {
        //GIVEN
        sut.createExpense(EXPENSE_2);
        sut.createExpense(EXPENSE_3);

        //WHEN
        final int UNEXISTING_EXPENSE_ID = 7;
        sut.getExpenseById(UNEXISTING_EXPENSE_ID);
    }

    @Test
    @Transactional
    public void saveSomeExpenses_getAllSavedExpenses_allExpensesRetrievedSuccessfully() {
        //GIVEN
        sut.createExpense(EXPENSE_1);
        sut.createExpense(EXPENSE_2);
        sut.createExpense(EXPENSE_3);

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
        final Expense AN_EXPENSE = sut.createExpense(EXPENSE_1);

        //THEN
        Assert.assertEquals(1, AN_EXPENSE.getId());
    }

    @Test
    @Transactional
    public void saveTwoExpenses_saveTheExpenses_expesesIdsAreCorrectlyOrdered() {
        //WHEN
        sut.createExpense(EXPENSE_1);
        final Expense ANOTHER_EXPENSE = sut.createExpense(EXPENSE_2);

        //THEN
        Assert.assertEquals(2, ANOTHER_EXPENSE.getId());

    }

    @Test
    @Transactional
    public void saveAnExpense_retrieveModifyAndSaveUpdatedExpense_expenseIsCorrectlyUpdated(){
        //GIVEN
        final Expense AN_EXPENSE = sut.createExpense(EXPENSE_1);
        Expense AN_EXPENSE_READ = sut.getExpenseById(AN_EXPENSE.getId());

        //WHEN
        AN_EXPENSE_READ.setVendor("UPDATED_VENDOR");
        AN_EXPENSE_READ.setPrice(5d);
        AN_EXPENSE_READ.setPay_method(3);
        sut.updateExpense(AN_EXPENSE_READ);

        //THEN
        Assert.assertEquals(AN_EXPENSE_READ, sut.getExpenseById(AN_EXPENSE.getId()));
    }

    @Test
    @Transactional
    public void saveAnExpense_deleteThatExpense_tryGettingThatExpenseResultInExceptionBecauseExpenseDeleted(){
        //GIVEN
        sut.createExpense(EXPENSE_1);
        final Expense EXPENSE_TO_BE_DELETED = sut.createExpense(EXPENSE_2);
        sut.createExpense(EXPENSE_3);

        //WHEN
        sut.deleteExpense(EXPENSE_TO_BE_DELETED.getId());

        //THEN
        exceptionRule.expect(RuntimeException.class);
        sut.getExpenseById(EXPENSE_TO_BE_DELETED.getId());
    }

    @Test
    @Transactional
    public void deleteExpense_thatExpenseExists_expenseIsDeleted(){
        //GIVEN
        sut.createExpense(EXPENSE_1);

        //WHEN
        int SINGLE_EXPENSE_DELETED = sut.deleteExpense(EXPENSE_1.getId());

        //THEN
        final int ONE_EXPENSE_DELETED = 1;
        Assert.assertEquals(ONE_EXPENSE_DELETED, SINGLE_EXPENSE_DELETED);
    }

}