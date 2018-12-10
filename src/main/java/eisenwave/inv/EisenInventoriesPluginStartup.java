package eisenwave.inv;

import eisenwave.inv.cmd.CmdCancelQuery;
import eisenwave.inv.cmd.CmdInvTest;
import eisenwave.inv.cmd.EisenInvCommand;
import eisenwave.inv.menu.MenuManager;
import eisenwave.inv.query.QueryListener;
import eisenwave.inv.util.LegacyUtil;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EisenInventoriesPluginStartup {
    
    private MenuManager manager;
    private final JavaPlugin plugin;
    
    public EisenInventoriesPluginStartup(JavaPlugin master) {
        this.plugin = master;
    }
    
    public MenuManager getManager() {
        return manager;
    }
    
    public Plugin getPlugin() {
        return plugin;
    }
    
    public void onEnable() {
        manager = MenuManager.getInstance();
        
        initEvents();
        initCommands();
        
        plugin.getLogger().info(LegacyUtil.isApi13()? "Running on 1.13+ API" : "Running on 1.12- API");
    }
    
    public void onDisable() {
        manager.onStop();
    }
    
    protected void initEvents() {
        PluginManager plugMan = plugin.getServer().getPluginManager();
        plugMan.registerEvents(manager, plugin);
        plugMan.registerEvents(new QueryListener(), plugin);
        
        plugin.getServer().getScheduler().runTaskTimer(plugin, manager::onTick, 0, 1);
    }
    
    protected void initCommands() {
        EisenInvCommand[] commands = {
            new CmdInvTest(plugin),
            new CmdCancelQuery(plugin)
        };
        
        for (EisenInvCommand command : commands)
            plugin.getCommand(command.getName()).setExecutor(command);
    }
    
}
