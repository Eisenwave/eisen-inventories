package eisenwave.inv.widget;

import eisenwave.inv.event.CheckListener;
import org.bukkit.entity.Player;

public interface Checkable {
    
    // MODEL
    
    /**
     * Returns whether this box is checked.
     *
     * @return whether this box is checked
     */
    public boolean isChecked();
    
    /**
     * Sets the checked state of this box.
     *
     * @param checked whether the box should be checked
     */
    public void setChecked(boolean checked);
    
    /**
     * Change the checked state to the inverse of its current state.
     */
    public void toggle();
    
    // INTERACTION
    
    /**
     * Performs a toggling of this object.
     * <p>
     * This will invoke all listeners.
     *
     * @param player the player
     */
    public void performToggle(Player player);
    
    public void addCheckListener(CheckListener listener);
    
    public void removeCheckListener(CheckListener listener);
    
}
