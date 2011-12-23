package com.mcbouncer.event;

public class PlayerUpdateEvent extends MCBEvent {

    @Override
    public MCBEventType getType() {
        return MCBEventType.PLAYER_UPDATE;
    }

}
