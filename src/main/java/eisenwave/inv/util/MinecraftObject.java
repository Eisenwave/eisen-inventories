package eisenwave.inv.util;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class MinecraftObject {
    
    private final boolean item;
    private final String key;
    private final Material material;
    private final int id;
    private final short data;
    
    public MinecraftObject(@NotNull String key, @NotNull Material material, int id, short data) {
        if (id < 0) throw new IllegalArgumentException("id must be positive");
        if (data < 0) throw new IllegalArgumentException("data must be positive");
        
        this.item = id > 255;
        this.key = key;
        this.material = material;
        this.id = id;
        this.data = data;
    }
    
    public boolean isItem() {
        return item;
    }
    
    public String getKey() {
        return key;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public int getId() {
        return id;
    }
    
    public short getData() {
        return data;
    }
    
}
