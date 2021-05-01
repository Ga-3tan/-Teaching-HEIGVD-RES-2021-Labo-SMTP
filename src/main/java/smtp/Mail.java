package smtp;

import lombok.Getter;
import java.util.List;

/**
 * Represents a mail. It only uses primitive types so this structure
 * can be reused in any project without any dependencies
 *
 * Name : Mail
 * File : Mail.java
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 01.05.2021
 */
@Getter
public class Mail {
    private final String from;
    private final List<String> to;
    private final List<String> CCs;
    private final String subject;
    private final String content;

    /**
     * Constructs a new mail
     * @param sender The sender
     * @param recipients The addresses receiving the mail
     * @param CCs The persons notified by the mail
     * @param subject The mail subject
     * @param content The mail content
     */
    public Mail(String sender, List<String> recipients, List<String> CCs, String subject, String content) {
        this.from = sender;
        this.to = recipients;
        this.CCs = CCs;
        this.subject = subject;
        this.content = content;
    }
}