package com.mcbouncer.exception;

/**
 * Exception for when there is an error during MCBouncer config.
 * 
 */
public class ConfigurationException extends BouncerException {

    public ConfigurationException() {
        super();
    }

    public ConfigurationException(String msg) {
        super(msg);
    }
}