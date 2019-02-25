package com.lorenzo.summer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Expense not found")
public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(Throwable exception, String message) {
        super(message, exception);
    }
}
