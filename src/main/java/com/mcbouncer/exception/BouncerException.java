package com.mcbouncer.exception;

public class BouncerException extends Exception {

    private static final long serialVersionUID = 1L;

    public BouncerException(String string) {
        super(string);
    }

    public BouncerException() {
    }
}
