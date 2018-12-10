package eisenwave.inv;

import org.bukkit.plugin.java.JavaPlugin;

public class EisenInventoriesPlugin extends JavaPlugin {
    
    private final EisenInventoriesPluginStartup startup = new EisenInventoriesPluginStartup(this);
    
    @Override
    public void onEnable() {
        startup.onEnable();
    }
    
    @Override
    public void onDisable() {
        startup.onDisable();
    }
    
}
