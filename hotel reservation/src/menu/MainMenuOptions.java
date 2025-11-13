package menu;

/**
 * Enum representing the options available in the main menu
 * @author Cláudia Martins
 */
enum MainMenuOptions {

    /**
     * Option to find and reserve a room
     */
    FIND_RESERVE_ROOM(1, "Find and reserve a room"),
    /**
     * Option to see own reservations
     */
    SEE_OWN_RESERVATIONS(2, "See my reservations"),
    /**
     * Option to create an account
     */
    CREATE_ACCOUNT(3, "Create an account"),
    /**
     * Option to open the admin menu
     */
    OPEN_ADMIN_MENU(4, "Admin"),
    /**
     * Option to exit the application
     */
    EXIT(5, "Exit");

    /**
     * The numeric code associated with the menu option
     */
    private final int code;
    /**
     * The description of the menu option
     */
    private final String description;

    /**
     * Constructor for MainMenuOptions enum
     * @param code: the numeric code of the menu option
     * @param description: the description of the menu option
     */
    private MainMenuOptions(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets the numeric code of the menu option
     * @return the numeric code of the menu option
     */
    int getCode() {
        return this.code;
    }

    /**
     * Gets the description of the menu option
     * @return the description of the menu option
     */
    String getDescription() {
        return this.description;
    }

    /**
     * Returns a string representation of the menu option
     * @return a string representation of the menu option
     */
    @Override
    public String toString() {
        return this.code + ". " + this.description;
    }
}
