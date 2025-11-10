import api.AdminResource;
import api.HotelResource;
import menu.AdminMenu;
import menu.MainMenu;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class HotelApplication {

    public static void main (String[] args) {
        System.out.println("~ Welcome to the Hotel Reservation Application! ~");
        try (Scanner scanner = new Scanner(System.in)) {
            handleMainInputs(scanner);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void handleMainInputs(Scanner scanner) {
        System.out.println("Please choose an option from the menu below:");
        System.out.println("--- MENU ---");
        printMainMenuOptions();

        int userChoice = scanner.nextInt();
        if(userChoice != MainMenu.EXIT.getCode()) {
            MainMenu choice = null;
            try {
                choice = MainMenu.values()[userChoice - 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Illegal choice. Please select a number from the list " +
                        "(1-" + MainMenu.values().length + ").");
            }

            if (choice != null) {
                System.out.println(choice);
                switch (choice) {
                    case FIND_RESERVE_ROOM: {
                        handleReservationCreation(scanner);
                        break;
                    }
                    case SEE_OWN_RESERVATIONS: {
                        showOwnReservationList();
                        break;
                    }
                    case CREATE_ACCOUNT: {
                        handleAccountCreation(scanner);
                        break;
                    }
                    case OPEN_ADMIN_MENU: {
                        handleAdminInputs(scanner);
                        break;
                    }
                    case EXIT: {
                        System.out.println("Goodbye! We hope to see you again soon.");
                        break;
                    }
                    default:
                        break;
                }
            }
            handleMainInputs(scanner);
        }
    }

    //TODO
    private static void showOwnReservationList() {
    }

    //TODO
    private static void handleReservationCreation(Scanner scanner) {
    }

    private static void handleAdminInputs(Scanner scanner) {
        System.out.println("Please choose an option from the menu below:");
        System.out.println("--- ADMIN MENU ---");
        printAdminMenuOptions();

        int userChoice = scanner.nextInt();
        if(userChoice != AdminMenu.OPEN_MAIN_MENU.getCode()) {
            AdminMenu choice = null;
            try {
                choice = AdminMenu.values()[userChoice - 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Illegal choice. Please select a number from the list " +
                        "(1-" + AdminMenu.values().length + ").");
            }

            if (choice != null) {
                System.out.println(choice);
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
                        handleRoomCreation(scanner);
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
            handleAdminInputs(scanner);
        }
    }

    private static void handleRoomCreation(Scanner scanner) {
        System.out.println("--- ROOM CREATION ---");
        List<IRoom> newRooms = new ArrayList<>();
        System.out.println("How many rooms do you wish to add?");
        int numberOfRooms = scanner.nextInt();
        int i = 0;
        while(i < numberOfRooms) {
            IRoom newRoom = createNewRoom(scanner, i+1);
            newRooms.add(newRoom);
            System.out.println("Created room: " + newRoom);
            i++;
        }
        AdminResource adminResource = AdminResource.getInstance();
        adminResource.addRoom(newRooms);
        System.out.println("--- END ROOM CREATION ---");
    }

    private static IRoom createNewRoom(Scanner scanner, int iteration) {
        System.out.println("--- Room " + iteration);
        System.out.println("Please insert the room number:");
        String roomNumber = scanner.next();
        System.out.println("Please insert the price:");
        double price = scanner.hasNextDouble() ? scanner.nextDouble() : Double.parseDouble(scanner.next());
        System.out.println("Please select the room type:\n1. Single room\n2. Double room");
        int typeNumber = scanner.nextInt();
        if(typeNumber < 0 || typeNumber > 2) {
            System.err.println("Invalid choice. A room can either be single (1) or double (2).\n"
                    + "Please try creating the room again.");
            createNewRoom(scanner, iteration);
        }
        RoomType type = typeNumber == 1 ? RoomType.SINGLE : RoomType.DOUBLE;
        return price > 0.0 ? new Room(roomNumber, price, type) : new FreeRoom(roomNumber, type);
    }

    private static void handleAccountCreation(Scanner scanner) {
        System.out.println("--- ACCOUNT CREATION ---");
        System.out.println("Please insert your email:");
        String email = scanner.next();
        HotelResource hotelResource = HotelResource.getInstance();
        if(hotelResource.getCustomer(email) != null) {
            System.err.println("There already exists a customer with email " + email + ".");
        } else {
            System.out.println("Please insert your first name:");
            String firstName = scanner.next();
            System.out.println("Please insert your last name:");
            String lastName = scanner.next();
            try {
                hotelResource.createACustomer(email, firstName, lastName);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
                handleAccountCreation(scanner);
            }
            System.out.println("Created customer: " + hotelResource.getCustomer(email));
            System.out.println("--- END ACCOUNT CREATION");
        }
    }



    private static void showRoomList() {
        AdminResource adminResource = AdminResource.getInstance();
        System.out.println("--- ROOM LIST ---");
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        if(allRooms.isEmpty()) {
            System.out.println("No rooms to show.");
        } else {
            for(IRoom room : allRooms) {
                System.out.println(room);
            }
        }
        System.out.println("--- END ROOM LIST ---");
    }

    private static void showCustomerList() {
        AdminResource adminResource = AdminResource.getInstance();
        System.out.println("--- CUSTOMER LIST ---");
        Collection<Customer> allCustomers = adminResource.getAllCustomers();
        if(allCustomers.isEmpty()) {
            System.out.println("No customers to show.");
        } else {
            for(Customer customer : allCustomers) {
                System.out.println(customer);
            }
        }
        System.out.println("--- END CUSTOMER LIST ---");
    }

    private static void showReservations() {
        AdminResource adminResource = AdminResource.getInstance();
        adminResource.displayAllReservations();
    }

    private static void printMainMenuOptions() {
        for(MainMenu item : MainMenu.values()) {
            System.out.println(item);
        }
    }

    private static void printAdminMenuOptions() {
        for(AdminMenu item : AdminMenu.values()) {
            System.out.println(item);
        }
    }

}
