package model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Class that represents a customer in a hotel.
 * A customer is represented by their first name ({@link #firstName}), last name ({@link #lastName})
 * and email ({@link #email}).
 * @author Cláudia Martins
 */
public class Customer {


    /**
     * Customer's first name
     */
    private String firstName;

    /**
     * Customer's last name
     */
    private String lastName;

    /**
     * Customer's email.
     * Must be unique and match the email@domain.extension format.
     */
    private String email;

    /**
     * Constructor for the Customer class
     * @param firstName: customer's first name
     * @param lastName: customer's last name
     * @param email: customer's email (must be unique)
     * @throws IllegalArgumentException when the email does not match the "email@domain.extension" format
     */
    public Customer(String firstName, String lastName, String email) throws IllegalArgumentException {
        if(!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format. " +
                    "Email must match the format \"email@domain.extension\".");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Retrieves the customer's first name
     * @return the customer's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retrieves the customer's last name
     * @return the customer's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retrieves the customer's email
     * @return the customer's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the customer's full name.
     * The full name is represented by the customer's first and last names with a space between them.
     * @return the customer's full name
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Sets the customer's first name as the name given
     * @param firstName: the customer's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the customer's last name as the name given
     * @param lastName: the customer's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the customer's email as the email given
     * @param email: the customer's email
     * @throws IllegalArgumentException when the email does not match the "email@domain.extension" format
     */
    public void setEmail(String email) throws IllegalArgumentException {
        if(!isValidEmail(email)) {
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    /**
     * Returns a String representation for the customer
     * @return customer represented in String format
     */
    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + ": " + this.email;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(this.getEmail(), customer.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getEmail());
    }


    /**
     * Checks whether a given email is valid.
     * An email is valid if it matches an email@domain.extension pattern.
     * @param email: the email to be validated
     * @return true if the email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        return Pattern.compile("^(.+)[@](.+)[.](.+)$").matcher(email).matches();
    }
}
