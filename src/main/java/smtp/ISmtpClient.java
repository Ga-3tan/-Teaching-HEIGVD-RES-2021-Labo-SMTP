package smtp;

import model.mail.Mail;

public interface ISmtpClient {
    void sendMail(Mail mail);
}
