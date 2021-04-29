package smtp;

import model.mail.Mail;

public interface ISmtpClient {
    void connect();
    void close();
    void sendMail(Mail mail);
}
