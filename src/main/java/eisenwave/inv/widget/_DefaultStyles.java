package eisenwave.inv.widget;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

final class _DefaultStyles {
    
    public final static ItemStack
        BUTTON_ITEM = itemStack(Material.EMERALD, "&eButton"),
        CHECKBOX_UNCHECKED = itemStack(Material.FIREWORK_CHARGE, "&8Unchecked"),
        CHECKBOX_CHECKED = itemStack(Material.SLIME_BALL, "&aChecked"),
        DISPLAY_ITEM = new ItemStack(Material.PAPER),
        RADIO_BUTTON_CHECKED = new ItemStack(Material.INK_SACK, 1, (short) 10),
        RADIO_BUTTON_UNCHECKED = new ItemStack(Material.INK_SACK, 1, (short) 8),
        PANE_ITEM = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15),
        PROGRESSBAR_ON = new ItemStack(Material.CONCRETE, 1, (short) 5),
        PROGRESSBAR_OFF = new ItemStack(Material.CONCRETE, 1, (short) 14);
    
    private static ItemStack itemStack(Material material, String colName) {
        ItemStack result = new ItemStack(material);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', colName));
        result.setItemMeta(meta);
        return result;
    }
    
}
