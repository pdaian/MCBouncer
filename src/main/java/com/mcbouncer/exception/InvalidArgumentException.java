package com.mcbouncer.exception;

/**
 * Exception for when a method receives an 
 * argument it cannot use correctly.
 * 
 */
public class InvalidArgumentException extends BouncerRuntimeException {

    private static final long serialVersionUID = -2442886939908724203L;

    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(String msg) {
        super(msg);
    }
}