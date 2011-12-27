
import com.mcbouncer.MCBouncer;
import com.mcbouncer.event.ChatEvent;
import com.mcbouncer.event.CommandEvent;
import com.mcbouncer.event.JoinEvent;
import com.mcbouncer.event.LoginEvent;
import com.mcbouncer.util.MiscUtils;
import net.lahwran.fevents.MCBEventHandler;

public class CanaryListener extends PluginListener {

    protected MCBouncer controller;

    public CanaryListener(MCBouncer controller) {
        this.controller = controller;
    }

    @Override
    public boolean onChat(Player player, String message) {
        ChatEvent newEvent = new ChatEvent(controller, player.getName());
        MCBEventHandler.callEvent(newEvent);

        if (newEvent.isCancelled()) {
            return true;
        }
        return false;
    }

    @Override
    public PluginLoader.HookResult canPlayerUseCommand(Player player, String command) {
        CommandEvent newEvent = new CommandEvent(controller, player.getName());
        MCBEventHandler.callEvent(newEvent);

        if (newEvent.isCancelled()) {
            return PluginLoader.HookResult.PREVENT_ACTION;
        }
        return PluginLoader.HookResult.ALLOW_ACTION;
    }

    @Override
    public boolean onCommand(Player player, String[] split) {
        CanaryCommandThread r = new CanaryCommandThread(controller, player, split);
        r.start();
        return false;
    }

    @Override
    public boolean onConsoleCommand(String[] split) {
        CanaryCommandThread r = new CanaryCommandThread(controller, null, split);
        r.start();
        return false;
    }

    @Override
    public String onLoginChecks(String user) {
        LoginEvent newEvent = new LoginEvent(controller, user);
        MCBEventHandler.callEvent(newEvent);

        if (newEvent.isCancelled()) {
            return "Unexpected error, try again in a few minutes.";
        }
        return "";
    }

    @Override
    public Object onPlayerConnect(Player player, HookParametersConnect event) {
        String name = player.getName();
        String ip = player.getIP();

        Thread r = new PlayerJoinThread(name, ip, event.getJoinMessage());
        r.start();

        event.setHidden(true);
        return event;
    }

    /**
     * Thread class to connect to the MCBouncer server
     * in a thread. This lets users into the server quicker,
     * and prevents banned users from spamming the server.
     */
    public class PlayerJoinThread extends Thread {

        protected String player;
        protected String ip;
        protected String message;

        public PlayerJoinThread(String player, String ip, String message) {
            this.player = player;
            this.ip = ip;
            this.message = message;
        }

        @Override
        public void run() {
            JoinEvent newEvent = new JoinEvent(controller, player, ip, message);
            MCBEventHandler.callEvent(newEvent);
        }
    }
}
