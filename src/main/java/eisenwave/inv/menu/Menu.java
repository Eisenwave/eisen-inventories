package eisenwave.inv.menu;

import eisenwave.inv.view.*;
import eisenwave.inv.widget.MenuPane;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Menu {
    
    private boolean interactable = true;
    
    private final static int WIDTH = 9;
    
    private final Set<View> invalid = new HashSet<>();
    
    private final Inventory inventory;
    private final MenuPane contentPane;
    private IconBuffer buffer;
    
    private final int width, height;
    
    /**
     * Constructs a new menu with an optional custom title.
     *
     * @param size the size of the menu in slots (must be a multiple of 9)
     * @param title the title of the menu or null if the default title is to be used
     * @throws IllegalArgumentException if the size is not a multiple of 9
     */
    public Menu(int size, @Nullable String title) {
        if (size % WIDTH != 0) throw new IllegalArgumentException("size must be a multiple of 9");
        this.inventory = Bukkit.createInventory(null, size, title);
        this.width = WIDTH;
        this.height = size / WIDTH;
        this.contentPane = new MenuPane(this);
        this.buffer = new IconBuffer(width, height);
    }
    
    /**
     * Constructs a new menu with an optional custom title.
     *
     * @param size the size of the menu in slots (must be a multiple of 9)
     * @throws IllegalArgumentException if the size is not a multiple of 9
     */
    public Menu(int size) {
        this(size, null);
    }
    
    /**
     * Constructs a new menu which with maximum size.
     */
    public Menu(@Nullable String title) {
        this(54, title);
    }
    
    /**
     * Constructs a new menu which with maximum size.
     */
    public Menu() {
        this(54);
    }
    
    /**
     * Returns the title of this menu.
     *
     * @return the menu title
     */
    public String getTitle() {
        return inventory.getTitle();
    }
    
    /**
     * Returns the size of this inventory in slots.
     *
     * @return the size of this inventory in slots
     */
    public int getSize() {
        return inventory.getSize();
    }
    
    /**
     * Returns the maximum available width of this menu.
     *
     * @return the menu width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the menu height.
     *
     * @return the menu height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Returns the view which contains all views in this inventory.
     *
     * @return the content pane
     */
    public MenuPane getContentPane() {
        return contentPane;
    }
    
    /**
     * Returns whether this menu allows player interaction.
     *
     * @return whether this menu allows player interaction
     */
    public boolean isInteractable() {
        return interactable;
    }
    
    /**
     * Sets the menu to be interactable.
     *
     * @param interactable whether the menu should be interactable
     */
    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }
    
    // ACTIONS
    
    public void click(Player player, int x, int y) {
        if (!interactable) return;
        
        Icon icon = this.buffer.get(x, y);
        if (icon != null) {
            System.out.println("clicked icon");
            View view = icon.getView();
            view.performAction(player, new ViewAction(ViewActionType.CLICK));
        }
        else {
            System.out.println("clicked empty slot");
        }
    }
    
    public void draw() {
        if (invalid.isEmpty()) return;
        //System.out.println(ANSI.YELLOW + "draw call!" + ANSI.RESET);
        //this.buffer.clear();
        
        if (contentPane.isInvalidated()) {
            //System.out.println("drawing: "+contentPane.getClass().getSimpleName());
            clearBuffer();
            int lim = buffer.getSize();
            ItemStack[] contents = new ItemStack[lim];
            
            if (!contentPane.isHidden()) {
                contentPane.draw(buffer);
                //System.out.println("drawing "+x+", "+y+": "+icon.getStack().getType());
                for (int i = 0; i < lim; i++) {
                    Icon icon = buffer.get(i);
                    if (icon != null)
                        contents[i] = icon.getStack();
                }
            }
            inventory.setContents(contents);
        }
        else for (View view : invalid) {
            //System.out.println("is invalidated: " + view);
            if (view.isInvalidated() && !view.getParent().isInvalidated()) {
                //System.out.println("drawing invalidated: " + view);
                final int
                    vx = view.getX(),
                    vy = view.getY(),
                    vw = view.getWidth(),
                    vh = view.getHeight();
                IconBuffer viewBuffer = new IconBuffer(vw, vh);
                //buffer.setOffset(view.getX(), view.getY());
                //System.out.println("set offset in buffer to: "+view.getX()+", "+view.getY());
                view.draw(viewBuffer);
                for (int x = 0; x < vw; x++) {
                    for (int y = 0; y < vh; y++) {
                        setIcon(vx + x, vy + y, viewBuffer.get(x, y));
                    }
                }
            }
            //System.out.println(ANSI.FG_CYAN + "validating " + view + ANSI.FG_RESET);
            //view.setValidated(true);
        }
        
        /*
        buffer.forEach((x, y, icon) -> {
            //System.out.println("drawing "+x+", "+y+": "+icon.getStack().getType());
            inventory.setItem(x + y * width, icon.getStack());
        });
        */
        invalid.clear();
    }
    
    // INVALIDATION
    
    /**
     * Marks a view as invalidated for the menu.
     *
     * @param view the view
     */
    public void invalidate(@NotNull View view) {
        this.invalid.add(view);
    }
    
    /**
     * Returns an immutable set containing all invalid views.
     *
     * @return all invalid views
     */
    public Set<View> getInvalid() {
        return Collections.unmodifiableSet(invalid);
    }
    
    // VIEWERS
    
    /**
     * Shows this menu to a viewer.
     *
     * @param viewer the viewer
     */
    public void showTo(Player viewer) {
        viewer.openInventory(inventory);
        MenuManager.getInstance().openMenu(viewer, this);
    }
    
    // UTIL
    
    private void setIcon(int x, int y, @Nullable Icon icon) {
        //System.out.println(x + " " + y + " -> " + icon);
        buffer.set(x, y, icon);
        inventory.setItem(x + y * width, icon == null? null : icon.getStack());
    }
    
    private void clearBuffer() {
        this.buffer = new IconBuffer(width, height);
    }
    
}
