package com.mcbouncer.exception;

public class NetworkException extends BouncerException {

    private static final long serialVersionUID = -2442886828808724203L;

    public NetworkException() {
        super();
    }

    public NetworkException(String msg) {
        super(msg);
    }
}