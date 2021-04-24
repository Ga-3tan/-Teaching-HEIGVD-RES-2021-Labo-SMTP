package model.prank;

import model.mail.Mail;
import model.mail.Message;
import model.mail.Person;

import java.util.ArrayList;
import java.util.List;

public class Prank {
    private Person sender;
    private List<Person> victims;
    private List<String> witnesses;
    private Message content;

    public Prank(Person sender, List<Person> victims, List<String> witnesses, Message prankMessage) {
        this.sender = sender;
        this.victims = victims;
        this.witnesses = witnesses;
        this.content = prankMessage;
    }

    public Mail generateMail() {
        String from = sender.getAddress();
        List<String> to = new ArrayList<>();
        for (Person p : victims) to.add(p.getAddress());
        return new Mail(from, to, witnesses, content);
    }
}
