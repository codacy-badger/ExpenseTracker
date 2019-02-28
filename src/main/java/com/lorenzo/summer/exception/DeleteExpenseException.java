package com.lorenzo.summer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error deleting Expense")
public class DeleteExpenseException extends RuntimeException {
}
