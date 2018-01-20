package eisenwave.inv.query;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

import static net.md_5.bungee.api.ChatColor.*;

public abstract class ChatQuery implements Query<String> {
    
    private final static String
        //HEADER = "--------- Chat Query -----------------------------\n",
        HEADER_PRE = "--------- ",
        TITLE = "Chat Query",
        HEADER_SUF =  " -----------------------------\n",
        BODY = "Enter something in chat or",
        FOOTER = "\n--------------------------------------------------";
    
    /*
     * Initializes the query.
     * <p>
     * This method is called when the menu has been closed and the player is ready to be queried.
     * <p>
     * This also gives the query an opportunity to register listeners and other things which are necessary to retrieve
     * a result.
     *
     * @param player the player to be queried
     *
    public void onInit(Player player) {
        player.sendMessage(ChatColor.GOLD+"Please enter the string:");
    }
    */
    
    public void inform(Player player) {
        BaseComponent[] hoverText = TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',
            "&cCancel\n&7/cancelquery"));
        ClickEvent onClick = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cancelquery");
        HoverEvent onHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText);
        
        BaseComponent[] msg = new ComponentBuilder(HEADER_PRE)
            .color(BLUE)
            .append(TITLE).color(RESET).bold(true)
            .append(HEADER_SUF).color(BLUE).bold(false)
            .append("Enter something in chat or press ").color(GRAY)
            .append("[Cancel]", ComponentBuilder.FormatRetention.NONE)
            .color(RED).bold(true)
            .event(onClick).event(onHover)
            .append(" to cancel this query").color(GRAY).bold(false)
            .append(FOOTER).color(BLUE)
            //.append(FOOTER).color(BLUE)
            .create();
        
        player.spigot().sendMessage(msg);
    }
    
}
