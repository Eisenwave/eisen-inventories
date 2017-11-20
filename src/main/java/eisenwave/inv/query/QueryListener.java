package eisenwave.inv.query;

import eisenwave.inv.menu.MenuManager;
import eisenwave.inv.menu.MenuSession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class QueryListener implements Listener {
    
    private final MenuManager menus;
    
    public QueryListener() {
        this.menus = MenuManager.getInstance();
    }
    
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        
        if (menus.hasSession(player)) {
            MenuSession session = menus.getSession(player);
            if (!session.isQueried()) return;
            Query<?> query = session.getQuery();
            
            if (query instanceof ChatQuery) {
                event.setCancelled(true);
                ChatQuery chatQuery = (ChatQuery) query;
                chatQuery.onResult(player, event.getMessage());
                menus.endQuery(player);
            }
        }
    }
    
}
