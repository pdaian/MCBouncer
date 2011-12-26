package com.mcbouncer.exception;

/**
 * Exception for when a sub command is not found
 * 
 */
public class MissingNestedCommandException extends CommandUsageException {

    public MissingNestedCommandException(String message, String usage) {
        super(message, usage);
    }
}
