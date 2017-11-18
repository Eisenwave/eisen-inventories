package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.view.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class Display extends View {
    
    private final ItemStack item;
    
    public Display(Menu menu, @Nullable ViewStyle style) {
        super(menu.getContentPane(), menu, new Style(style), new ViewSize(1, 1, true, true));
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
    
    private static class Style extends ViewStyle {
        
        public Style(@Nullable ViewStyle parent) {
            super(parent);
            defineItem("display.item", _DefaultStyles.DISPLAY_ITEM);
        }
        
    }
    
}
