package prank;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a group of persons
 *
 * Name : Group
 * File : Group.java
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 01.05.2021
 */
@Getter
public class Group {
    private final List<Person> members = new ArrayList<>();

    /**
     * Adds a person to the list
     * @param p The person to add
     */
    public void addMember(Person p) {
        members.add(p);
    }

    /**
     * Removes a person from the list
     * @param p The person to remove
     */
    public void removeMember(Person p) {
        members.remove(p);
    }
}