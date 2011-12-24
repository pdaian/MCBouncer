package com.mcbouncer.exception;

/**
 * Exception for when the JSON parser receives an invalid input
 * 
 */
public class ParserException extends BouncerRuntimeException {

    public ParserException() {
        super();
    }

    public ParserException(String msg) {
        super(msg);
    }
}