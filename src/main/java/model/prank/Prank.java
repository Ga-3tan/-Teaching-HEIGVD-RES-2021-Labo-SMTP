package model.prank;

import model.mail.Mail;
import model.mail.Person;

public class Prank {
    private Person sender;
    private Group victims;
    private Group witnesses;
    private String content;

    public Prank(Person sender, Group victims, Group witnesses, String prankMessage) {
        this.sender = sender;
        this.victims = victims;
        this.witnesses = witnesses;
        this.content = prankMessage;
    }

    public Mail generateMail() {
        // TODO
    }
}
