package com.mcbouncer.util.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Any command with this annotation will run the raw command as shown in the
 * thing, as long as it is registered in the current {@link CommandsManager}.
 * Mostly to move commands around without breaking things.
 *
 * @author zml2008
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandAlias {

    /**
     *
     * @return Raw {@link CommandsManager}-formatted command arg array to run
     */
    String[] value();
}
