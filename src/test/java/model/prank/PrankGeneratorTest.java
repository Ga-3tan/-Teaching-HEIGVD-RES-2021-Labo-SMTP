package model.prank;

import config.IConfigurationManager;
import model.mail.Mail;
import model.mail.Message;
import model.mail.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PrankGeneratorTest {

    Person p1 = new Person("Zwock", "Getan", "gaetan.imposter@jaimail.com");
    Person p2 = new Person("Moziero", "Mörkö", "marco.imposter@mailmail.com");
    Person p3 = new Person("E", "E", "ee@eemail.yeet");
    Person p4 = new Person("Binks", "JarJar", "exiled@gungan.oof");
    Person p5 = new Person("Pranker", "The", "the.pranker@prank.com");
    Person p6 = new Person("Pranker2", "The", "the.pranker2@prank.com");
    Person p7 = new Person("Pranker3", "The", "the.pranker3@prank.com");

    ArrayList<Person> victims;

    Message m1 = new Message("totally not a prank", "Q: What do you call a busy waiter?\n\nA: A server.");
    Message m2 = new Message("important", "REDS is an evil secret organisation whose goal is to control the world");

    ArrayList<Message> messages;

    ArrayList<String> witnessToCC;

    int numberOfGroups;

    IConfigurationManager config = new IConfigurationManager() {
        @Override
        public String getSmtpServerAddress() {
            return "127.0.0.1";
        }

        @Override
        public int getSmtpServerPort() {
            return 25;
        }

        @Override
        public int getNumberOfGroup() {
            return numberOfGroups;
        }

        @Override
        public List<Person> getVictims() {
            return victims;
        }

        @Override
        public List<Message> getMessages() {
            return messages;
        }

        @Override
        public List<String> getWitnessesToCC() {
            return witnessToCC;
        }
    };

    @Test
    public void weShouldBeAbleToGenerateAListOfCorrectsPranks() {
        victims = new ArrayList<>(Arrays.asList(p1, p2, p3, p4, p5, p6, p7));
        messages = new ArrayList<>(Arrays.asList(m1, m2));
        witnessToCC = new ArrayList<>(Arrays.asList("gaetan.zwick@heig-vd.ch", "marco.maziero@heig-vd.ch"));
        numberOfGroups = 2;

        PrankGenerator generator = new PrankGenerator(config);
        List<Prank> pranks = new LinkedList<>();
        try {
            pranks = generator.generatePranks();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 1 prank per group
        Assertions.assertEquals(pranks.size(), numberOfGroups);

        List<String> victimsEmailExpexted = new ArrayList<>(Arrays.asList(p1.getAddress(), p2.getAddress(), p3.getAddress(), p4.getAddress(), p5.getAddress(), p6.getAddress(), p7.getAddress()));
        List<String> victimsEmailActual = new ArrayList<>();

        for (Prank prank : pranks) {
            Mail m = prank.generateMail();
            victimsEmailActual.add(m.getFrom());
            victimsEmailActual.addAll(m.getTo());

            // checks if the subject and content are from one of the messages
            Assertions.assertTrue(
                    (m1.getContent().equals(m.getContent()) && m1.getSubject().equals(m.getSubject())) ||
                            (m2.getContent().equals(m.getContent())) && m2.getSubject().equals(m.getSubject())
            );

        }

        // everyone is present in the pranks
        Assertions.assertTrue(
                victimsEmailActual.size() == victimsEmailExpexted.size() &&
                        victimsEmailActual.containsAll(victimsEmailExpexted) &&
                        victimsEmailExpexted.containsAll(victimsEmailActual)
        );

        // all witnesses are present
        List<String> witnessToCCActual = pranks.get(0).generateMail().getCCs();
        Assertions.assertTrue(
                witnessToCCActual.size() == witnessToCC.size() &&
                        witnessToCCActual.containsAll(witnessToCC) &&
                        witnessToCC.containsAll(witnessToCCActual)
        );
    }

    @Test
    public void weShouldntBeAbleToGenerateAListOfPranksForZeroGroup() {
        victims = new ArrayList<>(Arrays.asList(p1, p2, p3, p4, p5, p6, p7));
        messages = new ArrayList<>(Arrays.asList(m1, m2));
        witnessToCC = new ArrayList<>(Arrays.asList("gaetan.zwick@heig-vd.ch", "marco.maziero@heig-vd.ch"));
        numberOfGroups = 0;

        Assertions.assertThrows(IllegalArgumentException.class, () -> new PrankGenerator(config).generatePranks());
    }

    @Test
    public void weShouldntBeAbleToGenerateAListOfPranksWithLessThan3Victims() {
        victims = new ArrayList<>();
        messages = new ArrayList<>(Arrays.asList(m1, m2));
        witnessToCC = new ArrayList<>(Arrays.asList("gaetan.zwick@heig-vd.ch", "marco.maziero@heig-vd.ch"));
        numberOfGroups = 2;

        // can't have 0 victim
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PrankGenerator(config).generatePranks());

        // can't have less than 3 victims
        victims = new ArrayList<>(Arrays.asList(p1, p2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PrankGenerator(config).generatePranks());
    }

    @Test
    public void weShouldBeAbleToGenerateAListOfPranksWithANewNumberOfGroupIfThereIsNotEnoughVictimsToHaveThreeVictimsPerGroup() {
        victims = new ArrayList<>(Arrays.asList(p1, p2, p3, p4, p5, p6, p7));
        messages = new ArrayList<>(Arrays.asList(m1, m2));
        witnessToCC = new ArrayList<>(Arrays.asList("gaetan.zwick@heig-vd.ch", "marco.maziero@heig-vd.ch"));
        numberOfGroups = 3;

        PrankGenerator generator = new PrankGenerator(config);

        List<Prank> pranks = new LinkedList<>();
        try {
            pranks = generator.generatePranks();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 1 prank per group
        // should not be 3 groups anymore
        Assertions.assertNotEquals(pranks.size(), numberOfGroups);
        // should have 2 groups
        Assertions.assertEquals(pranks.size(), 2);
    }

}
