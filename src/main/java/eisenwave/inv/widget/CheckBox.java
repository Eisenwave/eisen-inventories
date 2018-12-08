package eisenwave.inv.widget;

import eisenwave.inv.event.*;
import eisenwave.inv.menu.Menu;
import eisenwave.inv.menu.MenuResponse;
import eisenwave.inv.style.Stylesheet;
import eisenwave.inv.view.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class CheckBox extends Widget implements Checkable {
    
    private ItemStack checkedItem, uncheckedItem;
    
    private final Set<CheckListener> listeners = new HashSet<>();
    
    private boolean checked = false;
    
    public CheckBox(@NotNull Menu menu, @Nullable Stylesheet style) {
        super(menu, new ViewSize(1, 1, true, true), new Style(style));
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
        CheckEvent action = new CheckEvent(this, player, !checked);
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
    
    /**
     * Sets the checked-state item.
     *
     * @param item the item
     */
    public void setCheckedItem(ItemStack item) {
        this.checkedItem = item.clone();
        this.invalidate();
    }
    
    /**
     * Sets the unchecked-state item.
     *
     * @param item the item
     */
    public void setUncheckedItem(ItemStack item) {
        this.uncheckedItem = item.clone();
        this.invalidate();
    }
    
    private static class Style extends Stylesheet {
        
        public Style(@Nullable Stylesheet parent) {
            super(parent);
            defineItem("checkbox.unchecked", _DefaultStyles.CHECKBOX_UNCHECKED);
            defineItem("checkbox.checked", _DefaultStyles.CHECKBOX_CHECKED);
        }
        
    }
    
}
