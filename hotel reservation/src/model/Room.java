package model;

import java.util.Objects;

/**
 * Class that represents a room in a hotel.
 * A room is represented by its room number ({@link #roomNumber}), price
 * ({@link #roomPrice})
 * and type ({@link #roomType}).
 * @see IRoom
 * @see RoomType
 * @author Cláudia Martins
 */
public class Room implements IRoom {

    /** Small value (epsilon) to compare Double values */
    private static final Double EPSILON = 0.000001d;

    /**
     * Room number
     */
    private String roomNumber;

    /**
     * Room price
     */
    private Double roomPrice;

    /**
     * Room type
     * @see RoomType
     */
    private RoomType roomType;

    /**
     * Constructor for the Room class
     * @param roomNumber: the room number
     * @param roomPrice: the room price
     * @param roomType: the room type
     * @see RoomType
     */
    public Room(String roomNumber, Double roomPrice, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
    }

    /**
     * Retrieves the room number
     * @return the room number
     */
    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    /**
     * Retrieves the room price
     * @return the room price
     */
    @Override
    public Double getRoomPrice() {
        return this.roomPrice;
    }

    /**
     * Retrieves the room type
     * @return the room type
     * @see RoomType
     */
    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    /**
     * Checks if the room is free (price = 0.00)
     * @return true if the room is free, false otherwise
     */
    @Override
    public boolean isFree() {
        return Math.abs(this.roomPrice - 0.00) < EPSILON;
    }

    /**
     * Sets the room number as the given room number
     * @param roomNumber: the room number
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Sets the room price as the given room price
     * @param roomPrice: the room price
     */
    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    /**
     * Sets the room type as the given room type
     * @param roomType: the room type
     * @see RoomType
     */
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    /**
     * Returns a string representation of the room
     * @return a string representation of the room
     */
    @Override
    public String toString() {
        return "Room #" + this.roomNumber + " (" + this.roomType + ", " + String.format("%.2f", this.roomPrice) + "€)";
    }

    /**
     * Checks if two rooms are equal (based on room number)
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Room room = (Room) o;
        return Objects.equals(this.getRoomNumber(), room.getRoomNumber());
    }

    /**
     * Generates a hash code for the room (based on room number)
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.getRoomNumber());
    }
}
