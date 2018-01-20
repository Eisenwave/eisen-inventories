package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.style.Stylesheet;
import eisenwave.inv.view.IconBuffer;
import eisenwave.inv.view.View;
import eisenwave.inv.view.ViewGroup;
import eisenwave.inv.view.ViewSize;
import eisenwave.spatium.util.PrimMath;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class InlineList<T extends View> extends ViewGroup<T> {
    
    private final Button leftBtn, rightBtn;
    
    private final ItemStack itemLeftOn, itemRightOn, itemLeftOff, itemRightOff;
    
    private View[] toDraw;
    private final Style style;
    private int offset = 0;
    
    public InlineList(@NotNull Menu menu, @NotNull ViewSize size, @Nullable Stylesheet stylesheet) {
        super(menu, size);
        this.style = new Style(stylesheet);
        
        this.itemLeftOff = style.getItem("inline_list.nav_left.off");
        this.itemRightOff = style.getItem("inline_list.nav_right.off");
        this.itemLeftOn = style.getItem("inline_list.nav_left.on");
        this.itemRightOn = style.getItem("inline_list.nav_right.on");
        
        this.leftBtn = new Button(menu, null);
        this.leftBtn.setParent(this);
        this.leftBtn.setPosition(ViewSize.MIN_POS, 0);
        this.leftBtn.addClickListener(event -> {
            ClickType click = event.getClick();
            if (click.isRightClick())
                scrollElements(-1);
            else if (click.isLeftClick())
                scrollElements(-getWidth() + 2);
            else
                setOffset(0);
        });
        
        this.rightBtn = new Button(menu, null);
        this.rightBtn.setParent(this);
        this.rightBtn.setPosition(ViewSize.MAX_POS, 0);
        this.rightBtn.addClickListener(event -> {
            ClickType click = event.getClick();
            if (click.isRightClick())
                scrollElements(1);
            else if (click.isLeftClick())
                scrollElements(getWidth() - 2);
            else
                setOffset(getMaxOffset());
        });
    }
    
    // STYLE
    
    public Stylesheet getStyle() {
        return style;
    }
    
    public ItemStack getLeftNavItemOn() {
        return itemLeftOn;
    }
    
    public ItemStack getLeftNavItemOff() {
        return itemLeftOff;
    }
    
    public ItemStack getRightNavItemOn() {
        return itemRightOn;
    }
    
    public ItemStack getRightNavItemOff() {
        return itemRightOff;
    }
    
    /**
     * Returns the element offset of this list.
     *
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }
    
    /**
     * Returns the length of this list in elements.
     *
     * @return the length
     */
    public int getLength() {
        return children.size();
    }
    
    public int getMaxOffset() {
        return Math.max(0, children.size() - getWidth() + 2);
    }
    
    /**
     * Sets the element offset of this list.
     */
    public void setOffset(int offset) {
        if (offset < 0) throw new IllegalArgumentException("offset must be positive");
        this.offset = offset;
        this.invalidate();
    }
    
    /**
     * Increases or decreases the offset by a given amount of elements.
     *
     * @param count the element count
     */
    public void scrollElements(int count) {
        this.offset = PrimMath.clamp(0, offset + count, getMaxOffset());
        this.invalidate();
    }
    
    @Override
    public int getHeight() {
        return 1;
    }
    
    @Override
    public int getContentWidth() {
        return 2;
    }
    
    @Override
    public void prepareDraw() {
        if (children.isEmpty()) {
            toDraw = null;
            return;
        }
        
        int size = children.size();
        int adjOffset = Math.min(offset, size);
        int elementCount = getWidth() - 2;
        
        leftBtn.setItem(offset == 0? itemLeftOff : itemLeftOn);
        rightBtn.setItem(offset == getMaxOffset()? itemRightOff : itemRightOn);
        
        toDraw = children.toArray(new View[children.size()]);
        toDraw = Arrays.copyOfRange(toDraw, adjOffset, Math.min(size, adjOffset + elementCount));
        
        for (int i = 0; i < toDraw.length; i++) {
            View child = toDraw[i];
            child.setPosition(i + 1, 0);
        }
    }
    
    @SuppressWarnings("Duplicates")
    @Override
    protected void drawContent(IconBuffer buffer) {
        if (toDraw == null) return;
        
        buffer.set(0, 0, leftBtn.draw());
        leftBtn.revalidate();
        buffer.set(buffer.getWidth() - 1, 0, rightBtn.draw());
        rightBtn.revalidate();
        
        for (View child : toDraw) {
            if (!child.isHidden()) {
                IconBuffer childBuffer = new IconBuffer(child.getWidth(), child.getHeight());
                child.draw(childBuffer);
                buffer.set(child.getRelX(), child.getRelY(), childBuffer);
            }
            child.revalidate();
        }
    }
    
    private static class Style extends Stylesheet {
        
        public Style(@Nullable Stylesheet parent) {
            super(parent);
            defineItem("inline_list.nav_left.off", _DefaultStyles.NAV_BUTTON_L_OFF);
            defineItem("inline_list.nav_left.on", _DefaultStyles.NAV_BUTTON_L_ON);
            defineItem("inline_list.nav_right.off", _DefaultStyles.NAV_BUTTON_R_OFF);
            defineItem("inline_list.nav_right.on", _DefaultStyles.NAV_BUTTON_R_ON);
        }
        
    }
    
}
