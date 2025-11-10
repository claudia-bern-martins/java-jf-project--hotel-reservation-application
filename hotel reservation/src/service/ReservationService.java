package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import utils.DateFormatter;

import java.util.*;

/**
 * Service class for managing reservations in the hotel reservation application.
 * Implements the singleton design pattern to ensure a single instance is always
 * returned.
 * @see Reservation
 * @see IRoom
 * @see Customer
 * @author Cláudia Martins
 */
public final class ReservationService {

    /**
     * Singleton instance of ReservationService
     */
    private static ReservationService RESERVATION_SERVICE;

    /**
     * Map to store rooms by their room numbers
     */
    private final Map<String, IRoom> rooms;

    /**
     * Map to store reservations by room numbers
     */
    private final Map<String, List<Reservation>> reservations;

    /**
     * Private constructor to prevent outside instantiation
     */
    private ReservationService() {
        this.rooms = new HashMap<>();
        this.reservations = new HashMap<>();
    }

    /**
     * Retrieves the singleton instance of ReservationService, creating it first if it does not yet exist
     * @return the singleton instance of ReservationService
     */
    public static synchronized ReservationService getInstance() {
        if (RESERVATION_SERVICE == null) {
            RESERVATION_SERVICE = new ReservationService();
        }
        return RESERVATION_SERVICE;
    }

    /**
     * Adds a new room to the system if a room with the given room number does not
     * already exist
     * @param room: the room to be added
     * @throws IllegalArgumentException if a room with the given room number already exists
     */
    public void addRoom(IRoom room) throws IllegalArgumentException {
        String roomNumber = room.getRoomNumber();
        if (this.getARoom(roomNumber) == null) {
            this.rooms.put(roomNumber, room);
        } else {
            throw new IllegalArgumentException("A room with room number " + roomNumber + " already exists.");
        }
    }

    /**
     * Retrieves a room by its room number
     * @param roomId: the room number of the room
     * @return the room associated with the given room number, or null if no such room exists
     */
    public IRoom getARoom(String roomId) {
        return this.rooms.get(roomId);
    }

    /**
     * Reserves a room for a customer for the specified check-in and check-out dates
     * @param customer: the customer making the reservation
     * @param room: the room being reserved
     * @param checkInDate: the check-in date
     * @param checkOutDate: the check-out date
     * @return the created reservation
     * @throws IllegalArgumentException if the room is already reserved for the given dates
     */
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate)
            throws IllegalArgumentException {
        List<Reservation> roomReservations = this.reservations.computeIfAbsent(
                room.getRoomNumber(), k -> new ArrayList<>());
        for (Reservation reservation : roomReservations) {
            if (isDateInRange(checkInDate, reservation.getCheckInDate(), reservation.getCheckOutDate()) ||
                    isDateInRange(checkOutDate, reservation.getCheckInDate(), reservation.getCheckOutDate()) ||
                    isDateInRange(reservation.getCheckInDate(), checkInDate, checkOutDate) ||
                    isDateInRange(reservation.getCheckOutDate(), checkInDate, checkOutDate)) {
                throw new IllegalArgumentException("This room already has a reservation in place for " +
                        "the chosen dates (" + DateFormatter.formatDate(checkInDate) + " - "
                        + DateFormatter.formatDate(checkOutDate) + "): " + reservation);
            }
        }
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        roomReservations.add(reservation);
        return reservation;
    }

    /**
     * Retrieves all reservations made by a specific customer
     * @param customer: the customer whose reservations are to be retrieved
     * @return a collection of reservations made by the specified customer
     */
    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> reservationList = new ArrayList<>();
        for (String roomNumber : this.reservations.keySet()) {
            for (Reservation reservation : this.reservations.computeIfAbsent(
                    roomNumber, k -> new ArrayList<>())) {
                if (reservation.getCustomer().equals(customer)) {
                    reservationList.add(reservation);
                }
            }
        }
        reservationList.sort(Comparator.comparing(o -> o.getCheckInDate().getTime()));
        return reservationList;
    }

    /**
     * Prints all the reservations in the application
     */
    public void printAllReservation() {
        System.out.println("---   CURRENT RESERVATIONS ---");
        if(this.reservations.isEmpty()) {
            System.out.println("No reservations to show.");
        } else {
            for (String roomNumber : this.reservations.keySet()) {
                System.out.println("--- --- ROOM " + roomNumber + " --- ---");
                for (Reservation reservation : this.reservations.computeIfAbsent(
                        roomNumber, k -> new ArrayList<>())) {
                    System.out.println(reservation);
                }
            }
        }
        System.out.println("---   END CURRENT RESERVATIONS ---");
    }

    /**
     * Retrieves all available rooms for the specified check-in and check-out dates
     * @param checkInDate: the desired check-in date
     * @param checkOutDate: the desired check-out date
     * @return a collection of available rooms for the given dates
     */
    public Collection<IRoom> getAvailableRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : this.getAllRooms()) {
            List<Reservation> reservations = this.reservations.computeIfAbsent(
                    room.getRoomNumber(), k -> new ArrayList<>());
            if(!reservations.isEmpty()) {
                for (Reservation reservation : reservations) {
                    if (!isDateInRange(checkInDate, reservation.getCheckInDate(), reservation.getCheckOutDate()) &&
                            !isDateInRange(checkOutDate, reservation.getCheckInDate(), reservation.getCheckOutDate()) &&
                            !isDateInRange(reservation.getCheckInDate(), checkInDate, checkOutDate) &&
                            !isDateInRange(reservation.getCheckOutDate(), checkInDate, checkOutDate)) {
                        availableRooms.add(room);
                    }
                }
            } else {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    /**
     * Retrieves a Collection of all the rooms in the hotel
     * @return a collection of all the rooms
     */
    public Collection<IRoom> getAllRooms() {
        List<IRoom> rooms = new ArrayList<>(this.rooms.values().stream().toList());
        rooms.sort(Comparator.comparing(IRoom::getRoomNumber));
        return rooms;
    }

    /**
     * Checks if a given date falls within a specified date range (inclusive)
     * @param date: the date to check
     * @param beginRangeDate: the start date of the range
     * @param endRangeDate: the end date of the range
     * @return true if the date is within the range, false otherwise
     */
    private boolean isDateInRange(Date date, Date beginRangeDate, Date endRangeDate) {
        return (date.after(beginRangeDate) && date.before(endRangeDate))
                || date.getTime() == beginRangeDate.getTime()
                || date.getTime() == endRangeDate.getTime();
    }

}
