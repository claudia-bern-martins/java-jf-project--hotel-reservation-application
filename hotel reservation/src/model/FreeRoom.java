package model;

/**
 * Class that represents a free room in a hotel.
 * A free room is represented by its room number ({@link #getRoomNumber()}) and
 * type ({@link #getRoomType()}).
 * The price of a free room is always 0.00.
 * @see Room
 * @author Cláudia Martins
 */
public class FreeRoom extends Room {

    /**
     * Constructor for the FreeRoom class
     * @param roomNumber: the room number
     * @param roomType: the room type
     */
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.00, roomType);
    }

    /**
     * Returns a string representation of the free room
     * @return a string representation of the free room
     */
    @Override
    public String toString() {
        return "Room #" + this.getRoomNumber() + " (" + this.getRoomType() + ", FREE / "
                + String.format("%.2f", 0.00) + " €)";
    }
}
