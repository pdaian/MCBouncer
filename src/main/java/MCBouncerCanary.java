
import com.mcbouncer.command.*;
import com.mcbouncer.plugin.MCBouncer;
import com.mcbouncer.util.MCBLogger;
import com.mcbouncer.util.config.MCBConfiguration;
import java.io.File;
import java.util.HashMap;

public class MCBouncerCanary extends Plugin {

    public static final MCBLogger log = new MCBLogger();

    
    public HashMap<String, BaseCommand> commands = new HashMap<String, BaseCommand>();

    @Override
    public void disable() {
        log.info("Plugin disabled. (version " + MCBouncer.version + ")");
    }

    @Override
    public void enable() {
        
        MCBConfiguration.load(this.getDataFolder());
        setupListeners();
        setupCommands();

        log.info("Plugin enabled. (version " + MCBouncer.version + ")");
        log.debug("Debug mode enabled!");
    }
    
    private void setupListeners() {
        PluginListener listener = new Listener();
        etc.getLoader().addListener(PluginLoader.Hook.LOGIN, listener, this, PluginListener.Priority.CRITICAL);
        etc.getLoader().addListener(PluginLoader.Hook.KICK, listener, this, PluginListener.Priority.CRITICAL);
    }
    
    private void setupCommands() {
        this.commands.put("ban", new BanCommand(this));
        this.commands.put("banip", new BanipCommand(this));
        this.commands.put("unban", new UnbanCommand(this));
        this.commands.put("unbanip", new UnbanipCommand(this));
        this.commands.put("kick", new KickCommand(this));
        this.commands.put("lookup", new LookupCommand(this));
        this.commands.put("mcbouncer", new MCBCommand(this));
        this.commands.put("addnote", new AddnoteCommand(this));
        this.commands.put("removenote", new RemovenoteCommand(this));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        MCBCommandThread r = new MCBCommandThread(this, sender, command, commandLabel, args);
        r.start();
        return true;
    }

    public boolean hasPermission(Player player, String permission) {
        if (this.permissionHandler == null) {
            return player.isOp();
        } else {
            return this.permissionHandler.has(player, permission);
        }
    }

    public void messageMods(String message) {
        for (Player player : this.getServer().getOnlinePlayers()) {
            if (this.hasPermission(player, "mcbouncer.mod")) {
                player.sendMessage(message);
            }
        }
    }

    private File getDataFolder() {
        return new File("plugins" + File.separator + "MCBouncer");
    }
    
    private static class Listener extends PluginListener {

        public Listener() {
        }
        
    }
    
}
