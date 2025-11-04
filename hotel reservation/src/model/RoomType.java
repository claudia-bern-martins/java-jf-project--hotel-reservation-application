package model;

/**
 * Type of room in a hotel.
 * Can be {@link #SINGLE} or {@link #DOUBLE}
 * 
 * @author Claudia Martins
 */
public enum RoomType {

    /**
     * Single room, meant for one person.
     * Contains one single bed.
     */
    SINGLE,

    /**
     * Double room, meant for two people.
     * Contains one double bed (2 people) or two single beds (1 person each).
     */
    DOUBLE
}
