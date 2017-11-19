package eisenwave.inv.style;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import eisenwave.inv.error.UnknownAttributeException;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the style of widgets.
 * <p>
 * View styles are intended to work as immutable lookup tables for items and other information necessary to display an
 * icon. Each widget should implement its own style.
 */
public class Stylesheet {
    
    @Nullable
    private final Stylesheet parent;
    private final Map<String, ItemStack> icons = new HashMap<>();
    
    public Stylesheet(@Nullable Stylesheet parent) {
        this.parent = parent;
    }
    
    public Stylesheet() {
        this(null);
    }
    
    // PROTECTED
    
    /**
     * Defines a default item.
     * <p>
     * Defining a default item makes it possible to retrieve it using {@link #getItem(String)} or to replace it using
     * {@link #setItem(String, ItemStack)}.
     * <p>
     * If the item has a parent, this will not set the item of this style but declare, that the parent's item should
     * be used.
     *
     * @param key the item name
     * @param defaultItem the default item value
     */
    protected final void defineItem(String key, @NotNull ItemStack defaultItem) {
        if (parent != null && parent.hasItem(key))
            icons.put(key, null);
        else
            icons.put(key, defaultItem);
    }
    
    // PUBLIC
    
    /**
     * Returns the parent style that this style inherits from or null if has none.
     *
     * @return the parent style or null
     */
    @Contract(pure = true)
    public Stylesheet getParent() {
        return parent;
    }
    
    /**
     * Returns whether the style defines an item with a given name. For the style of each widget this will always return
     * the same result for the same names.
     * <p>
     * If this method returns {@code true}, {@link #getItem(String)} will not fail.
     *
     * @param key the name
     * @return whether the style defines an item of given name
     */
    @Contract(pure = true)
    public boolean hasItem(String key) {
        return icons.containsKey(key) || parent != null && parent.hasItem(key);
    }
    
    /**
     * Returns an item with a given name.
     *
     * @param key the icon name
     * @return the icon
     * @throws UnknownAttributeException if the given style does not have an attribute of given name
     */
    @NotNull
    public ItemStack getItem(String key) {
        if (icons.containsKey(key)) {
            ItemStack result = icons.get(key);
            if (result == null) {
                if (parent != null)
                    return parent.getItem(key);
                else
                    throw new UnknownAttributeException(key);
            }
            return result;
        } else if (parent != null)
            return parent.getItem(key);
        else
            throw new UnknownAttributeException(key);
    }
    
    /**
     * Sets an item with a given name.
     *
     * @param key the icon name
     * @param item the item
     * @return the previous item for the same key
     * @throws UnknownAttributeException if the given style does not have an attribute of given name
     */
    @NotNull
    public ItemStack setItem(String key, @NotNull ItemStack item) {
        if (hasItem(key))
            return icons.put(key, item);
        else
            throw new UnknownAttributeException(key);
    }
    
}
