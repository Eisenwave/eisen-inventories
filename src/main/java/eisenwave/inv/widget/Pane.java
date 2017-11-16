package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.view.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Pane extends View {
    
    private final ItemStack item;
    
    public Pane(@NotNull Menu menu, @Nullable ViewStyle style) {
        super(menu, new Style(style));
        this.item = getStyle().getItem("pane.item");
    }
    
    @Override
    public void drawContent(IconBuffer buffer) {
        buffer.fill(getWidth(), getHeight(), new Icon(this, item));
    }
    
    // STYLE
    
    /**
     * Returns a copy of this pane's item.
     *
     * @return a copy of this pane's item.
     */
    public ItemStack getItem() {
        return item.clone();
    }
    
    private static class Style extends ViewStyle {
        
        public Style(@Nullable ViewStyle parent) {
            super(parent);
            defineItem("pane.item", _DefaultStyles.PANE_ITEM);
        }
        
    }
    
}
