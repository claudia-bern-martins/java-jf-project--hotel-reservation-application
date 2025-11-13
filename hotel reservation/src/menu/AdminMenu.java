package menu;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Singleton class that handles the admin menu interactions in a hotel
 * reservation application
 * @author Cláudia Martins
 */
public final class AdminMenu {

    /**
     * The singleton instance of AdminMenu
     */
    private static AdminMenu ADMIN_MENU;
    /**
     * The HotelResource instance for managing hotel-related operations
     */
    private final HotelResource hotelResource;
    /**
     * The AdminResource instance for managing admin-related operations
     */
    private final AdminResource adminResource;
    /**
     * Scanner for reading user input
     */
    private final Scanner scanner;

    /**
     * Private constructor to prevent instantiation from outside the class
     * @param sc: the Scanner instance for reading user input
     */
    private AdminMenu(Scanner sc) {
        this.scanner = sc;
        this.hotelResource = HotelResource.getInstance();
        this.adminResource = AdminResource.getInstance();
    }

    /**
     * Provides access to the singleton instance of AdminMenu
     * @param sc: the Scanner instance for reading user input
     * @return the singleton instance of AdminMenu
     */
    public static synchronized AdminMenu getInstance(Scanner sc) {
        if (ADMIN_MENU == null) {
            ADMIN_MENU = new AdminMenu(sc);
        }
        return ADMIN_MENU;
    }

    /**
     * Handles the admin menu interactions
     */
    void handleInputs() {
        System.out.println("Please choose an option from the menu below:");
        System.out.println("--- ADMIN MENU ---");
        printMenuOptions();

        int userChoice = this.scanner.nextInt();
        if (userChoice != AdminMenuOptions.OPEN_MAIN_MENU.getCode()) {
            AdminMenuOptions choice = null;
            try {
                choice = AdminMenuOptions.values()[userChoice - 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Illegal choice. Please select a number from the list " +
                        "(1-" + AdminMenuOptions.values().length + ").");
            }

            if (choice != null) {
                switch (choice) {
                    case SEE_CUSTOMERS: {
                        showCustomerList();
                        break;
                    }
                    case SEE_ROOMS: {
                        showRoomList();
                        break;
                    }
                    case SEE_RESERVATIONS: {
                        showReservations();
                        break;
                    }
                    case ADD_ROOM: {
                        handleRoomCreation();
                        break;
                    }
                    case POPULATE: {
                        System.out.println("Populating the hotel with test " +
                                "data...");
                        populateWithTestData();
                        break;
                    }
                    case OPEN_MAIN_MENU: {
                        System.out.println("Returning to Main Menu...");
                        return;
                    }
                    default:
                        break;
                }
            }
            handleInputs();
        }
    }

    /**
     * Prints the admin menu options to the console
     */
    private void printMenuOptions() {
        Arrays.stream(AdminMenuOptions.values()).forEach(
                option -> System.out.println(option));
    }

    /**
     * Displays the list of all customers
     */
    private void showCustomerList() {
        System.out.println("\n--- CUSTOMER LIST ---");
        Collection<Customer> allCustomers = this.adminResource.getAllCustomers();
        if (allCustomers.isEmpty()) {
            System.out.println("No customers to show.");
        } else {
            for (Customer customer : allCustomers) {
                System.out.println(customer);
            }
        }
        System.out.println("--- END CUSTOMER LIST ---\n");
    }

    /**
     * Displays the list of all rooms
     */
    private void showRoomList() {
        System.out.println("\n--- ROOM LIST ---");
        Collection<IRoom> allRooms = this.adminResource.getAllRooms();
        if (allRooms.isEmpty()) {
            System.out.println("No rooms to show.");
        } else {
            for (IRoom room : allRooms) {
                System.out.println(room);
            }
        }
        System.out.println("--- END ROOM LIST ---\n");
    }

    /**
     * Displays all reservations
     */
    private void showReservations() {
        this.adminResource.displayAllReservations();
    }

    /**
     * Handles the room creation process
     */
    private void handleRoomCreation() {
        System.out.println("\n--- ROOM CREATION ---");
        List<IRoom> newRooms = new ArrayList<>();
        System.out.println("How many rooms do you wish to add?");
        int numberOfRooms = this.scanner.nextInt();
        int i = 0;
        while (i < numberOfRooms) {
            IRoom newRoom = createNewRoom(i + 1);
            newRooms.add(newRoom);
            System.out.println("Created room: " + newRoom);
            i++;
        }
        this.adminResource.addRoom(newRooms);
        System.out.println("--- END ROOM CREATION ---\n");
    }

    /**
     * Creates a new room based on user input
     * @param iteration: the current iteration number for room creation
     * @return the created IRoom instance
     */
    private IRoom createNewRoom(int iteration) {
        System.out.println("--- Room " + iteration);

        System.out.println("Please insert the room number:");
        String roomNumber = this.scanner.next();

        System.out.println("Please insert the price:");
        double price = this.scanner.hasNextDouble() ? this.scanner.nextDouble()
                : Double.parseDouble(this.scanner.next());

        System.out.println("Please select the room type:\n1. Single room\n2. Double room");
        int typeNumber = this.scanner.nextInt();
        if (typeNumber < 0 || typeNumber > 2) {
            System.out.println("Invalid choice. A room can either be single (1) or double (2).\n"
                    + "Please try creating the room again.");
            createNewRoom(iteration);
        }

        RoomType type = typeNumber == 1 ? RoomType.SINGLE : RoomType.DOUBLE;
        return price > 0.0 ? new Room(roomNumber, price, type) : new FreeRoom(roomNumber, type);
    }

    /**
     * Populates the hotel with test data
     */
    private void populateWithTestData() {
        System.out.println("--- POPULATING WITH TEST DATA --- ");

        this.hotelResource.createACustomer("alice@email.com", "Alice",
                "Anderson");
        this.hotelResource.createACustomer("bob@email.com", "Bob",
                "Brown");
        this.hotelResource.createACustomer("carol@email.com", "Carol",
                "Carter");
        System.out.println("--- CREATED CUSTOMERS --- ");
        this.adminResource.getAllCustomers().forEach(customer -> System.out.println(customer));

        List<IRoom> rooms = new ArrayList<>();
        rooms.add(new Room("101", 50.00, RoomType.SINGLE));
        rooms.add(new Room("102", 75.00, RoomType.DOUBLE));
        rooms.add(new FreeRoom("103", RoomType.SINGLE));
        rooms.add(new Room("201", 100.00, RoomType.DOUBLE));
        rooms.add(new Room("202", 25.00, RoomType.SINGLE));
        this.adminResource.addRoom(rooms);
        System.out.println("--- CREATED ROOMS --- ");
        this.adminResource.getAllRooms().forEach(room -> System.out.println(room));

        this.hotelResource.bookARoom("alice@email.com",
                this.hotelResource.getRoom("101"),
                new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(30)),
                new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(33)));
        this.hotelResource.bookARoom("bob@email.com",
                this.hotelResource.getRoom("103"),
                new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(31)),
                new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(41)));
        this.adminResource.displayAllReservations();

        System.out.println("--- END POPULATING WITH TEST DATA --- ");
    }
}
