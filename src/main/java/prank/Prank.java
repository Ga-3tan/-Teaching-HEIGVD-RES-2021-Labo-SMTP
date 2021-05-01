package prank;

import smtp.Mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a prank that can be converted
 * into an email for the smtpClient
 *
 * Name : Prank
 * File : Prank.java
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 01.05.2021
 */
public class Prank {
    private Person sender;
    private List<Person> victims;
    private List<String> witnesses;
    private Message message;

    /**
     * Constructs the prank
     * @param sender The sender
     * @param victims The persons receiving the prank
     * @param witnesses The persons notified by the prank
     * @param prankMessage The prank content
     */
    public Prank(Person sender, List<Person> victims, List<String> witnesses, Message prankMessage) {
        this.sender = sender;
        this.victims = victims;
        this.witnesses = witnesses;
        this.message = prankMessage;
    }

    /**
     * Generates a mail from the prank to sent it through the smtp client
     * @return The created mail
     */
    public Mail generateMail() {
        String from = sender.getAddress();
        List<String> to = new ArrayList<>();
        for (Person p : victims) to.add(p.getAddress());
        return new Mail(from, to, witnesses, message.getSubject(), message.getContent());
    }
}
