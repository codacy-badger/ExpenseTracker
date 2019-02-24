package com.lorenzo.summer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The expense you are trying to delete does not exists")
public class ExpenseDeleteException extends RuntimeException {
    public ExpenseDeleteException(String message) {
        super(message);
    }
}
