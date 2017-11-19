package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.style.Stylesheet;
import eisenwave.inv.view.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class Display extends Widget {
    
    private ItemStack item;
    
    public Display(Menu menu, @Nullable Stylesheet style) {
        super(menu, new ViewSize(1, 1, true, true), new Style(style));
        this.item = getStyle().getItem("display.item");
    }
    
    @Override
    protected void drawContent(IconBuffer buffer) {
        buffer.set(0, 0, new Icon(this, item));
    }
    
    /**
     * Returns a copy of this display's item.
     *
     * @return a copy of this display's item.
     */
    public ItemStack getItem() {
        return item.clone();
    }
    
    /**
     * Sets the display's item.
     */
    public void setItem(ItemStack item) {
        this.item = item.clone();
        this.invalidate();
    }
    
    private static class Style extends Stylesheet {
        
        public Style(@Nullable Stylesheet parent) {
            super(parent);
            defineItem("display.item", _DefaultStyles.DISPLAY_ITEM);
        }
        
    }
    
}
