package eisenwave.inv.menu;

import eisenwave.inv.widget.*;

public enum MenuResponse {
    /**
     * Indicates that whatever the user did in the view was handled correctly and took effect in the model.
     * <p>
     * Examples of this are navigation, highlighting, a successful button press etc.
     */
    OK,
    
    /**
     * Indicates that the user interacted with a disabled widget or that the user interacted with a menu which is
     * set to be non-interactive.
     * <p>
     * This response is not to be used for a lack of permissions, only for temporarily disabled functionality.
     */
    BLOCK,
    
    /**
     * Indicates that the user clicked on a portion of the menu that does not contain any widget or clicked outside
     * of the menu.
     * <p>
     * Either way, this response is only to be called by the menu or by a widget such as a {@link Pane}, which is
     * <b>never</b> interactive.
     */
    EMPTY,
    
    /**
     * Indicates that an error occurred when interacting with the menu, either because the user was not authorized to
     * perform some action or because an internal process failed.
     */
    ERROR
}
