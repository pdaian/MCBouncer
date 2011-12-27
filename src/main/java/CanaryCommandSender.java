
import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;
import com.mcbouncer.util.ChatColor;

public class CanaryCommandSender implements LocalPlayer {

    protected MCBouncer controller;

    public CanaryCommandSender(MCBouncer controller) {
        this.controller = controller;
    }

    public String getName() {
        return "CONSOLE";
    }

    public boolean hasPermission(String permission) {
        return true;
    }

    public void sendMessage(String message) {
        controller.getLogger().info(ChatColor.stripColor(message));
    }
}
