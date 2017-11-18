package eisenwave.inv.view;

public class ViewSize {
    
    public final static int
        MINIMUM = -1001,
        MAXIMUM = -1002,
        CENTER =  -1003,
        MATCH_PARENT = -1004,
        WRAP_CONTENT = -1005;
    
    /**
     * Returns the largest possible view size.
     *
     * @return the largest possible view size
     */
    public static ViewSize matchParent() {
        return new ViewSize(MINIMUM, MINIMUM, MATCH_PARENT, MATCH_PARENT);
    }
    
    /**
     * Returns the smallest possible view size.
     *
     * @return the smallest possible view size
     */
    public static ViewSize wrapContent() {
        return new ViewSize(MINIMUM, MINIMUM, WRAP_CONTENT, WRAP_CONTENT);
    }
    
    private final boolean staticWidth, staticHeight;
    private int x, y, width, height;
    
    public ViewSize(int x, int y, int width, int height, boolean staticWidth, boolean staticHeight) {
        this.staticWidth = staticWidth;
        this.staticHeight = staticHeight;
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
    
    public ViewSize(int width, int height, boolean staticWidth, boolean staticHeight) {
        this(MINIMUM, MINIMUM, width, height, staticWidth, staticHeight);
    }
    
    public ViewSize(int x, int y, int width, int height) {
        this(x, y, width, height, false, false);
    }
    
    public ViewSize(int width, int height) {
        this(MINIMUM, MINIMUM, width, height);
    }
    
    // GETTERS
    
    /**
     * Returns the x-offset
     *
     * @return the x-offset
     */
    public int getX() {
        return x;
    }
    
    /**
     * Returns the y-offset
     *
     * @return the y-offset
     */
    public int getY() {
        return y;
    }
    
    /**
     * Returns the width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Returns whether the width is immutable.
     *
     * @return whether the width is immutable
     */
    public boolean hasStaticWidth() {
        return staticWidth;
    }
    
    /**
     * Returns whether the height is immutable.
     *
     * @return whether the height is immutable
     */
    public boolean hasStaticHeight() {
        return staticHeight;
    }
    
    // SETTERS
    
    public void setX(int x) {
        if (x < 0 && x != MINIMUM && x != MAXIMUM && x != CENTER)
            throw new IllegalArgumentException("invalid x: "+x);
        this.x = x;
    }
    
    public void setY(int y) {
        if (y < 0 && y != MINIMUM && y != MAXIMUM && y != CENTER)
            throw new IllegalArgumentException("invalid y: "+y);
        this.y = y;
    }
    
    public void setWidth(int width) {
        if (width < 1 && width != MATCH_PARENT && width != WRAP_CONTENT)
            throw new IllegalArgumentException("invalid width: "+width);
        this.width = width;
    }
    
    public void setHeight(int height) {
        if (height < 1 && height != MATCH_PARENT && height != WRAP_CONTENT)
            throw new IllegalArgumentException("invalid height: "+height);
        this.height = height;
    }
    
}
