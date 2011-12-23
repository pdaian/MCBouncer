package com.mcbouncer.exception;

public class InvalidArgumentException extends BouncerRuntimeException {

    private static final long serialVersionUID = -2442886939908724203L;

    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(String msg) {
        super(msg);
    }
}