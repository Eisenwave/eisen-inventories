package eisenwave.inv.view;

import org.bukkit.event.inventory.ClickType;

public class ViewAction {
    
    private final ViewActionType type;
    private ClickType clickType;
    
    public ViewAction(ViewActionType type, ClickType clickType) {
        this.type = type;
        this.clickType = clickType;
    }
    
    public ViewActionType getType() {
        return type;
    }
    
    public ClickType getClickType() {
        return clickType;
    }
    
}
