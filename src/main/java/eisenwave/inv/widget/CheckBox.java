package eisenwave.inv.widget;

import eisenwave.inv.event.*;
import eisenwave.inv.menu.Menu;
import eisenwave.inv.menu.MenuResponse;
import eisenwave.inv.view.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class CheckBox extends View implements Checkable {
    
    private final ItemStack checkedItem, uncheckedItem;
    
    private final Set<CheckListener> listeners = new HashSet<>();
    
    private boolean checked = false;
    
    public CheckBox(@NotNull Menu menu, @Nullable ViewStyle style) {
        super(menu.getContentPane(), menu, new Style(style), new ViewSize(1, 1, true, true));
        this.checkedItem = getStyle().getItem("checkbox.checked");
        this.uncheckedItem = getStyle().getItem("checkbox.unchecked");
    }
    
    @Override
    public boolean isChecked() {
        return checked;
    }
    
    @Override
    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;
            this.invalidate();
        }
    }
    
    @Override
    public void toggle() {
        setChecked(!checked);
    }
    
    @Override
    public MenuResponse performAction(Player player, ViewAction action) {
        //player.sendMessage(action.getClickType().name());
        if (action.getType() == ViewActionType.CLICK)
            performToggle(player);
        return MenuResponse.OK;
    }
    
    @Override
    public void performToggle(Player player) {
        System.out.println("performing toggle");
        CheckEvent action = new CheckEvent(this, player, checked, !checked);
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
    
    @Override
    public void drawContent(IconBuffer buffer) {
        //System.out.println("drawing checkbox with checked state: "+checked);
        buffer.set(0, 0, new Icon(this, checked? checkedItem : uncheckedItem));
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
            defineItem("checkbox.unchecked", _DefaultStyles.CHECKBOX_UNCHECKED);
            defineItem("checkbox.checked", _DefaultStyles.CHECKBOX_CHECKED);
        }
        
    }
    
}
