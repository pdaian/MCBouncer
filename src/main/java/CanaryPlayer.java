
import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;

public class CanaryPlayer implements LocalPlayer {

    protected MCBouncer controller;
    protected Player player;

    public CanaryPlayer(MCBouncer controller, Player player) {
        this.controller = controller;
        this.player = player;
    }

    public String getName() {
        return player.getName();
    }

    public boolean hasPermission(String permission) {
        return player.canUseCommand("/" + permission);
    }

    public void sendMessage(String message) {
        System.out.println(message);
        player.chat(message);
        player.sendMessage(message);
    }
}
