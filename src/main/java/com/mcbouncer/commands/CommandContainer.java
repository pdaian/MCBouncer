package com.mcbouncer.commands;

import com.mcbouncer.MCBouncer;

/**
 * Simply stores the controller in a protected
 * field. There's no real reason this needs to
 * exist, and should probably be decommissioned.
 * 
 */
public abstract class CommandContainer {
    
    protected MCBouncer controller;

    public CommandContainer(MCBouncer controller) {
        this.controller = controller;
    }
    
}
