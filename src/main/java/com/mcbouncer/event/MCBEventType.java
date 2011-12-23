package com.mcbouncer.event;

public enum MCBEventType {

    /**
     * Called after permissions check on the ban command.
     */
    ADD_BAN,
    /**
     * Called after the API call for banning has been made.
     */
    BAN_ADDED,
    /**
     * Called after permissions check on the unban command.
     */
    REMOVE_BAN,
    /**
     * Called after the API call for unbanning has been made.
     */
    BAN_REMOVED,
    /**
     * Called after permissions check on the add note command.
     */
    ADD_NOTE,
    /**
     * Called after the API call for note adding has been made.
     */
    NOTE_ADDED,
    /**
     * Called after permissions check on the remove note command.
     */
    REMOVE_NOTE,
    /**
     * Called after the API call for note removal has been made.
     */
    NOTE_REMOVED,
    /**
     * Called before the API call for updating user has been made.
     */
    PLAYER_UPDATE,
    /**
     * Called after the API call for updating user has been made.
     */
    PLAYER_UPDATED
}