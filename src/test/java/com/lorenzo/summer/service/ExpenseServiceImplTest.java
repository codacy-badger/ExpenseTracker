package com.lorenzo.summer.service;

import com.lorenzo.summer.SummerApplication;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = SummerApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
//WE WANT TESTS TO BE ISOLATED, DirtiesContext RELOAD SPRING CONTEXT AFTER EACH TESTS, SO THAT THE HSQLDB STARTS CLEAN
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ExpenseServiceImplTest {

    private static final Expense EXPENSE_1 = new Expense(new Date(), "VENDOR_1", 1d, 1, new byte[1]);
    private static final Expense EXPENSE_2 = new Expense(new Date(), "VENDOR_2", 2d, 2, new byte[2]);
    private static final Expense EXPENSE_3 = new Expense(new Date(), "VENDOR_3", 3d, 3, new byte[3]);

    @Autowired
    private IExpenseService sut;

    @Test
    @Transactional
    public void getAllExpensesTest() {
        //GIVEN
        Expense EXPENSE_1_SAVED = sut.createExpense(EXPENSE_1);
        Expense EXPENSE_2_SAVED = sut.createExpense(EXPENSE_2);
        Expense EXPENSE_3_SAVED = sut.createExpense(EXPENSE_3);
        Collection SAVED_EXPENSES = Arrays.asList(EXPENSE_1_SAVED, EXPENSE_2_SAVED, EXPENSE_3_SAVED);

        //WHEN
        List<Expense> expenses = sut.getAllExpenses();

        //THEN
        Assert.assertEquals(SAVED_EXPENSES, expenses);
    }

    @Test
    @Transactional
    public void saveExpenseTest() {
        //GIVEN
        Expense AN_EXPENSE = new Expense(new Date(), "VENDOR_1", 1d, 1, new byte[1]);

        //WHEN
        Expense AN_EXPENSE_SAVED = sut.saveExpense(AN_EXPENSE.getDate(), AN_EXPENSE.getVendor(),
                AN_EXPENSE.getPrice(), AN_EXPENSE.getPay_method(), AN_EXPENSE.getScan());

        //THEN
        final int EXPECTED_ID = 1;
        Assert.assertEquals(EXPECTED_ID, AN_EXPENSE_SAVED.getId());
    }

    @Test
    @Transactional
    public void saveExpenseObjectTest() {
        //GIVEN
        Expense AN_EXPENSE = new Expense(new Date(), "VENDOR_1", 1d, 1, new byte[1]);

        //WHEN
        Expense AN_EXPENSE_SAVED = sut.createExpense(AN_EXPENSE);

        //THEN
        final int EXPECTED_ID = 1;
        Assert.assertEquals(EXPECTED_ID, AN_EXPENSE_SAVED.getId());
    }

    @Test
    @Transactional
    public void updateExpenseTest() {
        //GIVEN
        Expense EXPENSE_1_SAVED = sut.createExpense(EXPENSE_1);

        final String UPDATED_VENDOR_EXAMPLE = "UPDATED_VENDOR";
        EXPENSE_1_SAVED.setVendor(UPDATED_VENDOR_EXAMPLE);

        final int BILL_SCAN_DIMENSION_EXAMPLE = 34;
        final byte[] BILL_SCAN_EXAMPLE = new byte[BILL_SCAN_DIMENSION_EXAMPLE];
        EXPENSE_1_SAVED.setScan(BILL_SCAN_EXAMPLE);

        final Double PRICE_EXAMPLE = 45d;
        EXPENSE_1_SAVED.setPrice(PRICE_EXAMPLE);


        //WHEN
        Expense EXPENSE_1_UPDATED = sut.updateExpense(EXPENSE_1_SAVED);

        //THEN
        Assert.assertEquals(EXPENSE_1_SAVED.getId(), EXPENSE_1_UPDATED.getId());
        Assert.assertEquals(UPDATED_VENDOR_EXAMPLE, EXPENSE_1_UPDATED.getVendor());
        Assert.assertEquals(BILL_SCAN_EXAMPLE, EXPENSE_1_UPDATED.getScan());
        Assert.assertEquals(PRICE_EXAMPLE, EXPENSE_1_UPDATED.getPrice());
    }

    @Test
    @Transactional
    public void getExpenseByIdTest() {
        //GIVEN
        Expense EXPENSE_1_SAVED = sut.createExpense(EXPENSE_1);
        Expense EXPENSE_2_SAVED = sut.createExpense(EXPENSE_2);
        Expense EXPENSE_3_SAVED = sut.createExpense(EXPENSE_3);

        //WHEN
        Expense EXPENSE_1_RETRIEVED = sut.getExpenseById(EXPENSE_1_SAVED.getId());

        // THEN
        Assert.assertEquals(EXPENSE_1_SAVED,EXPENSE_1_RETRIEVED);
    }

    @Test
    @Transactional
    public void deleteExpenseTest() {
        //GIVEN
        Expense EXPENSE_1_SAVED = sut.createExpense(EXPENSE_1);
        Expense EXPENSE_2_SAVED = sut.createExpense(EXPENSE_2);
        Expense EXPENSE_3_SAVED = sut.createExpense(EXPENSE_3);

        //WHEN
        final int numberExpensesDeleted = sut.deleteExpense(EXPENSE_2_SAVED.getId());

        // THEN
        Collection EXPENSES_IN_DB = sut.getAllExpenses();
        final int SINGLE_EXPENSE_DELETED = 1;
        Assert.assertEquals(SINGLE_EXPENSE_DELETED,numberExpensesDeleted);
        Assert.assertEquals(Arrays.asList(EXPENSE_1_SAVED, EXPENSE_3_SAVED), EXPENSES_IN_DB);
    }
}