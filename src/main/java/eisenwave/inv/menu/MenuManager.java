package eisenwave.inv.menu;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public final class MenuManager implements Listener {
    
    // SINGLETON
    
    private static MenuManager instance;
    
    public static MenuManager getInstance() {
        return instance == null? instance = new MenuManager() : instance;
    }
    
    private MenuManager() {}
    
    private final Map<HumanEntity, Menu> menuMap = new HashMap<>();
    //private final Collection<Menu> menus = new HashSet<>();
    
    // EVENTS & TICK
    
    public void onTick() {
        menuMap.values().parallelStream().forEach(menu -> {
            menu.draw();
            menu.setInteractable(true);
        });
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(InventoryClickEvent event) {
        Menu menu = menuMap.get(event.getWhoClicked());
        if (menu == null) return;
        
        int slot = event.getSlot();
        if (event.getRawSlot() != slot || event.getSlot() < 0) {
            // lower (player) inventory or hotbar was clicked
            // OR click outside inventory
            event.setCancelled(true);
            return;
        }
        
        //Bukkit.broadcastMessage( event.getSlotType().toString() + " " + event.getSlot() + " " + event.getRawSlot());
        int x = slot % menu.getWidth(), y = slot / menu.getWidth();
        menu.click((Player) event.getWhoClicked(), x, y);
        menu.setInteractable(false);
        event.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onClose(InventoryCloseEvent event) {
        menuMap.remove(event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        menuMap.remove(event.getPlayer());
    }
    
    // ACTIONS
    
    public void openMenu(HumanEntity human, Menu menu) {
        menuMap.put(human, menu);
    }
    
    // GETTERS
    
    public Collection<Menu> getMenus() {
        return Collections.unmodifiableCollection(menuMap.values());
    }
    
    // UTIL
    
    
}
