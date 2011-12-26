package com.mcbouncer.exception;

/**
 * Exception for when an error occurs during an HTTP request
 * 
 */
public class NetworkException extends BouncerException {

    public NetworkException() {
        super();
    }

    public NetworkException(String msg) {
        super(msg);
    }

    public NetworkException(Throwable thrwbl) {
        super(thrwbl);
    }
}