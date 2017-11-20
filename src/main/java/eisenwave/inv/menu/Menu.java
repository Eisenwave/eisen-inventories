package eisenwave.inv.menu;

import eisenwave.inv.error.DrawException;
import eisenwave.inv.view.*;
import eisenwave.inv.widget.MenuPane;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.*;

import java.util.*;

public class Menu {
    
    private boolean interactable = true;
    
    private final static int WIDTH = 9;
    
    private final Set<View> invalid = new HashSet<>();
    
    private final Inventory inventory;
    private final MenuPane contentPane;
    private IconBuffer buffer;
    
    private boolean drawing = false;
    
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
    
    // MUTATORS
    
    /**
     * Sets the menu to be interactable.
     *
     * @param interactable whether the menu should be interactable
     */
    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }
    
    // ACTIONS
    
    /**
     * Performs a click inside this menu.
     *
     * @param player the player who clicked
     * @param x the x-coordinate of the click
     * @param y the y-coordinate of the click
     * @param click the type of the click
     * @return the response
     */
    public MenuResponse performClick(Player player, int x, int y, ClickType click) {
        if (!interactable) return MenuResponse.BLOCK;
        
        Icon icon = this.buffer.get(x, y);
        if (icon != null) {
            //System.out.println("clicked icon");
            View view = icon.getView();
            System.out.println(click);
            return view.performAction(player, new ViewAction(ViewActionType.CLICK, click));
        }
        else {
            return MenuResponse.EMPTY;
            //System.out.println("clicked empty slot");
        }
    }
    
    /**
     * Called from the outside when the player closes this menu.
     *
     * @param player the player
     */
    public void performClose(Player player) {}
    
    /**
     * Called from the outside when the player opens this menu.
     *
     * @param player the player
     */
    public void performOpen(Player player) {}
    
    private static int drawCallNo = 1;
    
    /**
     * Draws the menu into its inventory.
     *
     * @throws DrawException if an error occurs when drawing widgets
     */
    public void draw() {
        if (invalid.isEmpty()) return;
        System.out.println("DRAW_CALL_NO = "+drawCallNo++);
        
        for (View view : invalid.toArray(new View[invalid.size()]))
            view.prepareDraw();
        
        //System.out.println(ANSI.YELLOW + "draw call!" + ANSI.RESET);
        //this.buffer.clear();
        drawing = true;
        
        if (contentPane.isInvalidated()) {
            System.out.println("DRAW "+contentPane);
            clearBuffer();
            int lim = buffer.getSize();
            ItemStack[] contents = new ItemStack[lim];
            
            try {
                if (!contentPane.isHidden()) {
                    contentPane.draw(buffer);
                    //System.out.println("drawing "+x+", "+y+": "+icon.getStack().getType());
                    for (int i = 0; i < lim; i++) {
                        Icon icon = buffer.get(i);
                        if (icon != null)
                            contents[i] = icon.getStack();
                    }
                }
            } catch (Exception ex) {
                throw new DrawException(ex);
            } finally {
                drawing = false;
                inventory.setContents(contents);
                invalid.forEach(View::revalidate);
                invalid.clear();
            }
        }
        else try {
            for (View view : invalid) {
                //System.out.println("is invalidated: " + view);
                if (view.isInvalidated() && !view.getParent().isInvalidated()) {
                    System.out.println("DRAW "+view);
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
        } catch (Exception ex) {
            throw new DrawException(ex);
        } finally {
            drawing = false;
            invalid.forEach(View::revalidate);
            invalid.clear();
        }
        
        /*
        buffer.forEach((x, y, icon) -> {
            //System.out.println("drawing "+x+", "+y+": "+icon.getStack().getType());
            inventory.setItem(x + y * width, icon.getStack());
        });
        */
        
    }
    
    // INVALIDATION
    
    /**
     * Marks a view as invalidated for the menu.
     *
     * @param view the view
     */
    public void invalidate(@NotNull View view) {
        if (drawing)
            throw new ConcurrentModificationException("tried to invalidate " + view + " during a draw call");
        this.invalid.add(view);
    }
    
    /**
     * Forcefully re-validates this menu and all views in it.
     */
    public void revalidate() {
        this.invalid.forEach(View::revalidate);
        this.invalid.clear();
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
    }
    
    // BUFFER UTIL
    
    private void setIcon(int x, int y, @Nullable Icon icon) {
        //System.out.println(x + " " + y + " -> " + icon);
        buffer.set(x, y, icon);
        ItemStack item = icon == null? null : icon.getStack();
        inventory.setItem(x + y * width, item);
    }
    
    private void clearBuffer() {
        this.buffer = new IconBuffer(width, height);
    }
    
}
