package eisenwave.inv.event;

import eisenwave.inv.view.View;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the checking state of a view changes.
 */
public class CheckEvent extends ViewEvent {
    
    private final boolean checked;
    
    public CheckEvent(@NotNull View view, @NotNull Player player, boolean checked) {
        super(view, player);
        this.checked = checked;
    }
    
    /**
     * Returns the new checking state
     *
     * @return the new checking state
     */
    public boolean isChecked() {
        return checked;
    }
    
}
