package service;

import model.Customer;
import model.Room;
import model.RoomType;
import utils.DateFormatter;

/**
 * Tester class for CustomerService and ReservationService
 * @see CustomerService
 * @see ReservationService
 * @author Cláudia Martins
 */
public class ServiceTester {

    /**
     * Main method to test CustomerService and ReservationService functionalities
     * @param args: command line arguments
     */
    public static void main(String[] args) {
        CustomerService cs = CustomerService.getInstance();
        testCustomerService(cs);

        ReservationService rs = ReservationService.getInstance();
        testReservationService(rs);
    }

    /**
     * Tests the CustomerService functionalities
     * @param cs: the CustomerService instance to test
     */
    private static void testCustomerService(CustomerService cs) {
        System.out.println(cs.getAllCustomers());

        cs.addCustomer("jane@smith.org", "Jane", "Smith");
        System.out.println(cs.getCustomer("jane@smith.org"));

        cs.addCustomer("jdoe@something.com", "John", "Doe");
        System.out.println(cs.getAllCustomers());
    }

    /**
     * Tests the ReservationService functionalities
     * @param rs: the ReservationService instance to test
     */
    private static void testReservationService(ReservationService rs) {
        System.out.println(
                rs.getAvailableRooms(DateFormatter.getDate("2025/11/02"), DateFormatter.getDate("2025/11/05")));
        Room singleRoom = new Room("101", 50.0, RoomType.SINGLE);
        rs.addRoom(singleRoom);

        try {
            rs.addRoom(new Room("101", 100.0, RoomType.DOUBLE));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
        }

        rs.addRoom(new Room("102", 50.0, RoomType.SINGLE));
        rs.addRoom(new Room("103", 50.0, RoomType.SINGLE));
        rs.addRoom(new Room("104", 50.0, RoomType.SINGLE));
        Room doubleRoom = new Room("105", 100.0, RoomType.DOUBLE);

        rs.addRoom(doubleRoom);
        System.out.println(rs.getARoom("105"));
        System.out.println(rs.getAvailableRooms(DateFormatter.getDate("2025/11/02"),
                DateFormatter.getDate("2025/11/05")));

        Customer customerJane = new Customer("Jane", "Smith", "jane@smith.org");
        Customer customerJohn = new Customer("John", "Doe", "jdoe@something.com");
        rs.reserveARoom(customerJane, singleRoom, DateFormatter.getDate("2025/11/02"),
                DateFormatter.getDate("2025/11/05"));
        System.out.println(rs.getAvailableRooms(DateFormatter.getDate("2025/11/02"),
                DateFormatter.getDate("2025/11/05")));

        rs.reserveARoom(customerJohn, doubleRoom, DateFormatter.getDate("2025/12/01"),
                DateFormatter.getDate("2025/12/02"));
        System.out.println(rs.getAvailableRooms(DateFormatter.getDate("2025/11/02"),
                DateFormatter.getDate("2025/11/05")));
        System.out.println(rs.getAvailableRooms(DateFormatter.getDate("2025/12/01"),
                DateFormatter.getDate("2025/12/02")));

        try {
            rs.reserveARoom(customerJohn, singleRoom, DateFormatter.getDate("2025/11/03"),
                    DateFormatter.getDate("2025/11/06"));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
        }

        try {
            rs.reserveARoom(customerJohn, singleRoom, DateFormatter.getDate("2025/10/03"),
                    DateFormatter.getDate("2025/11/02"));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
        }

        try {
            rs.reserveARoom(customerJohn, singleRoom, DateFormatter.getDate("2025/11/05"),
                    DateFormatter.getDate("2025/11/11"));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
        }

        try {
            rs.reserveARoom(customerJohn, singleRoom, DateFormatter.getDate("2025/10/03"),
                    DateFormatter.getDate("2025/11/11"));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
        }

        rs.printAllReservation();

        rs.reserveARoom(customerJohn, singleRoom, DateFormatter.getDate("2025/11/25"),
                DateFormatter.getDate("2025/11/27"));
        System.out.println(rs.getCustomersReservation(customerJohn));
    }
}
