package eisenwave.inv.widget;

import org.bukkit.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static eisenwave.inv.util.ItemUtil.*;

final class _DefaultStyles {
    
    public final static ItemStack
        BUTTON_ITEM = create(Material.EMERALD, ChatColor.YELLOW + "Button"),
        CHECKBOX_CHECKED = create(Material.SLIME_BALL, ChatColor.GREEN + "Checked"),
        CHECKBOX_UNCHECKED = create(Material.FIREWORK_CHARGE, ChatColor.DARK_GRAY + "Unchecked"),
        DISPLAY_ITEM = new ItemStack(Material.PAINTING),
        NAV_BUTTON_L_OFF = create(Material.ARROW, ChatColor.DARK_GRAY + "<<<"),
        NAV_BUTTON_R_OFF = create(Material.ARROW, ChatColor.DARK_GRAY + ">>>"),
        NAV_BUTTON_L_ON = create(Material.SPECTRAL_ARROW, ChatColor.YELLOW + ChatColor.BOLD.toString() + "<<<",
            "&7Left-Click:\n&8scroll multiple\n&7Right-Click:\n&8scroll one\n&7Middle-Click:\n&8go to start"),
        NAV_BUTTON_R_ON = create(Material.SPECTRAL_ARROW, ChatColor.YELLOW + ChatColor.BOLD.toString() + ">>>",
            "&7Left-Click:\n&8scroll multiple\n&7Right-Click:\n&8scroll one\n&7Middle-Click:\n&8go to end"),
        RADIO_BUTTON_CHECKED = create(Material.INK_SACK, 1, (short) 10, ChatColor.GREEN + "Checked"),
        RADIO_BUTTON_UNCHECKED = create(Material.INK_SACK, 1, (short) 8, ChatColor.DARK_GRAY + "Unchecked"),
        PANE_ITEM = create(Material.STAINED_GLASS_PANE, 1, (short) 15, " "),
        PROGRESSBAR_ON = create(Material.STAINED_GLASS_PANE, 1, (short) 5, " "),
        PROGRESSBAR_OFF = create(Material.STAINED_GLASS_PANE, 1, (short) 15, " "),
        NUMBER_PICKER = new ItemStack(Material.PRISMARINE_CRYSTALS),
        SWITCH = new ItemStack(Material.PAPER);
    
    static {
        addItemFlags(CHECKBOX_UNCHECKED, ItemFlag.HIDE_POTION_EFFECTS);
    }
    
}
