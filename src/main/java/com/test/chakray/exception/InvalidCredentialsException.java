package com.test.chakray.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String msg) {
        super(msg);
    }
}
