package eisenwave.inv.event;

import eisenwave.inv.view.View;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

public class ClickEvent extends ViewEvent {
    
    protected final ClickType type;
    
    public ClickEvent(@NotNull View view, @NotNull Player player, ClickType type) {
        super(view, player);
        this.type = type;
    }
    
    public ClickType getClick() {
        return type;
    }
    
}
