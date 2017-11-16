package eisenwave.inv.error;

/**
 * Thrown when an attribute is being accessed which does not exist for a given widget type.
 */
public class UnknownAttributeException extends RuntimeException {
    
    public UnknownAttributeException(String attribute) {
        super(attribute);
    }
    
}
