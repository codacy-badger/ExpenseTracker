package com.lorenzo.summer.controller;

import com.lorenzo.summer.model.Expense;
import com.lorenzo.summer.service.IExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class SummerWebController {

    @Autowired
    private IExpenseService expenseService;

    private static final String template = "Hello, %s";

    @RequestMapping("/test")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        String ret = String.format(template, name);
        return ret;
    }

    @RequestMapping("/expense")
    public Expense getExpenseById(@RequestParam(value="expenseId") int expenseId) {
        return expenseService.getExpenseById(expenseId);
    }

    @PostMapping(path = "/saveExpense",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Expense createExpense(@RequestBody Expense toCreate){
        return expenseService.saveExpense(toCreate);
    }

    @PostMapping(path = "/updateExpense",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Expense updateExpense(@RequestBody Expense toUpdate){
        return expenseService.updateExpense(toUpdate);
    }
}
