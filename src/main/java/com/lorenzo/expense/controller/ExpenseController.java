package com.lorenzo.expense.controller;

import com.lorenzo.expense.model.Expense;
import com.lorenzo.expense.service.IExpenseService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ExpenseController {

    private final ResponseStatusException expenseNotFound = new ResponseStatusException(NOT_FOUND, "Expense not found");
    private final ResponseStatusException invalidExpense = new ResponseStatusException(BAD_REQUEST, "Invalid Expense");
    private IExpenseService expenseService;

    public ExpenseController(IExpenseService expenseServiceImpl) {
        this.expenseService = expenseServiceImpl;
    }

    @RequestMapping("/findById")
    public Expense findById(@RequestParam(value = "expenseId") int expenseId) {
        return expenseService.findById(expenseId).orElseThrow(() -> expenseNotFound);
    }

    @RequestMapping("/deleteById")
    public void deleteById(@RequestParam(value = "expenseId") int expenseId) {
        try {
            expenseService.deleteById(expenseId);
        } catch (IllegalArgumentException | EmptyResultDataAccessException e) {
            throw expenseNotFound;
        }
    }

    @RequestMapping(path = "/findAll", produces = APPLICATION_JSON_VALUE)
    public Collection<Expense> findAll() {
        return expenseService.findAll();
    }

    @PostMapping(path = "/save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Expense save(@RequestBody Expense expense) {
        try {
            return expenseService.save(expense);
        } catch (Exception e) {
            throw invalidExpense;
        }
    }

    @PostMapping(path = "/update", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Expense update(@RequestBody Expense toUpdate) {
        return save(toUpdate);
    }
}
