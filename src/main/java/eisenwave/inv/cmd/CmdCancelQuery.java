package eisenwave.inv.cmd;

import eisenwave.inv.error.MissingSessionException;
import eisenwave.inv.menu.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

public class CmdCancelQuery implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof HumanEntity) {
            try {
                MenuManager.getInstance().endQuery((HumanEntity) sender);
            } catch (MissingSessionException ex) {
                sender.sendMessage(ChatColor.RED + "[Error] " + ChatColor.RESET + "You don't have any query to cancel");
            }
            return true;
        }
        
        return false;
    }
    
}
