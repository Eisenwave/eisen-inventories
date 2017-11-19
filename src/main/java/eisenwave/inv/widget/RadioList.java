package eisenwave.inv.widget;

import eisenwave.inv.event.CheckListener;
import eisenwave.inv.menu.Menu;
import eisenwave.inv.view.ViewSize;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RadioList extends SimpleList<RadioButton> {
    
    private final Map<RadioButton, CheckListener> listenerMap = new HashMap<>();
    
    private int picked;
    
    public RadioList(@NotNull Menu menu, @NotNull ViewSize size) {
        super(menu, size);
    }
    
    /**
     * Returns the index of the currently picked radio button in this group.
     *
     * @return the currently picked index
     */
    public int getPicked() {
        return picked;
    }
    
    /**
     * Picks a radio button with given index.
     *
     * @param index the index
     */
    public void pick(int index) {
        //System.out.println("picking: picked = " + picked + ", index = " + index);
        if (picked == index) return;
        
        final int size = children.size();
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException(Integer.toString(index));
        
        for (int i = 0; i < size; i++) {
            RadioButton button = children.get(i);
            button.setChecked(i == index);
            //System.out.println("set "+children.get(i)+" #" + i + " to " + (i == index));
            //System.out.println("invalidation of the button = "+button.isInvalidated());
        }
        
        //System.out.println(ANSI.FG_GREEN+"radio list invalidated = "+this.isInvalidated()+ANSI.FG_RESET);
        picked = index;
    }
    
    @Override
    public void addChild(RadioButton child) {
        int index = children.size();
        super.addChild(child);
        CheckListener listener = event -> pick(index);
        //System.out.println("adding radio button with index " + index);
        child.addCheckListener(listener);
        listenerMap.put(child, listener);
    }
    
    @Override
    public void removeChild(RadioButton child) {
        CheckListener listener = listenerMap.remove(child);
        child.removeCheckListener(listener);
        super.removeChild(child);
    }
    
    @Override
    public void clearChildren() {
        for (RadioButton child : children)
            child.removeCheckListener(listenerMap.remove(child));
        listenerMap.clear();
        super.clearChildren();
    }
    
}
