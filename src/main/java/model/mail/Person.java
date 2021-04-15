package model.mail;

import lombok.Getter;

@Getter
public class Person {
    private final String lastName;
    private final String firstName;
    private final String address;

    public Person(String nom, String prenom, String email) {
        this.lastName = nom;
        this.firstName = prenom;
        this.address = email;
    }
}
