package smtp;

import java.io.IOException;

/**
 * Represents a generic smtp client
 *
 * Name : ISmtpClient
 * File : ISmtpClient.java
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 01.05.2021
 */
public interface ISmtpClient {
    /**
     * Sends an email through the client
     * @param mail The mail to send
     * @throws IOException If the mail could not be sent
     * @throws RuntimeException If the smtp server responded with an unexpected smtp code
     */
    void sendMail(Mail mail) throws IOException, RuntimeException;
}