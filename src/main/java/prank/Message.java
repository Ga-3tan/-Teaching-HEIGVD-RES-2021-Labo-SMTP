package prank;

import lombok.Getter;

/**
 * Represents a message (with a subject and content)
 *
 * Name : Message
 * File : Message.java
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 01.05.2021
 */
@Getter
public class Message {
    private final String subject;
    private final String content;

    /**
     * Constructs a new message
     * @param subject The subject
     * @param message The content
     */
    public Message(String subject, String message) {
        this.subject = subject;
        this.content = message;
    }
}