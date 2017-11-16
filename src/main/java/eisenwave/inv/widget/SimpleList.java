package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.view.IconBuffer;
import eisenwave.inv.view.View;
import eisenwave.inv.view.ViewGroup;
import eisenwave.inv.view.ViewSize;
import net.grian.spatium.util.Incrementer2;
import org.jetbrains.annotations.NotNull;

public class SimpleList<T extends View> extends ViewGroup<T> {
    
    private boolean horizontal = true;
    
    public SimpleList(@NotNull Menu menu) {
        super(menu.getContentPane(), menu, new ViewSize(ViewSize.MATCH_PARENT, ViewSize.MATCH_PARENT));
    }
    
    public boolean isVertical() {
        return !horizontal;
    }
    
    public void setVertical(boolean vertical) {
        this.horizontal = !vertical;
    }
    
    @Override
    public void drawContent(IconBuffer buffer) {
        Incrementer2 incr = horizontal?
            new Incrementer2(getWidth(), getHeight()) :
            new Incrementer2(getHeight(), getWidth());
        
        for (T child : children) {
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
