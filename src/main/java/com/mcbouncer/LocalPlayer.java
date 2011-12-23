package com.mcbouncer;

public interface LocalPlayer {
    
    public String getName();
    public boolean hasPermission(String permission);
    public void sendMessage(String message);
    
}
