package eisenwave.inv.util;

import org.jetbrains.annotations.Contract;

public final class EisenInvUtil {
    
    private EisenInvUtil() {}
    
    // CLAMP
    
    /**
     * Clamps a value so it is inclusively within the range <code>[min..max]</code>.
     *
     * @param min the range minimum value
     * @param val the value
     * @param max the range maximum value
     * @return the clamped value
     * @throws IllegalArgumentException if min > max
     */
    @Contract(pure = true)
    public static int clamp(int min, int val, int max) {
        if (min > max) throw new IllegalArgumentException("min > max");
        
        return (val < min)? min : (val > max)? max : val;
    }
    
    /**
     * Clamps a value so it is inclusively within the range <code>[min..max]</code>.
     *
     * @param min the range minimum value
     * @param val the value
     * @param max the range maximum value
     * @return the clamped value
     * @throws IllegalArgumentException if min > max
     */
    @Contract(pure = true)
    public static double clamp(double min, double val, double max) {
        if (min > max) throw new IllegalArgumentException("min > max");
        
        return (val < min)? min : (val > max)? max : val;
    }
    
    /**
     * Clamps a value so it is inclusively within the range <code>[min..max]</code>.
     *
     * @param min the range minimum value
     * @param val the value
     * @param max the range maximum value
     * @return the clamped value
     * @throws IllegalArgumentException if min > max
     */
    @Contract(pure = true)
    public static float clamp(float min, float val, float max) {
        if (min > max) throw new IllegalArgumentException("min > max");
        
        return (val < min)? min : (val > max)? max : val;
    }
    
    // CLAMP 01
    
    /**
     * Clamps a number to be in range(0,1).
     *
     * @param number the number
     * @return the clamped number
     */
    @Contract(pure = true)
    public static double clamp01(double number) {
        return number < 0? 0 : number > 1? 1 : number;
    }
    
    /**
     * Clamps a number to be in range(0,1).
     *
     * @param number the number
     * @return the clamped number
     */
    @Contract(pure = true)
    public static float clamp01(float number) {
        return number < 0? 0 : number > 1? 1 : number;
    }
    
}
