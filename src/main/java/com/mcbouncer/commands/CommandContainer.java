package com.mcbouncer.commands;

import com.mcbouncer.MCBouncer;

public abstract class CommandContainer {
    
    protected MCBouncer controller;

    public CommandContainer(MCBouncer controller) {
        this.controller = controller;
    }
    
}
