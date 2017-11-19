package eisenwave.inv.error;

/**
 * Thrown when a menu or view inside it throws an exception whilst being drawn.
 */
public class DrawException extends RuntimeException {
    
    public DrawException() {
        super();
    }
    
    public DrawException(String message) {
        super(message);
    }
    
    public DrawException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DrawException(Throwable cause) {
        super(cause);
    }
    
}
