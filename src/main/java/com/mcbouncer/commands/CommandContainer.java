package com.mcbouncer.commands;

import com.mcbouncer.MCBouncer;

/**
 * Simply stores the controller in a protected
 * field. It also provides an interface to ensure
 * that only command classes get registered.
 * 
 */
public abstract class CommandContainer {

    protected MCBouncer controller;

    public CommandContainer(MCBouncer controller) {
        this.controller = controller;
    }
}
