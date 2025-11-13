package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

/**
 * Singleton class that provides an interface for admin operations in a hotel
 * reservation application
 * @see CustomerService
 * @see ReservationService
 * @author Cláudia Martins
 */
public final class AdminResource {

    /**
     * The singleton instance of AdminResource
     */
    private static AdminResource ADMIN_RESOURCE;

    /**
     * The CustomerService instance for managing customer-related operations
     */
    private final CustomerService customerService;

    /**
     * The ReservationService instance for managing reservation-related operations
     */
    private final ReservationService reservationService;

    /**
     * Private constructor to prevent instantiation from outside the class
     */
    private AdminResource() {
        this.customerService = CustomerService.getInstance();
        this.reservationService = ReservationService.getInstance();
    }

    /**
     * Provides access to the singleton instance of AdminResource
     * @return the singleton instance of AdminResource
     */
    public static AdminResource getInstance() {
        if (ADMIN_RESOURCE == null) {
            ADMIN_RESOURCE = new AdminResource();
        }
        return ADMIN_RESOURCE;
    }

    /**
     * Retrieves a customer by their email address
     * @param email: the email address of the customer
     * @return the Customer associated with the given email
     */
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    /**
     * Adds a list of rooms to the hotel
     * @param rooms: the list of rooms to be added
     */
    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            this.reservationService.addRoom(room);
        }
    }

    /**
     * Retrieves all rooms
     * @return a Collection of all rooms
     */
    public Collection<IRoom> getAllRooms() {
        return this.reservationService.getAllRooms();
    }

    /**
     * Retrieves all customers
     * @return a Collection of all customers
     */
    public Collection<Customer> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    /**
     * Displays all reservations
     */
    public void displayAllReservations() {
        this.reservationService.printAllReservation();
    }

    /**
     * Retrieves all the reservations a customer has made
     * @param customer: the customer that has made the reservations
     * @return a collection of the reservations the customer has made
     */
    public Collection<Reservation> getCustomerReservations(Customer customer) {
        return this.reservationService.getCustomersReservation(customer);
    }
}
