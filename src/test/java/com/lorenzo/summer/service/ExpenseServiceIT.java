package com.lorenzo.summer.service;

import com.lorenzo.summer.SummerApplication;
import com.lorenzo.summer.model.Expense;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Date;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.*;

@SpringBootTest(classes = SummerApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
//WE WANT TESTS TO BE ISOLATED, DirtiesContext RELOAD SPRING CONTEXT AFTER EACH TESTS, SO THAT THE HSQLDB STARTS CLEAN
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class ExpenseServiceIT {

    private Expense expense_1;
    private Expense expense_2;
    private Expense expense_3;

    @Autowired
    private IExpenseService sut;

    @Before
    public void setUp() {
        expense_1 = new Expense(new Date(), "VENDOR_1", 1d, 1, new byte[1]);
        expense_2 = new Expense(new Date(), "VENDOR_2", 2d, 2, new byte[2]);
        expense_3 = new Expense(new Date(), "VENDOR_3", 3d, 3, new byte[3]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullExpense_save_throwsIllegalArgumentException() {
        //WHEN
        sut.save(null);
    }

    @Test
    public void givenAnExpense_save_expenseSavedAndReturned() {
        //WHEN
        final Expense result = sut.save(expense_1);

        //THEN
        assertEquals(expense_1, result);
    }

    @Test(expected = ResponseStatusException.class)
    public void nullId_findById_throwsResponseStatusException() {
        //WHEN
        sut.findById(46);
    }

    @Test
    public void validId_findById_returnsExpense() {
        //GIVEN
        final Expense saved = sut.save(expense_1);

        //WHEN
        final Expense result = sut.findById(saved.getId());

        //THEN
        assertEquals(saved, result);
    }

    @Test
    public void noExpensesInRepository_findAll_emptyListReturned() {
        //WHEN
        final Collection<Expense> expenses = sut.findAll();

        //THEN
        assertEquals(emptyList(), expenses);
    }

    @Test
    public void saveSomeExpenses_findAll_fullListReturned() {
        //GIVEN
        sut.save(expense_1);
        sut.save(expense_2);
        sut.save(expense_3);

        //WHEN
        final Collection<Expense> expenses = sut.findAll();

        //THEN
        assertEquals(asList(expense_1, expense_2, expense_3), expenses);
    }

    @Test
    public void noExpensesInRepository_count_returnsZero() {
        //WHEN
        final long count = sut.count();

        //THEN
        assertEquals(0, count);
    }

    @Test
    public void saveSomeExpenses_count_countIsCorrect() {
        //GIVEN
        sut.save(expense_1);
        sut.save(expense_2);
        sut.save(expense_3);

        //WHEN
        final long count = sut.count();

        //THEN
        assertEquals(asList(expense_1, expense_2, expense_3).size(), count);
    }

    @Test(expected = ResponseStatusException.class)
    public void noExpensesInRepository_deleteById_throwsResponseStatusException() {
        //WHEN
        sut.deleteById(33);
    }

    @Test(expected = ResponseStatusException.class)
    public void saveAnExpense_deleteById_expenseDeleted() {
        //GIVEN
        final int expense2Id = expense_2.getId();
        sut.save(expense_2);

        //WHEN
        sut.deleteById(expense2Id);

        //THEN
        sut.findById(expense2Id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullExpense_delete_throwsIllegalArgumentException() {
        //WHEN
        sut.delete(null);
    }

    @Test(expected = ResponseStatusException.class)
    public void notExistingExpense_delete_throwsResponseStatusException() {
        //WHEN
        sut.delete(expense_3);
    }

    @Test
    public void notExistingExpense_existsById_returnsFalse() {
        //WHEN
        final boolean exists = sut.existsById(56);

        //THEN
        assertFalse(exists);
    }

    @Test
    public void existingExpense_existsById_returnsTrue() {
        //GIVEN
        sut.save(expense_2);

        //WHEN
        final boolean exists = sut.existsById(expense_2.getId());

        //THEN
        assertTrue(exists);
    }

    @Test
    public void noExpensesInRepository_deleteAll_tryToDelete() {
        //WHEN
        sut.deleteAll();
    }

    @Test
    public void saveSomeExpenses_deleteAll_allExpensesSuccessfullyDeleted() {
        //GIVEN
        sut.save(expense_1);
        sut.save(expense_2);
        sut.save(expense_3);

        //WHEN
        sut.deleteAll();

        //THEN
        assertEquals(emptyList(), sut.findAll());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullExpenseCollection_delete_throwsIllegalArgumentException() {
        //WHEN
        sut.deleteAll(null);
    }

    @Test
    public void saveSomeExpenses_deleteSomeOfThem_expensesSuccessfullyDeleted() {
        //GIVEN
        sut.save(expense_1);
        sut.save(expense_2);
        sut.save(expense_3);

        //WHEN
        sut.deleteAll(asList(expense_2, expense_3));

        //THEN
        assertFalse(sut.existsById(expense_3.getId()));
        assertFalse(sut.existsById(expense_2.getId()));
        assertTrue(sut.existsById(expense_1.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullCollectionOfIds_findAllByIds_throwsIllegalArgumentException() {
        //WHEN
        sut.findAllById(null);
    }

    @Test
    public void saveSomeExpenses_findAllByIds_findCorrectExpenses() {
        //GIVEN
        sut.save(expense_1);
        sut.save(expense_2);
        sut.save(expense_3);

        //WHEN
        final Collection<Expense> allById = sut.findAllById(asList(expense_2.getId(), expense_3.getId()));

        //THEN
        assertTrue(allById.contains(expense_2));
        assertTrue(allById.contains(expense_3));
    }

    @Test
    public void noExpensesInRepository_findAllByIds_returnsEmptyList() {
        //WHEN
        final Collection<Expense> allById = sut.findAllById(asList(expense_2.getId(), expense_3.getId()));

        //THEN
        assertEquals(emptyList(), allById);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullCollectionOfExpenses_saveAll_throwsIllegalArgumentException() {
        //WHEN
        sut.saveAll(null);
    }

    @Test
    public void collectionOfSomeExpenses_saveAll_expensesSavedAndReturned() {
        //WHEN
        final Collection<Expense> saved = sut.saveAll(asList(expense_3, expense_1, expense_2));

        //THEN
        assertEquals(3, saved.size());
    }

    @Test
    public void saveAnExpense_update_expenseCorrectlyUpdated() {
        //GIVEN
        final Expense saved = sut.save(expense_1);

        //WHEN
        final double newPrice = 8888888888888d;
        saved.setPrice(newPrice);
        sut.save(saved);

        //THEN
        final Expense byId = sut.findById(expense_1.getId());
        assertEquals(byId.getPrice(), newPrice, 0.0);
    }

}