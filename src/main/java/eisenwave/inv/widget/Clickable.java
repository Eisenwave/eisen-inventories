package eisenwave.inv.widget;

import eisenwave.inv.event.ClickListener;
import org.bukkit.entity.Player;

public interface Clickable {
    
    /**
     * Performs a clicking of this object.
     * <p>
     * This will invoke all listeners.
     *
     * @param player the player
     */
    public void performClick(Player player);
    
    public void addClickListener(ClickListener listener);
    
    public void removeClickListener(ClickListener listener);
    
}
