package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.view.IconBuffer;
import eisenwave.inv.view.View;
import eisenwave.inv.view.ViewGroup;
import eisenwave.inv.view.ViewSize;
import net.grian.spatium.util.Incrementer2;
import net.grian.spatium.util.PrimMath;
import org.jetbrains.annotations.NotNull;

public class SimpleList<T extends View> extends ViewGroup<T> {
    
    private boolean horizontal = true;
    private int offset = 0;
    
    public SimpleList(@NotNull Menu menu, @NotNull ViewSize size) {
        super(menu.getContentPane(), menu, size);
    }
    
    public boolean isVertical() {
        return !horizontal;
    }
    
    public void setVertical(boolean vertical) {
        this.horizontal = !vertical;
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
        this.offset = PrimMath.clamp(0, offset + count, children.size());
        this.invalidate();
    }
    
    /**
     * Increases or decreases the offset by a given amount of rows.
     *
     * Scrolling by <b>N</b> pages will result in scrolling by an amount of elements equal to:
     * <blockquote>
     *     <code><b>N</b> * (width if horizontal, else height)</code>
     * </blockquote>
     *
     * @param count the row count
     */
    public void scrollRows(int count) {
        int factor = horizontal? getWidth() : getHeight();
        scrollElements(count * factor);
    }
    
    /**
     * Increases or decreases the offset by a given amount of pages.
     *
     * Scrolling by <b>N</b> pages will result in scrolling by an amount of elements equal to:
     * <blockquote>
     *     <code><b>N</b> * width * height</code>
     * </blockquote>
     *
     * @param count the page count
     */
    public void scrollPages(int count) {
        scrollElements(count * getWidth() * getHeight());
    }
    
    @Override
    protected void drawContent(IconBuffer buffer) {
        Incrementer2 incr = horizontal?
            new Incrementer2(getWidth(), getHeight()) :
            new Incrementer2(getHeight(), getWidth());
        View[] toDraw = children.toArray(new View[children.size()]);
        
        for (int i = offset; i < toDraw.length; i++) {
            View child = toDraw[i];
            if (!incr.canIncrement()) break;
            int[] xy = incr.getAndIncrement();
            int x = horizontal? xy[0] : xy[1],
                y = horizontal? xy[1] : xy[0];
    
            child.setPosition(x, y);
            if (!child.isHidden()) {
                IconBuffer childBuffer = new IconBuffer(1, 1);
                child.draw(childBuffer);
                buffer.set(x, y, childBuffer);
            }
            child.revalidate();
        }
    }
    
}
