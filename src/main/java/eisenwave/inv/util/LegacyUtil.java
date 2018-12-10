package eisenwave.inv.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public final class LegacyUtil {
    
    private final static BukkitVersion BUKKIT_VERSION = new BukkitVersion(Bukkit.getBukkitVersion());
    private final static boolean API_13 = BUKKIT_VERSION.isNewerEqual(new BukkitVersion("1.13"));
    
    @SuppressWarnings("deprecation")
    private final static ItemStackConstructor ITEM_STACK_CONSTRUCTOR = API_13?
        (key, count) -> {
            MinecraftObject obj = getByMinecraftKey13(key);
            if (obj == null)
                throw new IllegalArgumentException("key \"" + key + "\" is not a valid key");
            return new ItemStack(obj.getMaterial(), count);
        } :
        (key, count) -> {
            MinecraftObject obj = getByMinecraftKey13(key);
            if (obj == null)
                throw new IllegalArgumentException("key \"" + key + "\" is not a valid key");
            return new ItemStack(obj.getMaterial(), count, obj.getData());
        };
    
    @SuppressWarnings("SpellCheckingInspection")
    private final static LegacyUtil INSTANCE = new LegacyUtil("blocktable_extended.csv");
    
    private final Set<MinecraftObject> blocks = new LinkedHashSet<>();
    private final Set<MinecraftObject> items = new LinkedHashSet<>();
    private final Map<String, MinecraftObject> byKey = new HashMap<>();
    private final Map<Long, MinecraftObject> byIdData = new HashMap<>();
    private final Map<Material, MinecraftObject> byMaterial = new HashMap<>();
    
    private LegacyUtil(String blockTableResource) {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(blockTableResource)) {
            CSVParser parser = CSVParser.parse(stream, Charset.defaultCharset(), CSVFormat.DEFAULT);
            for (CSVRecord record : parser.getRecords()) {
                String dataStr = record.get(1);
                if (dataStr.isEmpty()) continue;
                
                int id = Integer.parseInt(record.get(0));
                short data = Short.parseShort(record.get(1));
                String key = record.get(3);
                int blockDataIndex = key.indexOf('[');
                if (blockDataIndex != -1)
                    key = key.substring(0, blockDataIndex);
                
                Material material = API_13?
                    Material.valueOf(key.toUpperCase()) :
                    Material.valueOf(record.get(2));
                
                try {
                    add(new MinecraftObject(key, material, id, data));
                } catch (IllegalArgumentException ex) {
                    throw new IOException("error when parsing: " + record.toString(), ex);
                }
            }
        } catch (IOException ex) {
            throw new IOError(ex);
        }
    }
    
    private void add(MinecraftObject object) {
        byKey.put(object.getKey(), object);
        byMaterial.put(object.getMaterial(), object);
        byIdData.put((long) ((object.getId() << 4) | object.getData()), object);
        
        (object.isItem()? items : blocks).add(object);
    }
    
    public static boolean isApi13() {
        return API_13;
    }
    
    public static Set<MinecraftObject> getAllItems() {
        return Collections.unmodifiableSet(INSTANCE.items);
    }
    
    public static Set<MinecraftObject> getAllBlocks() {
        return Collections.unmodifiableSet(INSTANCE.blocks);
    }
    
    @Nullable
    public static MinecraftObject getByIdAndData(int id, byte data) {
        //noinspection deprecation
        MinecraftObject result = INSTANCE.byIdData.get((long) ((id << 4) | data));
        
        return result != null? result : INSTANCE.byIdData.get((long) id << 4);
    }
    
    public static MinecraftObject getByMaterial(Material material) {
        return INSTANCE.byMaterial.get(material);
    }
    
    public static MinecraftObject getByMinecraftKey13(String key) {
        return INSTANCE.byKey.get(key);
    }
    
    @SuppressWarnings("deprecation")
    public static ItemStack createItemStack(String key, int count) {
        return ITEM_STACK_CONSTRUCTOR.init(key, count);
    }
    
    private static interface ItemStackConstructor {
        
        public ItemStack init(String key, int count);
        
    }
    
}
