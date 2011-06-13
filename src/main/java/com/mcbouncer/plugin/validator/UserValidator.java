package com.mcbouncer.plugin.validator;

import com.mcbouncer.plugin.MCBouncer;
import org.bukkit.command.CommandExecutor;

public class UserValidator extends BaseValidator implements IValidator {

    public UserValidator(CommandExecutor command, MCBouncer parent) {
    }

    @Override
    public boolean isValid(String[] args) {
	
	if( !this.isAtLeastArgs(args, 1) ) return false; //Must be at least 1 argument
	if( this.isAtLeastArgs(args, 2) ) return false; //Must be only 1 argument
	
	return true;
    }

}