package com.mcbouncer.exception;

/**
 * Exception for when the user provides an invalid input
 * 
 */
public class CommandUsageException extends CommandException {

    protected String usage;

    public CommandUsageException(String message, String usage) {
        super(message);
        this.usage = usage;
    }

    public String getUsage() {
        return usage;
    }
}
