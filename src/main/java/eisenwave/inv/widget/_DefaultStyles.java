package eisenwave.inv.widget;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;

import static eisenwave.inv.util.ItemInitUtil.*;

final class _DefaultStyles {
    
    public final static ItemStack
        BUTTON_ITEM = create(Material.EMERALD, ChatColor.YELLOW + "Button"),
        CHECKBOX_CHECKED = create(Material.SLIME_BALL, ChatColor.GREEN + "Checked"),
        CHECKBOX_UNCHECKED = create(Material.FIREWORK_CHARGE, ChatColor.DARK_GRAY + "Unchecked"),
        DISPLAY_ITEM = new ItemStack(Material.PAPER),
        RADIO_BUTTON_CHECKED = create(Material.INK_SACK, 1, (short) 10, ChatColor.GREEN + "Checked"),
        RADIO_BUTTON_UNCHECKED = create(Material.INK_SACK, 1, (short) 8, ChatColor.DARK_GRAY + "Unchecked"),
        PANE_ITEM = create(Material.STAINED_GLASS_PANE, 1, (short) 15, " "),
        PROGRESSBAR_ON = new ItemStack(Material.CONCRETE, 1, (short) 5),
        PROGRESSBAR_OFF = new ItemStack(Material.CONCRETE, 1, (short) 14);
    
}
