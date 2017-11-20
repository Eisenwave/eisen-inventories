package eisenwave.inv.query;

import org.bukkit.entity.Player;

public interface Queryable {
    
    abstract <T> T query(Player player, Query<T> query);
    
}
