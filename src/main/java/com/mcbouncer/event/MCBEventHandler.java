package com.mcbouncer.event;

import java.util.ArrayList;

public class MCBEventHandler {

    /**
     * List of all the listeners that are connected to the plugin.
     */
    private ArrayList<MCBListener> listeners = new ArrayList<MCBListener>();
    
    /**
     * Private contructor to ensure a singleton
     */
    private MCBEventHandler(){
    }
    
    /**
     * Register a listener to the plugin.
     * When an event is called, each listener gets called
     * @param listener Listener to register.
     */
    public void register(MCBListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    public void dispatch(MCBEvent event) {
        for (MCBListener listener : listeners){
            switch (event.getType()){
            case ADD_BAN:
                listener.onAddBan((AddBanEvent)event);
            case ADD_NOTE:
                listener.onAddNote((AddNoteEvent)event);
            case BAN_ADDED:
                listener.onBanAdded((BanAddedEvent)event);
            case BAN_REMOVED:
                listener.onBanRemoved((BanRemovedEvent)event);
            case NOTE_ADDED:
                listener.onNoteAdded((NoteAddedEvent)event);
            case NOTE_REMOVED:
                listener.onNoteRemoved((NoteRemovedEvent)event);
            case PLAYER_UPDATE:
                listener.onPlayerUpdate((PlayerUpdateEvent)event);
            case PLAYER_UPDATED:
                listener.onPlayerUpdated((PlayerUpdatedEvent)event);
            case REMOVE_BAN:
                listener.onRemoveBan((RemoveBanEvent)event);
            case REMOVE_NOTE:
                listener.onRemoveNote((RemoveNoteEvent)event);
            }
        }
    }
    
    /**
     * Singleton caller for EventHandler
     * @return EventHandler instance
     */
    public static MCBEventHandler getInstance() {
        return EventHandlerHolder.INSTANCE;
    }

    /**
     * Singleton holder
     */
    private static class EventHandlerHolder {

        /**
         * Singleton instance
         */
        private static final MCBEventHandler INSTANCE = new MCBEventHandler();
    }
}
