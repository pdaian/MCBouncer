package com.mcbouncer.exception;

/**
 * Exception for any type of command error. 
 * 
 */
public class CommandException extends BouncerException {

    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(Throwable t) {
        super(t);
    }
}
