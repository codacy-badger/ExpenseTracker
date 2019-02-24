package com.lorenzo.summer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The expense you are trying to update does not exists")
public class ExpenseUpdateException extends RuntimeException {
    public ExpenseUpdateException(String message) {
        super(message);
    }
}
