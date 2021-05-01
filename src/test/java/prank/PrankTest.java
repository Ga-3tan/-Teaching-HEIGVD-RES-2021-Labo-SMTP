package prank;

import prank.Message;
import prank.Person;
import prank.Prank;
import smtp.Mail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

        Assertions.assertEquals(mail.getFrom(), pranker.getAddress());
        Assertions.assertEquals(mail.getTo(), new ArrayList<>(Arrays.asList(p1.getAddress(), p2.getAddress())));
        Assertions.assertEquals(mail.getCCs(), new ArrayList<>(Arrays.asList(p3.getAddress(), p4.getAddress())));
        Assertions.assertEquals(mail.getContent(), m1.getContent());
        Assertions.assertEquals(mail.getSubject(), m1.getSubject());
    }

    @Test
    public void weShouldBeAbleToGenerateAMailFromAPrankWithoutWitnesses() {
        List<Person> victims = new ArrayList<>(Arrays.asList(p1, p2));
        List<String> witnesses = new ArrayList<>();

        Prank prank = new Prank(pranker, victims, witnesses, m2);

        Mail mail = prank.generateMail();

        Assertions.assertEquals(mail.getFrom(), pranker.getAddress());
        Assertions.assertEquals(mail.getTo(), new ArrayList<>(Arrays.asList(p1.getAddress(), p2.getAddress())));
        Assertions.assertEquals(mail.getCCs(), new ArrayList<>());
        Assertions.assertEquals(mail.getContent(), m2.getContent());
        Assertions.assertEquals(mail.getSubject(), m2.getSubject());
    }
}
