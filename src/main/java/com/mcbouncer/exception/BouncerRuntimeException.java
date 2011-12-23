package com.mcbouncer.exception;

public class BouncerRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BouncerRuntimeException(String string) {
        super(string);
    }

    public BouncerRuntimeException() {
    }
}
