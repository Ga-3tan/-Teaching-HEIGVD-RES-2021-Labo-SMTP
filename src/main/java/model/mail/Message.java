package model.mail;

import lombok.Getter;

@Getter
public class Message {
    private final String subject;
    private final String message;

    public Message(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }
}