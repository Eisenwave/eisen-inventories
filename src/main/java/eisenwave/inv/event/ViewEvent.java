package eisenwave.inv.event;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.view.View;
import org.bukkit.entity.Player;

public class ViewEvent {
    
    private final View view;
    private final Player player;
    
    public ViewEvent(View view, Player player) {
        this.view = view;
        this.player = player;
    }
    
    public View getView() {
        return view;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Menu getMenu() {
        return view.getMenu();
    }
    
}
