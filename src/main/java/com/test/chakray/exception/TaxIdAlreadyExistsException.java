package com.test.chakray.exception;

public class TaxIdAlreadyExistsException extends RuntimeException {
    public TaxIdAlreadyExistsException(String msg) {
        super(msg);
    }
}
