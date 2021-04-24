package model.prank;

import lombok.Getter;
import model.mail.Person;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Group {
    private final List<Person> members = new ArrayList<>();
    public void addMember(Person p) {
        members.add(p);
    }
    public void removeMember(Person p) {
        members.remove(p);
    }
}