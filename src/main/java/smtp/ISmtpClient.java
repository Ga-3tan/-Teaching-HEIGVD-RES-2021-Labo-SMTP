package smtp;

import model.mail.Mail;

import java.io.IOException;

public interface ISmtpClient {
    void sendMail(Mail mail) throws IOException;
}