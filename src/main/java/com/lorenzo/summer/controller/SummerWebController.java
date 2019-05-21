package com.lorenzo.summer.controller;

import com.lorenzo.summer.model.Expense;
import com.lorenzo.summer.service.IExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;

@RestController
public class SummerWebController {

    @Autowired
    private IExpenseService expenseService;

    private static final String template = "Hello, %s";

    @RequestMapping("/test")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        String ret = String.format(template, name);
        return ret;
    }

    @RequestMapping("/findById")
    public Expense findById(@RequestParam(value = "expenseId") int expenseId) {
        return expenseService.findById(expenseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));
    }

    @RequestMapping(path = "/deleteById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(@RequestParam(value = "expenseId") int expenseId) {
        final ResponseStatusException notFoundException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found, cannot delete");
        try {
            expenseService.deleteById(expenseId);
        } catch (IllegalArgumentException | EmptyResultDataAccessException e) {
            throw notFoundException;
        }
    }

    @RequestMapping(path = "/findAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Expense> findAll() {
        return expenseService.findAll();
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Expense save(@RequestBody Expense expense) {
        try {
            return expenseService.save(expense);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have passed an invalid Expense");
        }
    }

    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Expense update(@RequestBody Expense toUpdate) {
        return save(toUpdate);
    }
}
