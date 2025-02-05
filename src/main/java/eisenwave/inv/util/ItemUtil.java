package eisenwave.inv.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public final class ItemUtil {
    
    private ItemUtil() {}
    
    // CREATE
    
    @NotNull
    public static ItemStack create(String key) {
        return LegacyUtil.createItemStack(key, 1);
    }
    
    @NotNull
    public static ItemStack create(String key, int count) {
        return LegacyUtil.createItemStack(key, count);
    }
    
    @NotNull
    public static ItemStack create(String key, String name) {
        return create(key, 1, name, (List) null);
    }
    
    @NotNull
    public static ItemStack create(Material mat, String name) {
        return create(mat, 1, name, null);
    }
    
    @NotNull
    public static ItemStack create(String key, String name, String inlineLore) {
        return create(key, 1, name, inlineLore);
    }
    
    @NotNull
    public static ItemStack create(String key, int count, String name) {
        return create(key, count, name, (List) null);
    }
    
    @NotNull
    public static ItemStack create(String key, int count,
                                   @Nullable String name,
                                   @Nullable String inlineLore) {
        List<String> lore = inlineLore == null? Collections.emptyList() : decodeInlineLore(inlineLore);
        return create(key, count, name, lore);
    }
    
    @NotNull
    public static ItemStack create(String key, int count,
                                   @Nullable String name,
                                   @Nullable List<String> lore) {
        ItemStack item = LegacyUtil.createItemStack(key, count);
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
    
    @NotNull
    public static ItemStack create(Material mat, int count,
                                   @Nullable String name,
                                   @Nullable List<String> lore) {
        ItemStack item = new ItemStack(mat, count);
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
    
    // MUTATE
    
    public static ItemStack hideAttributes(ItemStack stack) {
        return changeMeta(stack, meta -> meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES));
    }
    
    /**
     * Sets the {@link #decodeInlineLore(String) inline lore} of the item.
     *
     * @param stack the item stack
     * @param lore the inline lore
     * @return the given item stack
     */
    public static ItemStack setInlineLore(ItemStack stack, String lore) {
        return changeMeta(stack, meta -> meta.setLore(decodeInlineLore(lore)));
    }
    
    public static ItemStack setLore(ItemStack stack, String... lore) {
        return changeMeta(stack, meta -> meta.setLore(Arrays.asList(lore)));
    }
    
    public static ItemStack setLore(ItemStack stack, String lore) {
        return changeMeta(stack, meta -> meta.setLore(Collections.singletonList(lore)));
    }
    
    public static ItemStack setName(ItemStack stack, String name) {
        return changeMeta(stack, meta -> meta.setDisplayName(name));
    }
    
    public static ItemStack addItemFlags(ItemStack stack, ItemFlag... flags) {
        return changeMeta(stack, meta -> meta.addItemFlags(flags));
    }
    
    // MUTATE & COPY
    
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
    
    public static ItemStack withName(ItemStack stack, String name) {
        return setName(stack.clone(), name);
    }
    
    public static ItemStack withItemFlags(ItemStack item, ItemFlag... flags) {
        return addItemFlags(item.clone(), flags);
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
    
    // UTIL
    
    private static ItemStack changeMeta(ItemStack stack, Consumer<ItemMeta> action) {
        ItemMeta meta = stack.getItemMeta();
        action.accept(meta);
        stack.setItemMeta(meta);
        return stack;
    }
    
}
