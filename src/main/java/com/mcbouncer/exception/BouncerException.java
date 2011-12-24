package com.mcbouncer.exception;

/**
 * Generic MCBouncer Exception, used as a catch-all for all
 * internally thrown exceptions.
 * 
 */
public class BouncerException extends Exception {

    private static final long serialVersionUID = 1L;

    public BouncerException(String string) {
        super(string);
    }

    public BouncerException() {
    }

    public BouncerException(Throwable thrwbl) {
        super(thrwbl);
    }
}
