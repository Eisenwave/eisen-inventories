package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.menu.MenuResponse;
import eisenwave.inv.style.Stylesheet;
import eisenwave.inv.util.EisenInvUtil;
import eisenwave.inv.util.ItemUtil;
import eisenwave.inv.view.*;
import net.grian.spatium.util.PrimMath;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class NumberPicker extends Widget {
    
    private ItemStack item = _DefaultStyles.NUMBER_PICKER;
    
    private boolean changeStack = true;
    private int number, min = 1, max = 64;
    
    public NumberPicker(Menu menu, @Nullable Stylesheet style) {
        super(menu, ViewSize.singleton(), style);
        setNumber(1);
    }
    
    // ACTIONS
    
    @Override
    public MenuResponse performAction(Player player, ViewAction action) {
        if (action.getType() == ViewActionType.CLICK) {
            ClickType clickType = action.getClickType();
            
            if (clickType.isLeftClick()) {
                return increment()? MenuResponse.OK : MenuResponse.BLOCK;
            }
            else if (clickType.isRightClick()) {
                return decrement()? MenuResponse.OK : MenuResponse.BLOCK;
            }
            else if (clickType == ClickType.MIDDLE) {
                setNumber(getMax());
                return MenuResponse.OK;
            }
            else if (clickType == ClickType.DROP || clickType == ClickType.CONTROL_DROP) {
                setNumber(getMin());
                return MenuResponse.OK;
            }
        }
        return MenuResponse.BLOCK;
    }
    
    
    // GETTERS
    
    /**
     * Returns the minimum.
     *
     * @return the minimum
     */
    public int getMin() {
        return min;
    }
    
    /**
     * Returns the maximum.
     *
     * @return the maximum
     */
    public int getMax() {
        return max;
    }
    
    /**
     * Returns the current number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Returns whether the number picker is changing the size of its {@link org.bukkit.inventory.ItemStack item stack}.
     *
     * @return whether the stack is being set
     */
    public boolean isChangingStack() {
        return changeStack;
    }
    
    /**
     * Returns the item of this number picker.
     *
     * @return the item
     */
    public ItemStack getItem() {
        return item.clone();
    }
    
    // MUTATORS
    
    /**
     * Sets the minimum.
     *
     * @param min the minimum
     */
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     * Sets the maximum
     *
     * @param max the maximum
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * Sets both the minimum and the maximum of this number.
     *
     * @param min the minimum
     * @param max the maximum
     * @throws IllegalArgumentException if min > max
     */
    public void setRange(int min, int max) {
        if (min > max) throw new IllegalArgumentException("min > max");
        this.min = min;
        this.max = max;
    }
    
    /**
     * Sets the current number.
     *
     * @param number the number
     */
    public void setNumber(int number) {
        number = EisenInvUtil.clamp(min, number, max);
        if (this.number != number) {
            internalSetNumber(number);
        }
    }
    
    /**
     * Increments the number.
     *
     * @return true if the number was changed, else false
     */
    public boolean increment() {
        if (number < max) {
            internalSetNumber(number + 1);
            return true;
        }
        else return false;
    }
    
    /**
     * Decrements the number.
     *
     * @return true if the number was changed, else false
     */
    public boolean decrement() {
        if (number > min) {
            internalSetNumber(number - 1);
            return true;
        }
        else return false;
    }
    
    private void internalSetNumber(int number) {
        this.number = number;
        this.invalidate();
    }
    
    /**
     * Sets whether the number picker should change the amount of items on its stack when its number changes.
     * <p>
     * <b>WARNING: </b>This can lead to unwanted effects if the {@link #setMin(int) minimum} is smaller than 1 and the
     * {@link #setMax(int) maximum} is greater than 64.
     *
     * @param changeStack whether the stack amount shit be changed
     */
    public void setChangeStack(boolean changeStack) {
        this.changeStack = changeStack;
    }
    
    /**
     * Sets the item of this number picker.
     *
     * @param item the item
     */
    public void setItem(ItemStack item) {
        this.item = item.clone();
        this.invalidate();
    }
    
    // ACTIONS
    
    @Override
    public void prepareDraw() {
        String name = ChatColor.GREEN.toString() + number;
        String suffix = number == min? " (min)" : number == max? " (max)" : "";
        ItemUtil.setName(item, name + suffix);
        
        if (isChangingStack())
            item.setAmount(PrimMath.clamp(1, number, 64));
    }
    
    @Override
    protected void drawContent(IconBuffer buffer) {
        buffer.set(0, 0, new Icon(this, item));
    }
    
}
