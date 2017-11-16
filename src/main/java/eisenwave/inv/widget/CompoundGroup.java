package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.view.*;
import org.jetbrains.annotations.NotNull;

/**
 * A group of views which acts as a single compound widget.
 * <p>
 * This view can be used to more easily create widgets which are a combination of various other, arbitrarily arranged
 * widgets.
 * <p>
 * An example of this would be a {@link Button} paired with a {@link Display} that describes it.
 */
public class CompoundGroup extends ViewGroup<View> {
    
    public CompoundGroup(@NotNull Menu menu) {
        super(menu.getContentPane(), menu, new ViewSize(ViewSize.WRAP_CONTENT, ViewSize.WRAP_CONTENT, true, true));
    }
    
    @Override
    public int getContentWidth() {
        int min = 0, max = 0;
        for (View view : children) {
            int offset = view.getRelX();
            min = Math.min(min, offset);
            max = Math.max(max, offset);
        }
        return max - min + 1;
    }
    
    @Override
    public int getContentHeight() {
        int min = 0, max = 0;
        for (View view : children) {
            int offset = view.getRelY();
            min = Math.min(min, offset);
            max = Math.max(max, offset);
        }
        return max - min + 1;
    }
    
}
