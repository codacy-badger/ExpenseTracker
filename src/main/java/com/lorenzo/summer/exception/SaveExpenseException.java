package com.lorenzo.summer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Error while saving the Expense")
public class SaveExpenseException extends RuntimeException {
    public SaveExpenseException() {
        super();
    }
}
