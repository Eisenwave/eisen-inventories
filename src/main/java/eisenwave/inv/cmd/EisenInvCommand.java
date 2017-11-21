package eisenwave.inv.cmd;

import eisenwave.inv.EisenInventoriesPlugin;
import org.bukkit.command.CommandExecutor;

public abstract class EisenInvCommand implements CommandExecutor {
    
    private final String name;
    
    protected final EisenInventoriesPlugin plugin;
    
    public EisenInvCommand(EisenInventoriesPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }
    
    public EisenInventoriesPlugin getPlugin() {
        return plugin;
    }
    
    public String getName() {
        return name;
    }
    
}
