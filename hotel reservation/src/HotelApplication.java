import menu.MainMenu;

/**
 * The main class for the Hotel Reservation Application
 * @author Cláudia Martins
 */
public class HotelApplication {

    /**
     * The main method to start the application
     * @param args: command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("~ Welcome to the Hotel Reservation Application! ~");
        try {
            MainMenu.getInstance().handleInputs();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        System.out.println("~ Goodbye! We hope to see you again soon. ~");
    }
}
