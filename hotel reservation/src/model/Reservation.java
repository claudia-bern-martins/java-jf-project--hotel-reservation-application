package model;

import utils.DateFormatter;

import java.util.Date;
import java.util.Objects;

/**
 * Class that represents a hotel reservation.
 * A reservation is made by a customer ({@link #customer}), for a given room ({@link #room}),
 * within a range of dates defined by a check-in date ({@link #checkInDate})
 * and a check-out date ({@link #checkOutDate})
 * @see Customer
 * @see IRoom
 * @author Cláudia Martins
 */
public class Reservation {

    /**
     * Customer that is making the reservation
     */
    private Customer customer;

    /**
     * Room being reserved
     */
    private IRoom room;

    /**
     * Check-in date for the reservation
     */
    private Date checkInDate;

    /**
     * Check-out date for the reservation
     */
    private Date checkOutDate;

    /**
     * Constructor for the Reservation class
     * @param customer: the customer making the reservation
     * @param room: the room being reserved
     * @param checkInDate: the check-in date (date of arrival in the room)
     * @param checkOutDate: the check-out date (date of departure from the room)
     */
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    /**
     * Retrieves the customer making the reservation
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Retrieves the room being reserved
     * @return the room
     */
    public IRoom getRoom() {
        return room;
    }

    /**
     * Retrieves the check-in/arrival date at the room
     * @return the check-in date
     */
    public Date getCheckInDate() {
        return checkInDate;
    }

    /**
     * Retrieves the check-out/departure date from the room
     * @return the check-out date
     */
    public Date getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * Sets the customer as the one given
     * @param customer: the customer making the reservation
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sets the room as the one given
     * @param room: the room being reserved
     * @see IRoom
     */
    public void setRoom(IRoom room) {
        this.room = room;
    }

    /**
     * Sets the check-in date for the reservation
     * @param checkInDate: the check-in date
     */
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * Sets the check-out date for the reservation
     * @param checkOutDate: the check-out date
     */
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /**
     * Returns a String representation for the reservation
     * @return the reservation in String format
     */
    @Override
    public String toString() {
        return "Reservation: " +
                "[ customer: " + customer.getFullName() +
                ", room: " + room.getRoomNumber() +
                ", check-in: " + DateFormatter.formatDate(checkInDate) +
                ", check-out: " + DateFormatter.formatDate(checkOutDate) +
                " ]";
    }


}
