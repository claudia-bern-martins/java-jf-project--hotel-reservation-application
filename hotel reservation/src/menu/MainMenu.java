package menu;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import utils.DateFormatter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Singleton class that handles the main menu interactions in a hotel
 * reservation application
 * 
 * @author Cláudia Martins
 */
public final class MainMenu {

    /**
     * The singleton instance of MainMenu
     */
    private static MainMenu MAIN_MENU;
    /**
     * Scanner for reading user input
     */
    private final Scanner scanner;
    /**
     * The AdminResource instance for managing admin-related operations
     */
    private final AdminResource adminResource;
    /**
     * The HotelResource instance for managing hotel-related operations
     */
    private final HotelResource hotelResource;

    /**
     * Private constructor to prevent instantiation from outside the class
     */
    private MainMenu() {
        this.scanner = new Scanner(System.in);
        this.adminResource = AdminResource.getInstance();
        this.hotelResource = HotelResource.getInstance();
    }

    /**
     * Provides access to the singleton instance of MainMenu
     * 
     * @return the singleton instance of MainMenu
     */
    public static synchronized MainMenu getInstance() {
        if (MAIN_MENU == null) {
            MAIN_MENU = new MainMenu();
        }
        return MAIN_MENU;
    }

    /**
     * Handles the main menu interactions
     */
    public void handleInputs() {
        System.out.println("Please choose an option from the menu below:");
        System.out.println("--- MENU ---");
        printMenuOptions();

        int userChoice = 0;
        try {
            userChoice = this.scanner.hasNextInt() ? this.scanner.nextInt()
                    : Integer.parseInt(this.scanner.next());
        } catch (NumberFormatException e) {
            System.out.println("Selection must be a number.");
            handleInputs();
            return;
        }
        if (userChoice != MainMenuOptions.EXIT.getCode()) {
            MainMenuOptions choice = null;
            try {
                choice = MainMenuOptions.values()[userChoice - 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Illegal choice. Please select a number from " +
                        "the list (1-" + MainMenuOptions.values().length + ").");
            }

            if (choice != null) {
                switch (choice) {
                    case FIND_RESERVE_ROOM: {
                        handleReservationCreation();
                        break;
                    }
                    case SEE_OWN_RESERVATIONS: {
                        showOwnReservationList();
                        break;
                    }
                    case CREATE_ACCOUNT: {
                        handleAccountCreation();
                        break;
                    }
                    case OPEN_ADMIN_MENU: {
                        handleAdminInputs();
                        break;
                    }
                    case EXIT: {
                        break;
                    }
                    default:
                        break;
                }
            }
            handleInputs();
        }
    }

    /**
     * Prints the main menu options to the console
     */
    private void printMenuOptions() {
        Arrays.stream(MainMenuOptions.values()).forEach(
                option -> System.out.println(option));
    }

    /**
     * Handles the reservation creation process
     */
    private void handleReservationCreation() {
        System.out.println("\n--- RESERVATION CREATION ---");
        if (this.adminResource.getAllRooms().isEmpty()) {
            System.out.println("No rooms have been created. Please add some " +
                    "rooms before attempting a reservation.");
            return;
        }

        System.out.println("Please enter your email:");
        String email = this.scanner.next();
        Customer customer = this.hotelResource.getCustomer(email);
        if (customer == null) {
            System.out.println("Customer with email " + email + " does not " +
                    "exist. Proceeding with account creation.");
            handleAccountCreation();
        }

        bookARoom(email);
        System.out.println("--- END RESERVATION CREATION ---\n");
    }

    /**
     * Books a room for the customer associated with the given email
     * 
     * @param email: the email address of the customer
     */
    private void bookARoom(String email) {
        try {
            Date checkInDate = this.getDate("check-in");
            Date checkOutDate = this.getDate("check-out");

            if (checkInDate.after(checkOutDate)) {
                throw new IllegalArgumentException("The check-out date has to be " +
                        "later than the check-in date.");
            }

            IRoom chosenRoom = this.getChosenRoom(checkInDate, checkOutDate);
            System.out.println(this.hotelResource.bookARoom(email, chosenRoom,
                    checkInDate, checkOutDate));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
            bookARoom(email);
        }
    }

