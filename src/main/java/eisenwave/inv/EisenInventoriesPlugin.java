package eisenwave.inv;

import eisenwave.inv.cmd.CmdCancelQuery;
import eisenwave.inv.cmd.CmdInvTest;
import eisenwave.inv.menu.MenuManager;
import eisenwave.inv.query.QueryListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EisenInventoriesPlugin extends JavaPlugin {
    
    private MenuManager manager;
    
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
        getCommand("invtest").setExecutor(new CmdInvTest());
        getCommand("cancelquery").setExecutor(new CmdCancelQuery());
    }
    
    @Override
    public void onDisable() {
        manager.onStop();
    }
    
}
