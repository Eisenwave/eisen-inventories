package eisenwave.inv.cmd;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;

public abstract class EisenInvCommand implements CommandExecutor {
    
    private final String name;
    
    protected final Plugin plugin;
    
    public EisenInvCommand(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }
    
    public Plugin getPlugin() {
        return plugin;
    }
    
    public String getName() {
        return name;
    }
    
}
