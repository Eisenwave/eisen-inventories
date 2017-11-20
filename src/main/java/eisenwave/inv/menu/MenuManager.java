package eisenwave.inv.menu;

import eisenwave.inv.error.DrawException;
import eisenwave.inv.error.MissingSessionException;
import eisenwave.inv.query.Query;
import nl.klikenklaar.util.gui.buttons.Button;
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
    
    private final Map<HumanEntity, MenuSession> sessionMap = new HashMap<>();
    //private final Collection<Menu> menus = new HashSet<>();
    
    // EVENTS & TICK
    
    public void onTick() {
        sessionMap.values().parallelStream().forEach(session -> {
            if (!session.isQueried())
                drawSilently(session.getMenu());
        });
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(InventoryClickEvent event) {
        //Bukkit.broadcastMessage("interacting on primary? "+Bukkit.getServer().isPrimaryThread());
        MenuSession session = sessionMap.get(event.getWhoClicked());
        if (session == null || session.isQueried()) return;
        Menu menu = session.getMenu();
        if (!menu.isInteractable()) return;
        
        int slot = event.getSlot();
        if (event.getRawSlot() != slot || event.getSlot() < 0) {
            // lower (player) inventory or hotbar was clicked
            // OR click outside inventory
            event.setCancelled(true);
            return;
        }
        
        //Bukkit.broadcastMessage( event.getSlotType().toString() + " " + event.getSlot() + " " + event.getRawSlot());
        Player player = (Player) event.getWhoClicked();
        int x = slot % menu.getWidth(), y = slot / menu.getWidth();
        MenuResponse response = menu.performClick(player, x, y, event.getClick());
        menu.setInteractable(false);
        event.setCancelled(true);
        
        //player.sendMessage(response.name());
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (hasSession(player)) {
            MenuSession session = getSession(player);
            if (!session.isQueried()) {
                Menu menu = session.getMenu();
                menu.performClose(player);
                sessionMap.remove(player);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        endSession(player);
    }
    
    /**
     * Called when the server shuts down.
     * <p>
     * This will close all sessions and fail all queries.
     */
    public void onStop() {
        sessionMap.forEach((human, session) -> closeSession((Player) human, session));
    }
    
    // ACTIONS
    
    /**
     * Starts a new {@link MenuSession}.
     *
     * @param human the human
     * @param menu the menu
     */
    public void startSession(HumanEntity human, Menu menu) {
        if (hasSession(human)) {
            closeSession((Player) human, getSession(human));
        }
        sessionMap.put(human, new MenuSession(menu));
        menu.showTo((Player) human);
    }
    /**
     * Ends the {@link MenuSession} of a player if such a session exists.
     *
     * @param human the human
     */
    public void endSession(HumanEntity human) {
        if (hasSession(human)) {
            MenuSession session = getSession(human);
            closeSession((Player) human, session);
            sessionMap.remove(human);
        }
    }
    
    /**
     * Queries a player.
     *
     * @param human the human
     * @param query the query
     */
    public void startQuery(HumanEntity human, Query query) {
        if (hasSession(human)) {
            MenuSession session = getSession(human);
            if (session.isQueried())
                session.getQuery().onFail((Player) human);
            session.setQuery(query);
            human.closeInventory();
        } else {
            throw new MissingSessionException("human has no session that could be queried");
        }
    }
    
    /**
     * Stops a player query.
     *
     * @param human the human
     */
    public void endQuery(HumanEntity human) {
        if (hasSession(human)) {
            MenuSession session = getSession(human);
            session.setQuery(null);
            session.getMenu().showTo((Player) human);
        } else {
            throw new MissingSessionException("human has no session that could be queried");
        }
    }
    
    // GETTERS
    
    /**
     * Returns an immutable collection of all {@link MenuSession}s.
     *
     * @return all menu sessions
     */
    public Collection<MenuSession> getSessions() {
        return Collections.unmodifiableCollection(sessionMap.values());
    }
    
    /**
     * Returns whether the human has a {@link MenuSession}.
     *
     * @param human the human
     * @return whether the human has a session
     */
    public boolean hasSession(HumanEntity human) {
        return sessionMap.containsKey(human);
    }
    
    /**
     * Returns the session of the human or {@code null} if there is none.
     *
     * @param human the human
     * @return whether the human has a session
     */
    public MenuSession getSession(HumanEntity human) {
        return sessionMap.get(human);
    }
    
    // UTIL
    
    /**
     * Fails the query of a session and invokes {@link Menu#performClose(Player)} for the given menu.
     *
     * @param player the player
     * @param session the session
     */
    private static void closeSession(Player player, MenuSession session) {
        if (session.isQueried())
            session.getQuery().onFail(player);
        session.getMenu().performClose(player);
    }
    
    /**
     * Draws a menu and handles any {@link DrawException}s.
     *
     * @param menu the menu to draw
     */
    private static void drawSilently(Menu menu) {
        try {
            menu.draw();
        } catch (DrawException ex) {
            menu.revalidate();
            ex.printStackTrace();
        }
        menu.setInteractable(true);
    }
    
    
}
