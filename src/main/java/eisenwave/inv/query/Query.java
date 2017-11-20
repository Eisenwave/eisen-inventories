package eisenwave.inv.query;

import org.bukkit.entity.Player;

public interface Query<T> {
    
    /**
     * Callback for a query result.
     * <p>
     * This method is called when the player has been successfully queried for the given result.
     *
     * @param player the player
     * @param result the result
     */
    abstract void onResult(Player player, T result);
    
    /**
     * Callback for a query failure.
     * <p>
     * This method is called when a query fails due to a player leaving, the server shutting down or other possible
     * causes.
     *
     * @param player the player
     */
    abstract void onFail(Player player);
    
}
