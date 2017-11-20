package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.style.Stylesheet;
import eisenwave.inv.view.View;
import eisenwave.inv.view.ViewSize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Widget extends View {
    
    private final Stylesheet style;
    
    /**
     * Constructs a new widget.
     *
     * @param menu the menu
     * @param style the style of this view
     * @see Menu#getContentPane()
     */
    protected Widget(Menu menu, @NotNull ViewSize size, @Nullable Stylesheet style) {
        super(menu.getContentPane(), menu, size);
        this.style = style;
    }
    
    /**
     * Constructs a new widget.
     *
     * @param menu the menu
     * @param style the style of this view
     * @see Menu#getContentPane()
     */
    protected Widget(Menu menu, @Nullable Stylesheet style) {
        super(menu);
        this.style = style;
    }
    
    /**
     * Returns the style of this view.
     *
     * @return the style
     */
    protected Stylesheet getStyle() {
        return style;
    }
    
}
