package model.prank;

import config.IConfigurationManager;
import model.mail.Message;
import model.mail.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrankGeneratorTest {

    Person p1 = new Person("Zwock", "Getan", "gaetan.imposter@jaimail.com");
    Person p2 = new Person("Moziero", "Mörkö", "marco.imposter@mailmail.com");
    Person p3 = new Person("E", "E", "ee@eemail.yeet");
    Person p4 = new Person("Binks", "JarJar", "exiled@gungan.oof");
    Person pranker = new Person("Pranker", "The", "the.pranker@prank.com");

    Message m1 = new Message("totally not a prank", "Q: What do you call a busy waiter?\n\nA: A server.");
    Message m2 = new Message("important", "REDS is an evil secret organisation whose goal is to control the world");


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
            return 2;
        }

        @Override
        public List<Person> getVictims() {
            return null;
        }

        @Override
        public List<Message> getMessages() {
            return new ArrayList<>(Arrays.asList(m1, m2));
        }

        @Override
        public List<String> getWitnessesToCC() {
            return new ArrayList<>(Arrays.asList("gaetan.zwick@heig-vd.ch","marco.maziero@heig-vd.ch"));
        }
    };

    PrankGenerator generator = new PrankGenerator(config);

}
