package net.lahwran.fevents;

/**
 * @author lahwran
 * @param <TEvent> Event type
 */
public interface MCBListener<TEvent extends MCBEvent<TEvent>> {

    /**
     * Handle an event
     * @param event Event to handle
     */
    public void onEvent(TEvent event);
}
