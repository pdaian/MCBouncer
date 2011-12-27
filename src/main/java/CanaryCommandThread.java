
import com.mcbouncer.LocalPlayer;
import com.mcbouncer.MCBouncer;

public class CanaryCommandThread extends Thread {

    public MCBouncer controller;
    public Player sender;
    public String[] args;

    public CanaryCommandThread(MCBouncer controller, Player sender, String[] args) {
        this.controller = controller;
        this.sender = sender;
        this.args = args;
    }

    /**
     * Runs the command. If the onCommand method returns false,
     * it will show an error message in the form of a usage line.
     * 
     */
    @Override
    public void run() {
        if (!this.onCommand()) {
            String command = this.controller.getCommandManager().getCommands().get(args[0]);
            if (command != null) {
                sender.sendMessage("/" + args[0] + " " + command);
            }
        }
    }

    /**
     * Handles the incoming Canary command data,
     * transforms it into generic data that MCBouncer
     * can read, and sends it to the controller.
     * 
     * @return 
     */
    public boolean onCommand() {

        String[] split = args;
        split[0] = "/" + split[0];
        
        if( controller.getCommandManager().getCommands().containsKey(args[0]) ) {
            return true;
        }

        LocalPlayer newSender = new CanaryCommandSender(controller);
        if (sender != null) {
            newSender = new CanaryPlayer(controller, sender);
        }

        controller.handleCommand(newSender, split);

        return true;
    }
}
