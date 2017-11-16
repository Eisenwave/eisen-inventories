package eisenwave.inv;

import eisenwave.inv.cmd.InvTestCommand;
import eisenwave.inv.menu.MenuManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EisenInventoriesPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(MenuManager.getInstance(), this);
        
        getCommand("invtest").setExecutor(new InvTestCommand());
        
        final MenuManager manager = MenuManager.getInstance();
        getServer().getPluginManager().registerEvents(MenuManager.getInstance(), this);
        getServer().getScheduler().runTaskTimer(this, manager::onTick, 0, 1);
    }
    
}
