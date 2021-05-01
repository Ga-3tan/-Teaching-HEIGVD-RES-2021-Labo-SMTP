package prank;

import lombok.Getter;

/**
 * Represents a person
 *
 * Name : Person
 * File : Person.java
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 01.05.2021
 */
@Getter
public class Person {
    private final String lastName;
    private final String firstName;
    private final String address;

    /**
     * Constructs a new person
     * @param firstname The first name
     * @param lastname The last name
     * @param email The person email
     */
    public Person(String firstname, String lastname, String email) {
        this.lastName = lastname;
        this.firstName = firstname;
        this.address = email;
    }
}
