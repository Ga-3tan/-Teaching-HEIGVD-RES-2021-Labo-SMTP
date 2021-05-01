package model.prank;

import model.mail.Mail;
import model.mail.Message;
import model.mail.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrankTest {

    Person p1 = new Person("Zwock", "Getan", "gaetan.imposter@jaimail.com");
    Person p2 = new Person("Moziero", "Mörkö", "marco.imposter@mailmail.com");
    Person p3 = new Person("E", "E", "ee@eemail.yeet");
    Person p4 = new Person("Binks", "JarJar", "exiled@gungan.oof");
    Person pranker = new Person("Pranker", "The", "the.pranker@prank.com");

    Message m1 = new Message("totally not a prank", "Q: What do you call a busy waiter?\n\nA: A server.");
    Message m2 = new Message("important", "REDS is an evil secret organisation whose goal is to control the world");

    @Test
    public void weShouldBeAbleToGenerateAMailFromANormalPrank() {
        List<Person> victims = new ArrayList<>(Arrays.asList(p1, p2));
        List<String> witnesses = new ArrayList<>(Arrays.asList(p3.getAddress(), p4.getAddress()));

        Prank prank = new Prank(pranker, victims, witnesses, m1);

        Mail mail = prank.generateMail();

        assert (mail.getFrom().equals(pranker.getAddress()));
        assert (mail.getTo().equals(new ArrayList<>(Arrays.asList(p1.getAddress(), p2.getAddress()))));
        assert (mail.getCCs().equals(witnesses));
        assert (mail.getContent().equals(m1.getContent()) &&
                mail.getSubject().equals(m1.getSubject()));
    }

    @Test
    public void weShouldBeAbleToGenerateAMailFromAPrankWithoutWitnesses() {
        List<Person> victims = new ArrayList<>(Arrays.asList(p1, p2));
        List<String> witnesses = new ArrayList<>();

        Prank prank = new Prank(pranker, victims, witnesses, m2);

        Mail mail = prank.generateMail();

        assert (mail.getFrom().equals(pranker.getAddress()));
        assert (mail.getTo().equals(new ArrayList<>(Arrays.asList(p1.getAddress(), p2.getAddress()))));
        assert (mail.getCCs().equals(new ArrayList<>()));
        assert (mail.getContent().equals(m2.getContent()) &&
                mail.getSubject().equals(m2.getSubject()));
    }

    @Test
    public void weShouldntBeAbleToGenerateAMailFromAPrankWithoutVictims() {
        List<Person> victims = new ArrayList<>();
        List<String> witnesses = new ArrayList<>(Arrays.asList(p3.getAddress(), p4.getAddress()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Prank(pranker, victims, witnesses, m2));
    }

    @Test
    public void weShouldntBeAbleToGenerateAMailFromAPrankWithoutSender() {
        List<Person> victims = new ArrayList<>(Arrays.asList(p1, p2));
        List<String> witnesses = new ArrayList<>(Arrays.asList(p3.getAddress(), p4.getAddress()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Prank(null, victims, witnesses, m2));
    }

    @Test
    public void weShouldntBeAbleToGenerateAMailFromAPrankWithoutAMessageObject() {
        List<Person> victims = new ArrayList<>(Arrays.asList(p1, p2));
        List<String> witnesses = new ArrayList<>(Arrays.asList(p3.getAddress(), p4.getAddress()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Prank(pranker, victims, witnesses, null));
    }

}
