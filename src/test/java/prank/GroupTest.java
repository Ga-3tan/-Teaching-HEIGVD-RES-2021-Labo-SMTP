package prank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GroupTest {

    Person p1 = new Person("Zwock", "Getan", "gaetan.imposter@jaimail.com");
    Person p2 = new Person("Moziero", "Mörkö", "marco.imposter@mailmail.com");

    @Test
    public void weShouldBeAbleToAddPersonsToAGroup() {
        Group group = new Group();
        group.addMember(p1);
        group.addMember(p2);

        List<Person> expectedOuput = new ArrayList<>();
        expectedOuput.add(p1);
        expectedOuput.add(p2);

        Assertions.assertEquals(group.getMembers(), expectedOuput);
    }

    @Test
    public void removingNonMemberPersonShouldDoNothing() {
        Group group = new Group();
        group.addMember(p1);
        group.removeMember(p2);

        List<Person> expectedOuput = new ArrayList<>();
        expectedOuput.add(p1);

        Assertions.assertEquals(group.getMembers(), expectedOuput);
    }

    @Test
    public void weShouldBeAbleToRemoveAMemberPerson() {
        Group group = new Group();
        group.addMember(p1);
        group.removeMember(p1);

        Assertions.assertTrue(group.getMembers().isEmpty());
    }
}
