package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.view.*;
import org.jetbrains.annotations.NotNull;

public final class MenuPane extends ViewGroup<View> {
    
    public MenuPane(Menu menu) {
        super(null, menu, new ViewSize(0, 0, menu.getWidth(), menu.getHeight(), true, true));
    }
    
    @Override
    public int getX() {
        return 0;
    }
    
    @Override
    public int getY() {
        return 0;
    }
    
    @Override
    public int getContentWidth() {
        return getMenu().getWidth();
    }
    
    public int getContentHeight() {
        return getMenu().getHeight();
    }
    
    @NotNull
    @Override
    public View getParent() {
        return this;
    }
    
}
