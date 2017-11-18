package eisenwave.inv.widget;

import eisenwave.inv.event.ClickListener;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface Clickable {
    
    /**
     * Performs a clicking of this object.
     * <p>
     * This will invoke all listeners.
     *
     * @param player the player
     */
    public void performClick(Player player, ClickType type);
    
    public void addClickListener(ClickListener listener);
    
    public void removeClickListener(ClickListener listener);
    
}
