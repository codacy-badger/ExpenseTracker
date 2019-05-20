package com.lorenzo.summer.controller;

import com.lorenzo.summer.model.Expense;
import com.lorenzo.summer.service.IExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        return expenseService.findById(expenseId);
    }

    @RequestMapping(path = "/deleteById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(@RequestParam(value = "expenseId") int expenseId) {
        expenseService.deleteById(expenseId);
    }

    @RequestMapping(path = "/findAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Expense> findAll() {
        return expenseService.findAll();
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Expense save(@RequestBody Expense toCreate) {
        return expenseService.save(toCreate);
    }

    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Expense update(@RequestBody Expense toUpdate) {
        return expenseService.save(toUpdate);
    }
}
