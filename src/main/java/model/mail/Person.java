package model.mail;

import lombok.Getter;

@Getter
public class Person {
    private final String lastName;
    private final String firstName;
    private final String address;

    public Person(String firstname, String lastname, String email) {
        this.lastName = lastname;
        this.firstName = firstname;
        this.address = email;
    }
}
