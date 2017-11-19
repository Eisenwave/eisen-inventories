package eisenwave.inv.view;

import eisenwave.inv.menu.*;

public interface Redrawable {
    
    /**
     * Initializes the {@link Redrawable} for drawing. This will be called by a {@link Menu} when it is about to draw
     * this widget.
     * <p>
     * This method will be called <u>once</u> before drawing an already invalidated widget.
     * <p>
     * Unlike {@link #draw(IconBuffer)}, this method is guaranteed to be called for a {@link View}, regardless of
     * whether it has an invalidated parent.
     */
    abstract void prepareDraw();
    
    /**
     * Draws this object into a buffer of items.
     *
     * @param buffer the buffer
     */
    abstract void draw(IconBuffer buffer);
    
    /**
     * Returns whether this {@link Redrawable} is hidden, meaning that it won't be drawn next time this is necessary.
     *
     * @return whether this object is hidden
     */
    abstract boolean isHidden();
    
    /**
     * Returns whether a {@link Redrawable} is no longer valid and needs to be redrawn.
     *
     * @return whether this object is no longer valid
     */
    abstract boolean isInvalidated();
    
}
