package com.lorenzo.summer.controller;

import com.lorenzo.summer.model.Expense;
import com.lorenzo.summer.service.IExpenseService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class SummerWebController {

    private IExpenseService expenseService;

    public SummerWebController(IExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @RequestMapping("/findById")
    public Expense findById(@RequestParam(value = "expenseId") int expenseId) {
        return expenseService.findById(expenseId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Expense not found"));
    }

    @RequestMapping(path = "/deleteById", consumes = APPLICATION_JSON_VALUE)
    public void deleteById(@RequestParam(value = "expenseId") int expenseId) {
        final ResponseStatusException notFoundException = new ResponseStatusException(NOT_FOUND, "Expense not found, cannot delete");
        try {
            expenseService.deleteById(expenseId);
        } catch (IllegalArgumentException | EmptyResultDataAccessException e) {
            throw notFoundException;
        }
    }

    @RequestMapping(path = "/findAll", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Collection<Expense> findAll() {
        return expenseService.findAll();
    }

    @PostMapping(path = "/save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Expense save(@RequestBody Expense expense) {
        try {
            return expenseService.save(expense);
        } catch (Exception e) {
            throw new ResponseStatusException(BAD_REQUEST, "You have passed an invalid Expense");
        }
    }

    @PostMapping(path = "/update", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Expense update(@RequestBody Expense toUpdate) {
        return save(toUpdate);
    }
}
