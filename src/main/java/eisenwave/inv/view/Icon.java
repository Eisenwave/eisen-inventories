package eisenwave.inv.view;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Icon {
    
    private final View view;
    private final ItemStack stack;
    
    public Icon(@NotNull View view, @NotNull ItemStack stack) {
        this.view = view;
        this.stack = stack;
    }
    
    public View getView() {
        return view;
    }
    
    public ItemStack getStack() {
        return stack;
    }
    
}
