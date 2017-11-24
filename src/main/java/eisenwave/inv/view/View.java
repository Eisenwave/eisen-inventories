package eisenwave.inv.view;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.menu.MenuResponse;
import eisenwave.inv.widget.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the basic building block for user interface components. It is an abstraction of a visible and
 * interactable rectangular component in an inventory.
 * <p>
 * Views are generally of two types:<ul>
 * <li>Widgets: Views which display data or which allow user interaction, such as buttons</li>
 * <li>Layouts: Views which arrange widgets in certain patterns, such as grid layouts</li>
 * </ul>
 */
public abstract class View implements Redrawable {
    
    private final Menu menu;
    private View parent;
    private ViewSize size;
    
    private boolean hidden = false;
    private boolean invalidated = false;
    
    /**
     * Constructs a new view.
     *
     * @param parent the parent view
     * @param menu the menu
     * @see Menu#getContentPane()
     */
    protected View(View parent, Menu menu, @NotNull ViewSize size) {
        if (parent == null && this != this.getParent())
            throw new NullPointerException("view must either be its own parent or be constructed with one");
        this.parent = parent;
        this.menu = menu;
        this.size = size;
        invalidate();
    }
    
    /**
     * Constructs a new view.
     *
     * @param menu the menu
     * @see Menu#getContentPane()
     */
    protected View(@NotNull Menu menu) {
        this(menu.getContentPane(), menu, new ViewSize(ViewSize.MATCH_PARENT, ViewSize.MATCH_PARENT));
    }
    
    /*
    protected View(@NotNull Menu menu, @NotNull ViewStyle style) {
        this(menu.getContentPane(), menu, style);
    }
    */
    
    // PROTECTED
    
    /**
     * Returns a container for the offset and the dimensions of this view.
     *
     * @return the size of this view
     */
    @NotNull
    protected ViewSize getSize() {
        return size;
    }
    
    // DECLARED GETTERS
    
    /**
     * Returns the parent of this view. This method must always return a nonnull value as all view have a parent.
     * Views which are positioned directly in a menu will have the menu's content pane as their parent.
     * <p>
     * If a view does not have a parent, such as the menu's content pane, it will return itself as its parent.
     *
     * @return the parent view or itself it it has no parent
     */
    @NotNull
    public View getParent() {
        return parent;
    }
    
    /**
     * Returns the menu that this view has been placed in.
     *
     * @return this view's menu
     */
    @NotNull
    public Menu getMenu() {
        return menu;
    }
    
    /**
     * Returns the view's position inside the menu.
     *
     * @return the absolute x
     */
    public int getX() {
        return parent.getX() + getRelX();
    }
    
    /**
     * Returns the view's position inside the menu.
     *
     * @return the absolute y
     */
    public int getY() {
        return parent.getY() + getRelY();
    }
    
    /**
     * Returns the view's position inside its parent view.
     *
     * @return the relative x
     */
    public int getRelX() {
        int result = size.getX();
        switch (result) {
            case ViewSize.MIN_POS: return 0;
            case ViewSize.MAX_POS: {
                return (size.getWidth() == ViewSize.MATCH_PARENT)?
                    0 : getParent().getWidth() - this.getWidth();
            }
            case ViewSize.CENTER: {
                return (size.getWidth() == ViewSize.MATCH_PARENT)?
                    0 : (getParent().getWidth() - this.getWidth()) / 2;
            }
            default: return result;
        }
    }
    
    /**
     * Returns the view's position inside its parent view.
     *
     * @return the relative y
     */
    public int getRelY() {
        int result = size.getY();
        switch (result) {
            case ViewSize.MIN_POS: return 0;
            case ViewSize.MAX_POS: {
                return (size.getHeight() == ViewSize.MATCH_PARENT)?
                    0 : getParent().getHeight() - this.getHeight();
            }
            case ViewSize.CENTER: {
                return (size.getHeight() == ViewSize.MATCH_PARENT)?
                    0 : (getParent().getHeight() - this.getHeight()) / 2;
            }
            default: return result;
        }
    }
    
    
    /**
     * Returns the view's width. Unless otherwise specified, the width of a view matches the width of its parent.
     *
     * @return the width
     */
    public int getWidth() {
        int result = size.getWidth();
        if (result > 0) {
            return result;
        }
        else if (result == ViewSize.WRAP_CONTENT) {
            return getContentWidth();
        }
        else if (result == ViewSize.MATCH_PARENT) {
            int parentSize = parent.size.getWidth();
            if (parentSize == ViewSize.WRAP_CONTENT) return parent.getContentWidth();
            else return parent.getWidth() - getRelX();
        }
        else throw new AssertionError(result);
    }
    
