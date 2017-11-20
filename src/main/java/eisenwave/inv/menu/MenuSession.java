package eisenwave.inv.menu;

import eisenwave.inv.query.Query;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MenuSession {
    
    @NotNull
    private final Menu menu;
    @Nullable
    private Query<?> query;
    
    /**
     * Constructs a new session.
     *
     * @param menu the menu of the session
     */
    public MenuSession(@NotNull Menu menu) {
        this.menu = menu;
    }
    
    // GETTERS
    
    /**
     * Returns the menu of this session.
     *
     * @return the menu
     */
    @NotNull
    public Menu getMenu() {
        return menu;
    }
    
    /**
     * Returns the query of this session or {@link null} if the session is not being queried.
     *
     * @return the query
     */
    public Query<?> getQuery() {
        return query;
    }
    
    // MUTATORS
    
    public boolean isQueried() {
        return query != null;
    }
    
    public void setQuery(@Nullable Query<?> query) {
        this.query = query;
    }
    
}
