package eisenwave.inv.view;

public class ViewAction {
    
    private final ViewActionType type;
    
    public ViewAction(ViewActionType type) {
        this.type = type;
    }
    
    public ViewActionType getType() {
        return type;
    }
    
}
