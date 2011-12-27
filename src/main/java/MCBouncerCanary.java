
import com.mcbouncer.LocalConfiguration;
import com.mcbouncer.LocalServer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.util.NetUtils;
import java.io.File;
import java.util.Map;

/**
 * Implementation of the LocalServer interface. It
 * is the main plugin class for Canary/Crow.
 * 
 * It registers the configuration, sets the main controller,
 * registers the pseudo-listeners, interprets commands,
 * as well as implementing many of the server-specific 
 * methods that need to be called during the execution of
 * this plugin.
 * 
 */
public class MCBouncerCanary extends Plugin implements LocalServer {

    protected MCBouncer controller;

    @Override
    public void disable() {
        controller.getLogger().info("Plugin disabled (version " + MCBouncer.getVersion() + ")");
    }

    @Override
    public void enable() {

        LocalConfiguration config = new CanaryConfiguration(new File("config" + File.separator + "MCBouncer"));
        config.load();

        controller = new MCBouncer(this, config);

        controller.getLogger().info("Plugin enabled. (version " + MCBouncer.getVersion() + ")");
        controller.getLogger().debug("Debug mode enabled!");

        setupListeners();
        setupCommands();

    }

    protected void setupListeners() {
        CanaryListener listener = new CanaryListener(controller);
        PluginLoader pl = etc.getLoader();

        pl.addListener(PluginLoader.Hook.CONNECT, listener, this, PluginListener.Priority.CRITICAL);
        pl.addListener(PluginLoader.Hook.LOGINCHECK, listener, this, PluginListener.Priority.LOW);
        pl.addListener(PluginLoader.Hook.COMMAND_CHECK, listener, this, PluginListener.Priority.LOW);
        pl.addListener(PluginLoader.Hook.COMMAND, listener, this, PluginListener.Priority.HIGH);
        pl.addListener(PluginLoader.Hook.SERVERCOMMAND, listener, this, PluginListener.Priority.HIGH);
        pl.addListener(PluginLoader.Hook.CHAT, listener, this, PluginListener.Priority.LOW);
    }

    protected void setupCommands() {
        Map<String, String> map = controller.getCommandManager().getCommands();
        for (String key : map.keySet()) {
            etc.getInstance().addCommand(key, map.get(key));
        }
    }

    public boolean isPlayerOnline(String name) {
        return etc.getServer().getPlayer(name) != null;
    }

    public String getPlayerName(String name) {
        Player player = etc.getServer().getPlayer(name);
        if (player == null) {
            return name;
        }
        return player.getName();
    }

    public String getIPAddress(String ipOrName) {
        if (NetUtils.isIPAddress(ipOrName)) {
            return ipOrName;
        }

        Player player = etc.getServer().getPlayer(ipOrName);
        if (player == null) {
            return "";
        }
        return player.getIP();
    }

    public void kickPlayer(String name, String reason) {
        Player player = etc.getServer().getPlayer(name);
        if (player != null) {
            player.kick(reason);
        }
    }

    public void kickPlayerWithIP(String ip, String reason) {
        for (Player player : etc.getServer().getPlayerList()) {
            if (player.getIP().equals(ip)) {
                player.kick(reason);
            }
        }
    }

    public void messageMods(String string) {
        for (Player player : etc.getServer().getPlayerList()) {
            if (player.canUseCommand("/mcbouncer.mod")) {
                player.sendMessage(string);
            }
        }
    }

    public void broadcastMessage(String message) {
        for (Player player : etc.getServer().getPlayerList()) {
            player.sendMessage(message);
        }
    }
}