    /**
     * Prompts the user to enter a date and returns the parsed Date object
     * 
     * @param type: the type of date to be entered (e.g., "check-in" or
     *              "check-out")
     * @return the parsed Date object
     */
    private Date getDate(String type) {
        Date date = null;
        try {
            System.out.println("Please enter your desired " + type + " date " +
                    "(YYYY/MM/DD):");
            date = DateFormatter.getDate(this.scanner.next());
            Date now = new Date();
            if (date.before(now)) {
                throw new IllegalArgumentException("Cannot reserve rooms for past" +
                        " dates.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
            date = this.getDate(type);
        }
        return date;
    }

    /**
     * Prompts the user to choose a room and returns the selected IRoom object
     * 
     * @param checkInDate:  the check-in date for the reservation
     * @param checkOutDate: the check-out date for the reservation
     * @return the selected IRoom object
     */
    private IRoom getChosenRoom(Date checkInDate, Date checkOutDate) {
        System.out.println("""
                What do you wish to do?\s
                1. Find an available room\s
                2. Book a specific room""");
        IRoom chosenRoom = null;
        int choice = 0;
        try {
            choice = this.scanner.hasNextInt() ? this.scanner.nextInt()
                    : Integer.parseInt(this.scanner.next());
        } catch (NumberFormatException e) {
            System.out.println("Selection must be a number.");
            return this.getChosenRoom(checkInDate, checkOutDate);
        }
        switch (choice) {
            case 1: {
                Collection<IRoom> availableRooms = this.getAvailableRooms(checkInDate, checkOutDate);
                System.out.println("Available rooms:");
                availableRooms.forEach(room -> {
                    System.out.println(room);
                });
            }
            case 2: {
                System.out.println("Please enter the room number you would " +
                        "like to reserve:");
                String roomNumber = this.scanner.next();
                chosenRoom = this.hotelResource.getRoom(roomNumber);
                break;
            }
            default: {
                System.out.println("Invalid choice. Please choose one of the " +
                        "available options");
                chosenRoom = this.getChosenRoom(checkInDate, checkOutDate);
                break;
            }
        }
        if (chosenRoom == null) {
            System.out.println("The chosen room does not exist. Please try " +
                    "again.");
            chosenRoom = this.getChosenRoom(checkInDate, checkOutDate);
        }
        return chosenRoom;
    }

    /**
     * Retrieves available rooms for the specified check-in and check-out dates
     * 
     * @param checkInDate:  the check-in date for the reservation
     * @param checkOutDate: the check-out date for the reservation
     * @return a Collection of available rooms
     */
    private Collection<IRoom> getAvailableRooms(Date checkInDate,
            Date checkOutDate) {
        Collection<IRoom> availableRooms = this.hotelResource.findARoom(checkInDate, checkOutDate);
        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms for these dates. " +
                    "How many days out would you like to search?");
            int daysOut = 7;
            try {
                daysOut = this.scanner.hasNextInt() ? this.scanner.nextInt() : Integer.parseInt(this.scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Selection must be a number. Defaulting to 7 days.");
            }

            if (daysOut < 1) {
                System.out.println("Days out must be a positive number. " +
                        "Defaulting to 7 days.");
                daysOut = 7;
            }
            Date newCheckInDate = new Date(checkInDate.getTime() + TimeUnit.DAYS.toMillis(daysOut));
            Date newCheckOutDate = new Date(checkOutDate.getTime() + TimeUnit.DAYS.toMillis(daysOut));

            return this.getAvailableRooms(newCheckInDate,
                    newCheckOutDate);
        }
        return availableRooms;
    }

    /**
     * Displays the reservations for the customer associated with the entered email
     */
    private void showOwnReservationList() {
        System.out.println("Please enter your email:");
        String email = this.scanner.next();
        System.out.println("--- RESERVATION LIST FOR " + email + " ---");
        Collection<Reservation> customerReservations = this.adminResource
                .getCustomerReservations(this.adminResource.getCustomer(email));
        if (customerReservations.isEmpty()) {
            System.out.println("No reservations to show");
        } else {
            customerReservations.forEach(reservation -> System.out.println(reservation));
        }
        System.out.println("--- END RESERVATION LIST ---");
    }

    /**
     * Handles the account creation process
     */
    private void handleAccountCreation() {
        System.out.println("\n--- ACCOUNT CREATION ---");
        createCustomer();
        System.out.println("--- END ACCOUNT CREATION\n");
    }

    /**
     * Creates a new customer based on user input
     */
    private void createCustomer() {
        System.out.println("Please insert your email:");
        String email = this.scanner.next();
        if (this.hotelResource.getCustomer(email) != null) {
            System.out.println("There already exists a customer with email " + email + ".");
        } else {
            System.out.println("Please insert your first name:");
            String firstName = this.scanner.next();
            System.out.println("Please insert your last name:");
            String lastName = this.scanner.next();
            try {
                this.hotelResource.createACustomer(email, firstName, lastName);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getLocalizedMessage());
                createCustomer();
            }
            Customer createdCustomer = this.hotelResource.getCustomer(email);
            if (createdCustomer != null) {
                System.out.println("Created customer: " + createdCustomer);
            }
        }
    }

    /**
     * Handles the admin menu interactions
     */
    private void handleAdminInputs() {
        AdminMenu.getInstance(this.scanner).handleInputs();
    }

}
