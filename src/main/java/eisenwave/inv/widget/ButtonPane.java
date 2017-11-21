package eisenwave.inv.widget;

import eisenwave.inv.event.ClickEvent;
import eisenwave.inv.event.ClickListener;
import eisenwave.inv.menu.Menu;
import eisenwave.inv.menu.MenuResponse;
import eisenwave.inv.style.Stylesheet;
import eisenwave.inv.view.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class ButtonPane extends Widget implements Clickable {
    
    private ItemStack item;
    
    private final Set<ClickListener> listeners = new HashSet<>();
    
    public ButtonPane(@NotNull Menu menu, @NotNull ViewSize size, @Nullable Stylesheet style) {
        super(menu, size, new Style(style));
        this.item = getStyle().getItem("button.item");
    }
    
    @Override
    public void drawContent(IconBuffer buffer) {
        buffer.fill(getWidth(), getHeight(), new Icon(this, item));
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
    
    /**
     * Sets the button's item to a copy of the given item.
     *
     * @param item the item
     */
    public void setItem(ItemStack item) {
        this.item = item.clone();
        this.invalidate();
    }
    
    private static class Style extends Stylesheet {
        
        public Style(@Nullable Stylesheet parent) {
            super(parent);
            defineItem("button.item", _DefaultStyles.BUTTON_ITEM);
        }
        
    }
    
}
