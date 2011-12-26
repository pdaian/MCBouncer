package com.mcbouncer.exception;

/**
 * Exception for when the API gets an unreadable result
 * 
 */
public class APIException extends BouncerException {

    public APIException() {
        super();
    }

    public APIException(String message) {
        super(message);
    }

    public APIException(Throwable t) {
        super(t);
    }
}
