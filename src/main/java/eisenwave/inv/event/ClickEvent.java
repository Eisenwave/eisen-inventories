package eisenwave.inv.event;

import eisenwave.inv.view.View;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClickEvent extends ViewEvent {
    
    public ClickEvent(@NotNull View view, @NotNull Player player) {
        super(view, player);
    }
    
}
