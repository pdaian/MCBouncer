package com.mcbouncer.plugin;

import com.mcbouncer.plugin.validator.IValidator;
import java.util.HashMap;

public class MCBValidators {

    /**
     * Singleton instance
     */
    private static MCBValidators instance;
    /**
     * List of all validators
     */
    private HashMap<String, IValidator> validators = new HashMap<String, IValidator>();

    /**
     * Singleton instance retrieval
     */
    public static MCBValidators getInstance() {
        if (instance == null) {
            instance = new MCBValidators();
        }
        return instance;
    }

    /**
     * Adds a validator
     */
    public void registerValidator(String command, IValidator validator) {
        validators.put(command, validator);
    }

    /**
     * Returns true if a validator exists
     */
    public boolean hasValidator(String command) {
        return validators.containsKey(command);
    }

    /**
     * Returns the validator
     */
    public IValidator getValidator(String command) {
        return validators.get(command);
    }
}
