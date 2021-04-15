package model.mail;

import lombok.Getter;

@Getter
public class Mail {
    Person sender;
    Person recipient;
    Group CCs;
    Message message;

    public Mail(Person sender, Person recipient, Group CCs, Message message) {
        this.sender = sender;
        this.recipient = recipient;
        this.CCs = CCs;
        this.message = message;
    }
}
