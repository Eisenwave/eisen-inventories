package eisenwave.inv.widget;

import org.bukkit.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static eisenwave.inv.util.ItemUtil.*;

final class _DefaultStyles {
    
    public final static ItemStack
        BUTTON_ITEM = create("emerald", ChatColor.YELLOW + "Button"),
        CHECKBOX_CHECKED = create("slime_ball", ChatColor.GREEN + "Checked"),
        CHECKBOX_UNCHECKED = create("firework_star", ChatColor.DARK_GRAY + "Unchecked"),
        DISPLAY_ITEM = create("painting"),
        NAV_BUTTON_L_OFF = create("arrow", ChatColor.DARK_GRAY + "<<<"),
        NAV_BUTTON_R_OFF = create("arrow", ChatColor.DARK_GRAY + ">>>"),
        NAV_BUTTON_L_ON = create("spectral_arrow", ChatColor.YELLOW + ChatColor.BOLD.toString() + "<<<",
            "&7Left-Click:\n&8scroll multiple\n&7Right-Click:\n&8scroll one\n&7Middle-Click:\n&8go to start"),
        NAV_BUTTON_R_ON = create("spectral_arrow", ChatColor.YELLOW + ChatColor.BOLD.toString() + ">>>",
            "&7Left-Click:\n&8scroll multiple\n&7Right-Click:\n&8scroll one\n&7Middle-Click:\n&8go to end"),
        RADIO_BUTTON_CHECKED = create("lime_dye", ChatColor.GREEN + "Checked"),
        RADIO_BUTTON_UNCHECKED = create("gray_dye", ChatColor.DARK_GRAY + "Unchecked"),
        PANE_ITEM = create("black_stained_glass_pane", " "),
        PROGRESSBAR_ON = create("lime_stained_glass_pane", " "),
        PROGRESSBAR_OFF = create("black_stained_glass_pane", " "),
        NUMBER_PICKER = create("prismarine_crystals"),
        SWITCH = create("paper");
    
    static {
        addItemFlags(CHECKBOX_UNCHECKED, ItemFlag.HIDE_POTION_EFFECTS);
    }
    
}
