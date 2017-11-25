package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.menu.MenuResponse;
import eisenwave.inv.style.Stylesheet;
import eisenwave.inv.util.ItemInitUtil;
import eisenwave.inv.view.*;
import net.grian.spatium.util.PrimMath;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Switch extends Widget {
    
    public final static int X = -5 % 5;
    
    private ItemStack item = _DefaultStyles.SWITCH;
    
    private boolean changeStack = true;
    private int index = 0;
    private String[] options;
    
    public Switch(Menu menu, @Nullable Stylesheet style, List<String> options) {
        super(menu, ViewSize.singleton(), style);
        if (options.isEmpty())
            throw new IllegalArgumentException("options list must not be empty");
        this.options = options.toArray(new String[options.size()]);
        setIndex(0);
    }
    
    public Switch(Menu menu, @Nullable Stylesheet style, String... options) {
        this(menu, style, Arrays.asList(options));
    }
    
    // ACTIONS
    
    @Override
    public MenuResponse performAction(Player player, ViewAction action) {
        if (action.getType() == ViewActionType.CLICK) {
            ClickType clickType = action.getClickType();
            
            if (clickType.isLeftClick()) {
                nextOption();
                return MenuResponse.OK;
            }
            else if (clickType.isRightClick()) {
                previousOption();
                return MenuResponse.OK;
            }
            else if (clickType == ClickType.MIDDLE) {
                return setIndex(options.length - 1)? MenuResponse.OK : MenuResponse.BLOCK;
            }
            else if (clickType == ClickType.DROP || clickType == ClickType.CONTROL_DROP) {
                return setIndex(0)? MenuResponse.OK : MenuResponse.BLOCK;
            }
        }
        return MenuResponse.BLOCK;
    }
    
    
    // GETTERS
    
    /**
     * Returns the index of the current option.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * Returns the current option.
     *
     * @return the current option
     */
    public String getCurrent() {
        return options[index];
    }
    
    /**
     * Returns whether the number picker is changing the size of its {@link ItemStack item stack}.
     *
     * @return whether the stack is being set
     */
    public boolean getChangeAmount() {
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
     * Sets the current index.
     *
     * @param index the index
     * @return true if the index changed, else false
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public boolean setIndex(int index) {
        if (this.index != index) {
            internalSetIndex(index);
            return true;
        }
        return false;
    }
    
    /**
     * Increments the index.
     */
    public void nextOption() {
        internalSetIndex((index + 1) % options.length);
    }
    
    /**
     * Decrements the index.
     */
    public void previousOption() {
        internalSetIndex(index == 0? options.length - 1 : index - 1);
    }
    
    private void internalSetIndex(int number) {
        this.index = number;
        this.invalidate();
    }
    
    /**
     * Sets whether the number picker should change the amount of items on its stack when its number changes.
     * <p>
     * <b>WARNING: </b>This can lead to unwanted effects if the amount of options is greater than {@code 64}.
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
        String name = ChatColor.GREEN.toString() + options[index];
        int lim = options.length;
        String[] lore = new String[lim];
        
        for (int i = 0; i < lore.length; i++) {
            ChatColor color = index == i? ChatColor.WHITE : ChatColor.DARK_GRAY;
            lore[i] = color + options[i];
        }
        
        ItemInitUtil.setName(item, name);
        ItemInitUtil.setLore(item, lore);
        
        if (getChangeAmount())
            item.setAmount(PrimMath.clamp(1, index + 1, 64));
    }
    
    @Override
    protected void drawContent(IconBuffer buffer) {
        buffer.set(0, 0, new Icon(this, item));
    }
    
}
