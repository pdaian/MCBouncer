package com.mcbouncer.util.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates a list of permissions that should be checked.
 *
 * @author sk89q
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandPermissions {

    /**
     * A list of permissions. Only one permission has to be met
     * for the command to be permitted.
     */
    String[] value();
}
