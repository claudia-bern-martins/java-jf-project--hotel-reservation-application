package model;

import utils.DateFormatter;

import java.util.Date;

/**
 * Class to test the classes present in the model package
 * @author Cláudia Martins
 */
public class ModelTester {

    /**
     * Main method to test the classes present in the model package
     * @param args: command line arguments
     */
    public static void main(String[] args) {
        String wrongEmail = "email@email";
        try {
            Customer wrongCustomer = new Customer("first", "second", wrongEmail);
            System.out.println(wrongCustomer);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage() + " Input given: " + wrongEmail);
        }

        Customer customerJohn = new Customer("John", "Doe", "jdoe@something.com");
        System.out.println(customerJohn);

        Customer customerJane = new Customer("Jane", "Smith", "jane@smith.org");
        System.out.println(customerJane);

        Room room = new Room("101", 200.00, RoomType.DOUBLE);
        System.out.println(room + ", isFree: " + room.isFree());

        Room freeRoom = new FreeRoom("001", RoomType.SINGLE);
        System.out.println(freeRoom + ", isFree: " + freeRoom.isFree());

        try {
            Reservation reservationJohn = new Reservation(customerJohn, room, DateFormatter.getDate("2025-11-11 09:00"),
                    DateFormatter.getDate("2025-11-13 11:00"));
            System.out.println(reservationJohn);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
        }

        try {
            Reservation reservationJane = new Reservation(customerJane, freeRoom, new Date(),
                    DateFormatter.getDate("2026/01/01"));
            System.out.println(reservationJane);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
