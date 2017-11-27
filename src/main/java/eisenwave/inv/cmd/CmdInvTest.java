package eisenwave.inv.cmd;

import eisenwave.inv.EisenInventoriesPlugin;
import eisenwave.inv.menu.Menu;
import eisenwave.inv.menu.MenuManager;
import eisenwave.inv.menu.MenuSession;
import eisenwave.inv.view.ViewSize;
import eisenwave.inv.widget.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdInvTest extends EisenInvCommand {
    
    public CmdInvTest(EisenInventoriesPlugin plugin) {
        super(plugin, "invtest");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;
        
        switch (args[0]) {
            case "open": {
                Player player = (Player) sender;
                Menu menu = new TestMenu();
                MenuManager.getInstance().startSession(player, menu);
                return true;
            }
            
            case "list": {
                sender.sendMessage("amount of menus: "+MenuManager.getInstance().getSessions().size());
                MenuManager.getInstance().getSessions().stream()
                    .map(MenuSession::getMenu)
                    .forEach(menu -> sender.sendMessage(menu.getTitle()+" "+menu.getWidth()+"x"+menu.getHeight()));
                return true;
            }
    
            case "stuff": {
        
            }
    
            default: return false;
        }
    }
    
    private static class TestMenu extends Menu {
        
        public TestMenu() {
            super(ChatColor.BOLD + "Test");
    
            Pane pane = new Pane(this, null);
            pane.setHeight(2);
            getContentPane().addChild(pane);
    
            CheckBox checkBox = new CheckBox(this, null);
            checkBox.setPosition(2, 2);
            checkBox.addCheckListener(event -> {
                event.getPlayer().sendMessage(ChatColor.BLUE + "checked: " + event.isChecked());
                pane.setHidden(event.isChecked());
            });
            getContentPane().addChild(checkBox);
    
            NumberPicker picker = new NumberPicker(this, null);
            picker.setPosition(4, 2);
            getContentPane().addChild(picker);
    
            Switch s = new Switch(this, null, "Apple", "Orange", "Strawberry", "Grapefruit", "Pineapple", "Lemon");
            s.setPosition(6, 2);
            getContentPane().addChild(s);
    
            ProgressBar bar = new ProgressBar(this, null);
            bar.setPosition(0, 3);
            bar.setSize(ViewSize.MATCH_PARENT, ViewSize.WRAP_CONTENT);
            bar.setProgress(0.5f);
            getContentPane().addChild(bar);
    
            RadioList radios = new RadioList(this, ViewSize.matchParent());
            for (int i = 0; i < 5; i++)
                radios.addChild(new RadioButton(this, null));
            radios.setPosition(1, 5);
            getContentPane().addChild(radios);
        }
        
    }
    
}
