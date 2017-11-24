package eisenwave.inv.widget;

import eisenwave.commons.util.PrimMath;
import eisenwave.inv.menu.Menu;
import eisenwave.inv.style.Stylesheet;
import eisenwave.inv.view.IconBuffer;
import eisenwave.inv.view.ViewSize;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class NumberPicker extends Widget {
    
    private ItemStack item;
    
    private boolean changeStack;
    private int number = 0, min = 1, max = 64;
    
    public NumberPicker(Menu menu, @Nullable Stylesheet style) {
        super(menu, ViewSize.singleton(), style);
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
        return item;
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
        number = PrimMath.clamp(min, number, max);
        if (this.number != number) {
            this.number = number;
            this.invalidate();
        }
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
    }
    
    // ACTIONS
    
    @Override
    protected void drawContent(IconBuffer buffer) {
    
    }
    
}
