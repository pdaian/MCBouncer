package com.mcbouncer.event;

public class PlayerUpdatedEvent extends MCBEvent {

    @Override
    public Type getType() {
        return Type.PLAYER_UPDATED;
    }

}
