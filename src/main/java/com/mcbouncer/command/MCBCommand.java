package com.mcbouncer.command;

import com.mcbouncer.bukkit.BaseCommand;
import com.mcbouncer.bukkit.MCBouncer;
import com.mcbouncer.util.MCBValidators;

public class MCBCommand extends BaseCommand {

    public MCBCommand(MCBouncer parent) {
        this.parent = parent;
    }

    public boolean runCommand() {
        if (args.length != 1) {
            return false;
        }
        BaseCommand commandClass;
        if( args[0].equals("version") ) {
            commandClass = new VersionCommand(parent);
        }
        else if( args[0].equals("reload") ) {
            commandClass = new ReloadCommand(parent);
        }
        else if( args[0].equals("help") ) {
            commandClass = new HelpCommand(parent);
        }
        else {
            return false;
        }
        commandClass.setArgs(args);
        commandClass.setParent(parent);
        commandClass.setSender(sender);
        return commandClass.runCommand();

    }
    
}
