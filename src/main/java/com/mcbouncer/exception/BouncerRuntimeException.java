package com.mcbouncer.exception;

/**
 * RuntimeException for internally thrown events
 * 
 */
public class BouncerRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BouncerRuntimeException(String string) {
        super(string);
    }

    public BouncerRuntimeException() {
    }
}
