package eisenwave.inv.widget;

import eisenwave.inv.event.CheckEvent;
import eisenwave.inv.event.CheckListener;
import eisenwave.inv.menu.Menu;
import eisenwave.inv.menu.MenuResponse;
import eisenwave.inv.view.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * A radio button is a two-states button that can be either checked or unchecked. When the radio button is unchecked,
 * the user can press or click it to check it. However, contrary to a CheckBox, a radio button cannot be unchecked by
 * the user once checked.
 * <p>
 * Radio buttons are normally used together in a RadioGroup. When several radio buttons live inside a radio group,
 * checking one radio button unchecks all the others.
 */
public class RadioButton extends View implements Checkable {
    
    private final ItemStack checkedItem, uncheckedItem;
    
    private final Set<CheckListener> listeners = new HashSet<>();
    
    private boolean checked = false;
    
    public RadioButton(@NotNull Menu menu, @Nullable ViewStyle style) {
        super(menu.getContentPane(), menu, new Style(style), new ViewSize(1, 1, true, true));
        this.checkedItem = getStyle().getItem("radio.checked");
        this.uncheckedItem = getStyle().getItem("radio.unchecked");
    }
    
    @Override
    public void drawContent(IconBuffer buffer) {
        //System.out.println("drawing "+this+" as "+checked);
        buffer.set(0, 0, new Icon(this, checked? checkedItem : uncheckedItem));
    }
    
    @Override
    public boolean isChecked() {
        return checked;
    }
    
    @Override
    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;
            //System.out.println("invalidation call on "+this+" when invalidated = "+isInvalidated());
            this.invalidate();
        }
    }
    
    @Override
    public void toggle() {
        setChecked(true);
    }
    
    @Override
    public MenuResponse performAction(Player player, ViewAction action) {
        boolean before = checked;
        if (action.getType() == ViewActionType.CLICK)
            performToggle(player);
        return this.checked != before? MenuResponse.OK : MenuResponse.BLOCK;
    }
    
    @Override
    public void performToggle(Player player) {
        CheckEvent action = new CheckEvent(this, player, !checked, checked);
        for (CheckListener listener : listeners)
            listener.onCheck(action);
        this.toggle();
    }
    
    @Override
    public void addCheckListener(CheckListener listener) {
        listeners.add(listener);
    }
    
    @Override
    public void removeCheckListener(CheckListener listener) {
        listeners.remove(listener);
    }
    
    // STYLE
    
    /**
     * Returns a copy of this button's checked item.
     *
     * @return a copy of this button's checked item.
     */
    public ItemStack getCheckedItem() {
        return checkedItem.clone();
    }
    
    /**
     * Returns a copy of this button's unchecked item.
     *
     * @return a copy of this button's checked item.
     */
    public ItemStack getUncheckedItem() {
        return uncheckedItem.clone();
    }
    
    private static class Style extends ViewStyle {
        
        public Style(@Nullable ViewStyle parent) {
            super(parent);
            defineItem("radio.checked", _DefaultStyles.RADIO_BUTTON_CHECKED);
            defineItem("radio.unchecked", _DefaultStyles.RADIO_BUTTON_UNCHECKED);
        }
        
    }
    
}
