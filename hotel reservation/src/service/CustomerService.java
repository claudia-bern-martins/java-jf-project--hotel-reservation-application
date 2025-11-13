package service;

import model.Customer;

import java.util.*;

/**
 * Service class for managing customers in the hotel reservation application.
 * Implements the singleton design pattern to ensure a single instance is always
 * returned.
 * @see Customer
 * @author Cláudia Martins
 */
public final class CustomerService {

    /**
     * Singleton instance of CustomerService
     */
    private static CustomerService CUSTOMER_SERVICE;

    /**
     * Map to store customers by their email addresses
     */
    private final Map<String, Customer> customers;

    /**
     * Private constructor to prevent outside instantiation
     */
    private CustomerService() {
        this.customers = new HashMap<>();
    }

    /**
     * Retrieves the singleton instance of CustomerService, creating it first if it
     * does not yet exist.
     * @return the singleton instance of CustomerService
     */
    public static synchronized CustomerService getInstance() {
        if (CUSTOMER_SERVICE == null) {
            CUSTOMER_SERVICE = new CustomerService();
        }
        return CUSTOMER_SERVICE;
    }

    /**
     * Adds a new customer to the system if a customer with the given email does not
     * already exist.
     * @param email: the email address of the customer
     * @param firstName: the first name of the customer
     * @param lastName: the last name of the customer
     */
    public void addCustomer(String email, String firstName, String lastName) {
        this.customers.putIfAbsent(email, new Customer(firstName, lastName,
                email));
    }

    /**
     * Retrieves a customer by their email address.
     * @param email the email address of the customer
     * @return the customer associated with the given email, or null if no such
     * customer exists
     */
    public Customer getCustomer(String email) {
        return this.customers.get(email);
    }

    /**
     * Retrieves all the customers in the application.
     * @return a collection of all the customers
     */
    public Collection<Customer> getAllCustomers() {
        List<Customer> allCustomers = new ArrayList<>(this.customers.values().stream().toList());
        allCustomers.sort(Comparator.comparing(Customer::getFullName));
        return allCustomers;
    }
}
