package model;

/**
 * Interface that represents a room in a hotel.
 * A room is represented by its room number ({@link #getRoomNumber()}), price
 * ({@link #getRoomPrice()}) and type ({@link #getRoomType()}).
 * @see RoomType
 * @author Cláudia Martins
 */
public interface IRoom {

    /**
     * Retrieves the room number
     * @return the room number
     */
    public String getRoomNumber();

    /**
     * Retrieves the room price
     * @return the room price
     */
    public Double getRoomPrice();

    /**
     * Retrieves the room type (SINGLE or DOUBLE)
     * @see RoomType
     * @return the room type
     */
    public RoomType getRoomType();

    /**
     * Checks if the room is free (price = 0.00)
     * @return true if the room is free, false otherwise
     */
    public boolean isFree();
}
