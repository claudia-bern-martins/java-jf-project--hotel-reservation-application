package menu;

public enum MainMenu {

    FIND_RESERVE_ROOM(1, "Find and reserve a room"),
    SEE_OWN_RESERVATIONS(2, "See my reservations"),
    CREATE_ACCOUNT(3, "Create an account"),
    OPEN_ADMIN_MENU(4, "Admin"),
    EXIT(5, "Exit");


    private final int code;
    private final String description;

    private MainMenu(int code, String description) {
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
