package com.lorenzo.summer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error updating Expense")
public class UpdateExpenseException extends RuntimeException {
    public UpdateExpenseException() {
        super();
    }
}
