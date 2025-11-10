package menu;

public enum AdminMenu {

    SEE_CUSTOMERS(1, "See all customers"),
    SEE_ROOMS(2, "See all rooms"),
    SEE_RESERVATIONS(3, "See all reservations"),
    ADD_ROOM(4, "Add a room"),
    OPEN_MAIN_MENU(5, "Back to main menu");


    private final int code;
    private final String description;

    private AdminMenu(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription () {
        return this.description;
    }

    @Override
    public String toString() {
        return this.code + ". " + this.description;
    }
}
