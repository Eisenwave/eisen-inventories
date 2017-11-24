package eisenwave.inv.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public final class ItemInitUtil {
    
    private ItemInitUtil() {}
    
    // CREATE
    
    @NotNull
    public static ItemStack create(Material material, String name) {
        return create(material, 1, (short) 0, name, (List) null);
    }
    
    @NotNull
    public static ItemStack create(Material material, String name, List<String> lore) {
        return create(material, 1, (short) 0, name, lore);
    }
    
    @NotNull
    public static ItemStack create(Material material, String name, String inlineLore) {
        return create(material, 1, (short) 0, name, inlineLore);
    }
    
    @NotNull
    public static ItemStack create(Material material, int count, String name) {
        return create(material, count, (short) 0, name, (List) null);
    }
    
    @NotNull
    public static ItemStack create(Material material, short damage, String name) {
        return create(material, 1, damage, name, (List) null);
    }
    
    @NotNull
    public static ItemStack create(Material material, int count, short damage, @Nullable String name) {
        return create(material, count, damage, name, (List) null);
    }
    
    @NotNull
    public static ItemStack create(Material material, int count, short damage,
                                   @Nullable String name,
                                   @Nullable String inlineLore) {
        List<String> lore = inlineLore == null? Collections.emptyList() : decodeInlineLore(inlineLore);
        return create(material, count, damage, name, lore);
    }
    
    @NotNull
    public static ItemStack create(Material material, int count, short damage,
                                   @Nullable String name,
                                   @Nullable List<String> lore) {
        ItemStack item = new ItemStack(material, count, damage);
        ItemMeta meta = item.getItemMeta();
        if (name != null)
            meta.setDisplayName(name);
        if (lore != null) {
            lore = lore.stream()
                .map(str -> ChatColor.translateAlternateColorCodes('&', str))
                .collect(Collectors.toList());
            meta.setLore(lore);
        }
        
        item.setItemMeta(meta);
        return item;
    }
    
    // SET
    
    /**
     * Sets the {@link #decodeInlineLore(String) inline lore} of the item.
     *
     * @param stack the item stack
     * @param lore the inline lore
     * @return the given item stack
     */
    public static ItemStack setInlineLore(ItemStack stack, String lore) {
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(decodeInlineLore(lore));
        stack.setItemMeta(meta);
        return stack;
    }
    
    public static ItemStack setLore(ItemStack stack, String... lore) {
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);
        return stack;
    }
    
    public static ItemStack setLore(ItemStack stack, String lore) {
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(Collections.singletonList(lore));
        stack.setItemMeta(meta);
        return stack;
    }
    
    /**
     * Returns a new item with the given {@link #decodeInlineLore(String) inline lore}.
     *
     * @param stack the item stack
     * @param lore the inline lore
     * @return the new item stack
     */
    public static ItemStack withInlineLore(ItemStack stack, String lore) {
        return setInlineLore(stack.clone(), lore);
    }
    
    public static ItemStack withLore(ItemStack stack, String... lore) {
        return setLore(stack.clone(), lore);
    }
    
    public static ItemStack withLore(ItemStack stack, String lore) {
        return setLore(stack.clone(), lore);
    }
    
    public static ItemStack setName(ItemStack stack, String name) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return stack;
    }
    
    public static ItemStack withName(ItemStack stack, String name) {
        return setName(stack.clone(), name);
    }
    
    // DECODE
    
    /**
     * Decodes inline lore.
     * <p>
     * Inline lore is a way of specifying the complete lore of an item in just a single line. Two achieve th is: <ol>
     * <li>The alternative color code {@code &} is supported</li>
     * <li>Lore is split at line feed characters ({@code \n})</li>
     * </ol>
     *
     * @param lore the inline lore
     * @return a list of strings representing the complete lore
     */
    public static List<String> decodeInlineLore(String lore) {
        return Arrays.stream(lore.split("\n"))
            .map(str -> ChatColor.translateAlternateColorCodes('&', str))
            .collect(Collectors.toList());
    }
    
}
