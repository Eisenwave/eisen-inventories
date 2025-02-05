package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.style.Stylesheet;
import eisenwave.inv.util.EisenInvUtil;
import eisenwave.inv.view.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProgressBar extends Widget {
    
    private ItemStack onItem, offItem;
    
    private boolean vertical = false;
    private float progress = 0F;
    
    public ProgressBar(@NotNull Menu menu, @Nullable Stylesheet style) {
        super(menu, new Style(style));
        this.onItem = getStyle().getItem("progressbar.on");
        this.offItem = getStyle().getItem("progressbar.off");
    }
    
    public ProgressBar(@NotNull Menu menu, @NotNull ViewSize size, @Nullable Stylesheet style) {
        super(menu, size, new Style(style));
        this.onItem = getStyle().getItem("progressbar.on");
        this.offItem = getStyle().getItem("progressbar.off");
    }
    
    /**
     * Returns the progress.
     * <p>
     * This is guaranteed to be a rational number between 0 and 1.
     *
     * @return the progress
     */
    public float getProgress() {
        return progress;
    }
    
    /**
     * Returns whether the progress bar is vertical instead of horizontal.
     *
     * @return whether the progress bar is vertical
     */
    public boolean isVertical() {
        return vertical;
    }
    
    /**
     * Sets the progress. Values outside range(0,1) will be clamped.
     *
     * @param progress the progress
     */
    public void setProgress(float progress) {
        this.progress = EisenInvUtil.clamp01(progress);
        this.invalidate();
    }
    
    /**
     * Sets whether the progress bar should be vertical.
     *
     * @param vertical whether the progress bar should be vertical
     */
    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        this.invalidate();
    }
    
    @Override
    public void drawContent(IconBuffer buffer) {
        final int
            width = getWidth(),
            height = getHeight();
        Stylesheet style = getStyle();
        Icon on = new Icon(this, onItem);
        Icon off = new Icon(this, offItem);
        
        if (!vertical) {
            int onWidth = (int) (width * getProgress());
            buffer.fill(0, 0, onWidth, height, on);
            buffer.fill(onWidth, 0, width, height, off);
        } else {
            int onHeight = (int) (height * getProgress());
            buffer.fill(0, 0, width, onHeight, on);
            buffer.fill(0, onHeight, width, height, off);
        }
    }
    
    // STYLE
    
    /**
     * Returns a copy of this button's checked item.
     *
     * @return a copy of this button's checked item.
     */
    public ItemStack getOnItem() {
        return onItem.clone();
    }
    
    /**
     * Returns a copy of this button's unchecked item.
     *
     * @return a copy of this button's checked item.
     */
    public ItemStack getOffItem() {
        return offItem.clone();
    }
    
    public void setOnItem(ItemStack item) {
        this.onItem = item.clone();
    }
    
    public void setOffItem(ItemStack item) {
        this.offItem = item.clone();
    }
    
    private static class Style extends Stylesheet {
        
        public Style(@Nullable Stylesheet parent) {
            super(parent);
            defineItem("progressbar.on", _DefaultStyles.PROGRESSBAR_ON);
            defineItem("progressbar.off", _DefaultStyles.PROGRESSBAR_OFF);
        }
        
    }
    
}
