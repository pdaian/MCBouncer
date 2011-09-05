package com.mcbouncer.event;

public class PlayerUpdateEvent extends MCBEvent {

    @Override
    public Type getType() {
        return Type.PLAYER_UPDATE;
    }

}
