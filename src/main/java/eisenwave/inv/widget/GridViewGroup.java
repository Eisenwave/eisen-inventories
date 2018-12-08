package eisenwave.inv.widget;

import eisenwave.inv.menu.Menu;
import eisenwave.inv.view.*;
import org.jetbrains.annotations.NotNull;

public class GridViewGroup extends ViewGroup {
    
    private int cellSize = 1;
    
    public GridViewGroup(@NotNull Menu menu) {
        super(menu);
    }
    
    public GridViewGroup(@NotNull View parent) {
        super(parent);
    }
    
    public int getCellSize() {
        return cellSize;
    }
    
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }
    
    @Override
    public void drawContent(IconBuffer buffer) {
        forEach(child -> {
        
        });
    }
    
}
