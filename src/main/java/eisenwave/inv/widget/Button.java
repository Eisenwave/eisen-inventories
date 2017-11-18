package eisenwave.inv.widget;

import eisenwave.inv.event.ClickEvent;
import eisenwave.inv.event.ClickListener;
import eisenwave.inv.menu.Menu;
import eisenwave.inv.menu.MenuResponse;
import eisenwave.inv.view.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class Button extends View implements Clickable {
    
    private final ItemStack item;
    
    private final Set<ClickListener> listeners = new HashSet<>();
    
    public Button(@NotNull Menu menu, @Nullable ViewStyle style) {
        super(menu.getContentPane(), menu, new Style(style), new ViewSize(1, 1, true, true));
        this.item = getStyle().getItem("button.item");
    }
    
    @Override
    public void drawContent(IconBuffer buffer) {
        buffer.set(0, 0, new Icon(this, item));
    }
    
    // CLICKABLE IMPL
    
    @Override
    public MenuResponse performAction(Player player, ViewAction action) {
        if (action.getType() == ViewActionType.CLICK)
            performClick(player, action.getClickType());
        return MenuResponse.OK;
    }
    
    @Override
    public void performClick(Player player, ClickType type) {
        ClickEvent action = new ClickEvent(this, player, type);
        for (ClickListener listener : listeners) {
            listener.onClick(action);
        }
    }
    
    @Override
    public void addClickListener(ClickListener listener) {
        listeners.add(listener);
    }
    
    @Override
    public void removeClickListener(ClickListener listener) {
        listeners.remove(listener);
    }
    
    // STYLE
    
    /**
     * Returns a copy of this button's item.
     *
     * @return a copy of this button's item.
     */
    public ItemStack getItem() {
        return item.clone();
    }
    
    private static class Style extends ViewStyle {
        
        public Style(@Nullable ViewStyle parent) {
            super(parent);
            defineItem("button.item", _DefaultStyles.BUTTON_ITEM);
        }
        
    }
    
}
