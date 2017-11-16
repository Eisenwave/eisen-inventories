package eisenwave.inv.event;

@FunctionalInterface
public interface CheckListener {
    
    abstract void onCheck(CheckEvent action);
    
}
