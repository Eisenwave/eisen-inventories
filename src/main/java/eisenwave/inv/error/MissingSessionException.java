package eisenwave.inv.error;

import eisenwave.inv.menu.MenuSession;
import eisenwave.inv.query.Query;

/**
 * Thrown when an operation of a {@link MenuSession} is being performed, such as starting a {@link Query} when a player
 * does not have an active session.
 */
public class MissingSessionException extends RuntimeException {
    
    public MissingSessionException() {
        super();
    }
    
    public MissingSessionException(String message) {
        super(message);
    }
    
}
