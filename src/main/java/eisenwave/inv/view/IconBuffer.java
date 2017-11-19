package eisenwave.inv.view;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class IconBuffer {
    
    protected final int width, height;
    protected final Icon[] buffer;
    
    public IconBuffer(int width, int height) {
        if (width < 1) throw new IllegalArgumentException("width must be > 0");
        if (height < 1) throw new IllegalArgumentException("height must be > 0");
        this.width = width;
        this.height = height;
        this.buffer = new Icon[width * height];
    }
    
    
    // GETTERS
    
    /**
     * Returns the width of this buffer.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the height of this buffer.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Returns the size of this buffer.
     *
     * @return the size
     */
    public int getSize() {
        return buffer.length;
    }
    
    /**
     * Returns the item at a given x and y coordinate.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the item
     */
    public Icon get(int x, int y) {
        return buffer[x + y * width];
    }
    
    /**
     * Returns the item at a given index.
     *
     * @param index the index
     * @return the item
     * @throws IndexOutOfBoundsException if the index exceeds the buffer boundaries
     */
    public Icon get(int index) {
        return buffer[index];
    }
    
    // MUTATORS
    
    /**
     * Sets the item at the given position to a given item stack.
     *
     * @param index the index
     * @param icon the icon
     * @throws IndexOutOfBoundsException if the index exceeds the buffer boundaries
     */
    protected void set(int index, @Nullable Icon icon) {
        buffer[index] = icon;
    }
    
    /**
     * Sets the item at the given position to a given item stack.
     *
     * @param x the x-position
     * @param y the y-position
     * @param icon the icon
     */
    public void set(int x, int y, @Nullable Icon icon) {
        set(x + y * width, icon);
    }
    
    /**
     * Draws the buffer into this buffer.
     *
     * @param x the x-position
     * @param y the y-position
     * @param buffer the buffer to draw
     */
    public void set(int x, int y, IconBuffer buffer) {
        if (buffer.width + x > width || buffer.height + y > height)
            throw bufferPlaceException(x, y, buffer);
        for (int i = 0; i < buffer.width; i++)
            for (int j = 0; j < buffer.height; j++)
                this.set(x + i, y + j, buffer.get(i, j));
    }
    
    /**
     * Fills an area of a buffer with an icon.
     *
     * @param minX the minimum x-coordinate of the area
     * @param minY the minimum y-coordinate of the area
     * @param limX the x-limit (exclusive maximum)
     * @param limY the y-limit (exclusive maximum)
     * @param icon the icon to draw
     */
    public void fill(int minX, int minY, int limX, int limY, @Nullable Icon icon) {
        for (int x = minX; x < limX; x++)
            for (int y = minY; y < limY; y++)
                set(x, y, icon);
    }
    
    /**
     * Fills an area of a buffer with an icon.
     *
     * @param limX the x-limit (exclusive maximum)
     * @param limY the y-limit (exclusive maximum)
     * @param icon the icon to draw
     */
    public void fill(int limX, int limY, @Nullable Icon icon) {
        fill(0, 0, limX, limY, icon);
    }
    
    /**
     * Fills the entire buffer with an icon.
     */
    public void fill(@Nullable Icon icon) {
        Arrays.fill(buffer, icon);
    }
    
    /*
    public void setOffset(int x, int y) {
        if (x < 0) throw new IllegalArgumentException("x must be positive");
        if (y < 0) throw new IllegalArgumentException("y must be positive");
        this.offX = x;
        this.offY = y;
    }
    */
    
    /**
     * Clears the buffer.
     */
    public void clear() {
        Arrays.fill(buffer, null);
    }
    
    // ITERATION
    
    /**
     * Performs an action for each item stack and its position.
     *
     * @param action the action
     */
    public void forEach(Consumer action) {
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                if (get(x, y) != null)
                    action.accept(x, y, get(x, y));
    }
    
    @FunctionalInterface
    public static interface Consumer {
        abstract void accept(int x, int y, Icon icon);
    }
    
    // ERROR
    
    private IndexOutOfBoundsException bufferPlaceException(int x, int y, IconBuffer buffer) {
        String error = String.format(
            "%d x %d buffer drawn at %d, %d: drawable buffer exceeds this buffer's boundaries (%d x %d)",
            buffer.getWidth(), buffer.getHeight(),
            x, y,
            this.getWidth(), this.getHeight());
        throw new IndexOutOfBoundsException(error);
    }
    
}
