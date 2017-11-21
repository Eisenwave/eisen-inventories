package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.style.Stylesheet;
import eisenwave.inv.view.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Pane extends Widget {
    
    private ItemStack item;
    
    public Pane(@NotNull Menu menu, @NotNull ViewSize size, @Nullable Stylesheet style) {
        super(menu, size, new Style(style));
        this.item = getStyle().getItem("pane.item");
    }
    
    public Pane(@NotNull Menu menu, @Nullable Stylesheet style) {
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
    
    /**
     * Sets this pane's item.
     */
    public void setItem(@NotNull ItemStack item) {
        this.item = item;
    }
    
    private static class Style extends Stylesheet {
        
        public Style(@Nullable Stylesheet parent) {
            super(parent);
            defineItem("pane.item", _DefaultStyles.PANE_ITEM);
        }
        
    }
    
}
