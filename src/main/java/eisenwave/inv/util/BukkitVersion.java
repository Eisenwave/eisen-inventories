package eisenwave.inv.util;

import org.jetbrains.annotations.*;

public class BukkitVersion implements Comparable<BukkitVersion> {
    
    private final String str;
    private final int major, minor, minorMinor;
    //private final String protocol;
    
    public BukkitVersion(@NotNull String version) {
        this.str = version;
        String[] parts = version.split("-", 2);
        String[] nums = parts[0].split("\\.", 3);
        
        this.major = Integer.parseInt(nums[0]);
        this.minor = Integer.parseInt(nums[1]);
        this.minorMinor = nums.length > 2? Integer.parseInt(nums[2]) : 0;
    }
    
    /**
     * Returns the first number of the version. For instance, for 1.12.2, this would return 1.
     *
     * @return the first number of the version
     */
    public int getMajor() {
        return major;
    }
    
    /**
     * Returns the second number of the version. For instance, for 1.12.2, this would return 12.
     *
     * @return the second number of the version
     */
    public int getMinor() {
        return minor;
    }
    
    /**
     * Returns the third number of the version. For instance, for 1.12.2, this would return 2.
     * <p>
     * If the version doesn't have a third number, such as the version 1.12, this returns 0.
     *
     * @return the third number of the version
     */
    public int getMinorMinor() {
        return minorMinor;
    }
    
    // PREDICATES
    
    /**
     * Tests whether this version is more recent than the given version.
     *
     * @param version the version
     * @return whether this version is newer
     */
    public boolean isNewerThan(BukkitVersion version) {
        return compareTo(version) > 0;
    }
    
    /**
     * Tests whether this version is more or equally recent than the given version.
     *
     * @param version the version
     * @return whether this version is newer or equal
     */
    public boolean isNewerEqual(BukkitVersion version) {
        return compareTo(version) >= 0;
    }
    
    /**
     * Tests whether this version is less recent than the given version.
     *
     * @param version the version
     * @return whether this version is older
     */
    public boolean isOlderThan(BukkitVersion version) {
        return compareTo(version) < 0;
    }
    
    /**
     * Tests whether this version is less or equally recent than the given version.
     *
     * @param version the version
     * @return whether this version is newer or equal
     */
    public boolean isOlderEqual(BukkitVersion version) {
        return compareTo(version) <= 0;
    }
    
    // MISC
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
    public boolean equals(BukkitVersion version) {
        return this.major == version.major
            && this.minor == version.minor
            && this.minorMinor == version.minorMinor;
    }
    
    @Override
    public String toString() {
        return str;
    }
    
    /**
     * Compares this version to another version. This comparator sorts versions from the oldest to most recent.
     *
     * @param version the version
     * @return the comparison result
     */
    @Override
    public int compareTo(@NotNull BukkitVersion version) {
        int result = this.getMajor() - version.getMajor();
        if (result != 0) return result;
        
        result = this.getMinor() - version.getMinor();
        if (result != 0) return result;
        
        return this.getMinorMinor() - version.getMinorMinor();
    }
    
}
