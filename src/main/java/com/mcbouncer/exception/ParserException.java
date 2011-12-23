package com.mcbouncer.exception;

public class ParserException extends BouncerRuntimeException {

    private static final long serialVersionUID = -2442886939908724203L;

    public ParserException() {
        super();
    }

    public ParserException(String msg) {
        super(msg);
    }
}