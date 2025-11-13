package menu;

/**
 * Enum representing the options available in the admin menu
 * @author Cláudia Martins
 */
enum AdminMenuOptions {

    /**
     * Option to see all customers
     */
    SEE_CUSTOMERS(1, "See all customers"),
    /**
     * Option to see all rooms
     */
    SEE_ROOMS(2, "See all rooms"),
    /**
     * Option to see all reservations
     */
    SEE_RESERVATIONS(3, "See all reservations"),
    /**
     * Option to add a room
     */
    ADD_ROOM(4, "Add a room"),
    /**
     * Option to populate with test data
     */
    POPULATE(5, "Populate with test data"),
    /**
     * Option to return to the main menu
     */
    OPEN_MAIN_MENU(6, "Back to main menu");

    /**
     * The numeric code associated with the menu option
     */
    private final int code;
    /**
     * The description of the menu option
     */
    private final String description;

    /**
     * Constructor for AdminMenuOptions enum
     * @param code: the numeric code of the menu option
     * @param description: the description of the menu option
     */
    private AdminMenuOptions(int code, String description) {
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
