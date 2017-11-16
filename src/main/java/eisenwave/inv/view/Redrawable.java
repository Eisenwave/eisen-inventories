package eisenwave.inv.view;

public interface Redrawable {
    
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
