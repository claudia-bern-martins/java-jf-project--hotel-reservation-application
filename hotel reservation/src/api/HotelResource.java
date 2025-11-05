package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

/**
 * Singleton class that provides an interface for hotel-related operations,
 * such as managing customers and reservations in a hotel reservation
 * application
 * @see CustomerService
 * @see ReservationService
 * @author Cláudia Martins
 */
public final class HotelResource {

    /**
     * The singleton instance of HotelResource
     */
    private static HotelResource HOTEL_RESOURCE;

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
    private HotelResource() {
        this.customerService = CustomerService.getInstance();
        this.reservationService = ReservationService.getInstance();
    }

    /**
     * Provides access to the singleton instance of HotelResource
     * @return the singleton instance of HotelResource
     */
    public static HotelResource getInstance() {
        if (HOTEL_RESOURCE == null) {
            HOTEL_RESOURCE = new HotelResource();
        }
        return HOTEL_RESOURCE;
    }

    /**
     * Retrieves a customer by their email address
     * @param email: the email address of the customer
     * @return the customer associated with the given email address, or null if no
     * such customer exists
     */
    public Customer getCustomer(String email) {
        return this.customerService.getCustomer(email);
    }

    /**
     * Creates a new customer with the given email, first name, and last name
     * @param email: the email address of the customer
     * @param firstName: the first name of the customer
     * @param lastName: the last name of the customer
     */
    public void createACustomer(String email, String firstName, String lastName) {
        this.customerService.addCustomer(email, firstName, lastName);
    }

    /**
     * Retrieves a room by its room number
     * @param roomNumber: the room number
     * @return the room associated with the given room number, or null if no such
     * room exists
     */
    public IRoom getRoom(String roomNumber) {
        return this.reservationService.getARoom(roomNumber);
    }

    /**
     * Books a room for a customer with the specified email, room, check-in date,
     * and check-out date
     * @param customerEmail: the email address of the customer
     * @param room: the room to be booked
     * @param checkInDate: the check-in date for the reservation
     * @param checkOutDate: the check-out date for the reservation
     * @return the reservation
     */
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return this.reservationService.reserveARoom(this.getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    /**
     * Retrieves a collection of the available rooms for a reservation in the hotel between two dates
     * @param checkIn: the check-in date for the reservation
     * @param checkOut: the check-out date for the reservation
     * @return a Collection of available rooms between the two dates
     */
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return this.reservationService.getAvailableRooms(checkIn, checkOut);
    }
}
