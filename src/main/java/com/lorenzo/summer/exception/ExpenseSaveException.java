package com.lorenzo.summer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error while saving expense")
public class ExpenseSaveException extends RuntimeException {
    public ExpenseSaveException(String message) {
        super(message);
    }
}
