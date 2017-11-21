package eisenwave.inv;

import eisenwave.inv.cmd.CmdCancelQuery;
import eisenwave.inv.cmd.CmdInvTest;
import eisenwave.inv.cmd.EisenInvCommand;
import eisenwave.inv.menu.MenuManager;
import eisenwave.inv.query.QueryListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EisenInventoriesPlugin extends JavaPlugin {
    
    private MenuManager manager;
    private static EisenInventoriesPlugin instance;
    
    @Override
    public void onLoad() {
        instance = this;
    }
    
    @Override
    public void onEnable() {
        manager = MenuManager.getInstance();
        
        initEvents();
        initCommands();
    }
    
    private void initEvents() {
        PluginManager plugMan = getServer().getPluginManager();
        plugMan.registerEvents(manager, this);
        plugMan.registerEvents(new QueryListener(), this);
    
        getServer().getScheduler().runTaskTimer(this, manager::onTick, 0, 1);
    }
    
    private void initCommands() {
        EisenInvCommand[] commands = {
            new CmdInvTest(this),
            new CmdCancelQuery(this)
        };
        
        for (EisenInvCommand command : commands) {
            getCommand(command.getName()).setExecutor(command);
        }
    }
    
    @Override
    public void onDisable() {
        manager.onStop();
    }
    
    // GETTERS
    
    public MenuManager getMenuManager() {
        return manager;
    }
    
    public static EisenInventoriesPlugin getInstance() {
        return instance;
    }
    
}
