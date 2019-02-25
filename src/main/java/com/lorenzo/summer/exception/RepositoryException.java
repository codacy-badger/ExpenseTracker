package com.lorenzo.summer.exception;

public class RepositoryException extends RuntimeException {
    public RepositoryException(Throwable exception, String message) {
        super(message, exception);
    }
}
