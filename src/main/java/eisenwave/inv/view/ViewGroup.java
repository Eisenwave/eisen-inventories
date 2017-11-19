package eisenwave.inv.view;

import eisenwave.inv.menu.Menu;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * A basic layout which implements all the logic for handling multiple view but does not arrange them in any special
 * manner.
 * <p>
 * Adding {@link View}s to this object will result in them being hidden, drawn and invalidated in sync with their
 * parent.
 */
public abstract class ViewGroup<T extends View> extends View implements Iterable<T> {
    
    protected final List<T> children = new ArrayList<>();
    
    protected ViewGroup(View parent, Menu menu, @NotNull ViewSize size) {
        super(parent, menu, size);
    }
    
    public ViewGroup(@NotNull Menu menu) {
        this(menu.getContentPane(), menu, new ViewSize(ViewSize.MATCH_PARENT, ViewSize.MATCH_PARENT));
    }
    
    public ViewGroup(@NotNull View parent) {
        this(parent, parent.getMenu(), new ViewSize(ViewSize.MATCH_PARENT, ViewSize.MATCH_PARENT));
    }
    
    /**
     * Adds a child to this view group.
     * <p>
     * This sets the parent of the child to this view group.
     *
     * @param child the child
     */
    public void addChild(T child) {
        this.children.add(child);
        child.setParent(this);
        this.invalidate();
    }
    
    /**
     * Removes a child from this view group.
     * <p>
     * This sets the parent of the child to be the parent of this view.
     *
     * @param child the child to remove
     */
    public void removeChild(T child) {
        this.children.remove(child);
        this.invalidate();
    }
    
    /**
     * Removes all children from this view group.
     *
     * @see #removeChild(View)
     */
    public void clearChildren() {
        //View parent = getParent();
        //children.forEach(child -> child.setParent(parent));
        children.clear();
        this.invalidate();
    }
    
    @Override
    protected void drawContent(IconBuffer buffer) {
        for (View child : children) {
            //if (!child.isInvalidated()) continue;
            System.out.println("drawing " + child.getClass().getSimpleName() + " inside view group");
            if (!child.isHidden()) {
                IconBuffer childBuffer = new IconBuffer(child.getWidth(), child.getHeight());
                child.draw(childBuffer);
                buffer.set(child.getRelX(), child.getRelY(), childBuffer);
            }
            child.revalidate();
        }
    }
    
    /*
    @Override
    public void invalidate() {
        if (!isInvalidated()) {
            super.invalidate();
            //forEach(View::invalidate);
        }
    }
    
    @Override
    public void setHidden(boolean hidden) {
        if (hidden != isHidden()) {
            super.setHidden(hidden);
            //forEach(child -> child.setHidden(hidden));
        }
    }
    */
    
    // ITERATION
    
    @Override
    public void forEach(Consumer<? super T> action) {
        children.forEach(action);
    }
    
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return children.iterator();
    }
    
}
