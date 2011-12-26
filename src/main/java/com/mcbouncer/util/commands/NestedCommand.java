package com.mcbouncer.util.commands;

import com.mcbouncer.commands.CommandContainer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates a nested command. Mark methods with this annotation to tell
 * {@link CommandsManager} that a method is merely a shell for child
 * commands. Note that the body of a method marked with this annotation
 * will never called. Additionally, not all fields of {@link Command} apply
 * when it is used in conjunction with this annotation, although both
 * are still required.
 *
 * @author sk89q
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NestedCommand {

    /**
     * A list of classes with the child commands.
     */
    Class<? extends CommandContainer>[] value();
}
