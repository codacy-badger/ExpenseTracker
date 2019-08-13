package com.lorenzo.expense;

import com.lorenzo.expense.controller.ExpenseController;
import com.lorenzo.expense.repository.ExpenseRepository;
import com.lorenzo.expense.service.IExpenseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExpenseTracker.class)
public class ExpenseTrackerSmokeTest {

    @Autowired
    private ExpenseController controller;
    @Autowired
    private IExpenseService service;
    @Autowired
    private ExpenseRepository repository;

    @Test
    public void contextLoads() {
        assertNotNull(controller);
        assertNotNull(service);
        assertNotNull(repository);
    }
}
