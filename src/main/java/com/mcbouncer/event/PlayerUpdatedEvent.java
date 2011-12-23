package com.mcbouncer.event;

public class PlayerUpdatedEvent extends MCBEvent {

    @Override
    public MCBEventType getType() {
        return MCBEventType.PLAYER_UPDATED;
    }

}