    /**
     * Returns the view's height. Unless otherwise specified, the height of a view matches the height of its parent.
     *
     * @return the width
     */
    public int getHeight() {
        int result = size.getHeight();
        if (result > 0) {
            return result;
        }
        else if (result == ViewSize.WRAP_CONTENT) {
            return getContentHeight();
        }
        else if (result == ViewSize.MATCH_PARENT) {
            int parentSize = parent.size.getHeight();
            if (parentSize == ViewSize.WRAP_CONTENT) return parent.getContentHeight();
            else return parent.getHeight() - getRelY();
        }
        else throw new AssertionError(result);
    }
    
    /**
     * Returns the area of this view.
     *
     * @return the area
     */
    public int getArea() {
        return getWidth() * getHeight();
    }
    
    /**
     * Returns the minimum width of the content of this view.
     * <p>
     * Views which have arbitrary dimensions such as {@link Pane} or {@link ProgressBar} default to 1.
     *
     * @return the minimum width
     */
    public int getContentWidth() {
        return 1;
    }
    
    /**
     * Returns the minimum height of this view.
     * <p>
     * Views which have arbitrary dimensions such as {@link Pane} or {@link ProgressBar} default to 1.
     *
     * @return the minimum height
     */
    public int getContentHeight() {
        return 1;
    }
    
    // INTERACTION
    
    /**
     * Performs an action.
     *
     * @param player the player who performs the action
     * @param action the action to perform
     */
    public MenuResponse performAction(Player player, ViewAction action) {
        return MenuResponse.BLOCK;
    }
    
    // DRAWABLE IMPL
    
    @Override
    public final void draw(IconBuffer buffer) {
        if (!isHidden())
            drawContent(buffer);
    }
    
    public IconBuffer draw() {
        IconBuffer buffer = new IconBuffer(getWidth(), getHeight());
        draw(buffer);
        return buffer;
    }
    
    @Override
    public void prepareDraw() {}
    
    /**
     * Draws the content of this view into the icon buffer.
     *
     * @param buffer the content of this view into the icon buffer
     */
    protected abstract void drawContent(IconBuffer buffer);
    
    /**
     * This method signalizes that a {@link Redrawable} is not longer valid and needs to be redrawn.
     * <p>
     * Whenever the model of a widget changes and the view requires an update, this method is to be called. A view must
     * also call this method whenever it becomes hidden or when it changes in size.
     *
     * @see #revalidate()
     */
    public void invalidate() {
        //Bukkit.broadcastMessage("invalidated: "+getClass().getSimpleName());
        if (!isInvalidated()) {
            this.invalidated = true;
            getMenu().invalidate(this);
        }
    }
    
    /**
     * This method signalizes that a {@link Redrawable} is valid again (most likely because it has been drawn).
     *
     * @see #invalidate()
     */
    public void revalidate() {
        //System.out.println(ANSI.FG_PURPLE+ANSI.BOLD+"set "+this+" to validated = "+validated+ANSI.FG_RESET+ANSI.RESET);
        //StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        //for (int i = 3; i < 6; i++) System.out.println(ANSI.FG_PURPLE+trace[i]+ANSI.FG_RESET);
        this.invalidated = false;
    }
    
    @Override
    public boolean isInvalidated() {
        return invalidated;
    }
    
    @Override
    public boolean isHidden() {
        return hidden;
    }
    
    // MUTATORS
    
    /**
     * Changes the parent of this view.
     * <p>
     * This method should not be called in regular use, the only time this method should be called is when adding this
     * view to a {@link ViewGroup} or other group of views.
     *
     * @param parent the parent
     */
    public void setParent(@NotNull View parent) {
        this.parent = parent;
    }
    
    /**
     * Sets the width of this view.
     *
     * @param width the width
     * @see ViewSize#setWidth(int)
     */
    public void setWidth(int width) {
        if (!this.size.hasStaticWidth()) {
            this.size.setWidth(width);
            this.invalidate();
            this.parent.invalidate();
        }
    }
    
    /**
     * Sets the height of this view.
     *
     * @param height the height
     * @see ViewSize#setHeight(int)
     */
    public void setHeight(int height) {
        if (!this.size.hasStaticHeight()) {
            this.size.setHeight(height);
            this.invalidate();
            this.parent.invalidate();
        }
    }
    
    public void setPosition(int x, int y) {
        this.size.setX(x);
        this.size.setY(y);
        this.invalidate();
        this.parent.invalidate();
    }
    
    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }
    
    /**
     * Sets this view to hidden.
     * <p>
     * When an element is hidden, it is being ignored in the drawing pipeline. However, hidden elements keep their
     * shape and size, they are only invisible inside the menu.
     *
     * @param hidden whether the element should be hidden
     */
    public void setHidden(boolean hidden) {
        //System.out.println("set " + getClass().getSimpleName() + " to hidden = " + hidden);
        if (this.hidden != hidden) {
            this.hidden = hidden;
            this.invalidate();
        }
    }
    
    @Override
    public String toString() {
        String path;
        if (getParent() != this)
            path = getParent().toString() + " / " + getClass().getSimpleName();
        else
            path = getClass().getSimpleName();
        
        return invalidated? path + "?" : path;
    }
    
}
