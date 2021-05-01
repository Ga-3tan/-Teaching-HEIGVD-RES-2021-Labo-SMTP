package smtp;

import model.mail.Mail;
import model.mail.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SmtpClientTest {
    static private PrintStream original = System.err;
    static private Mail emptyMail = new Mail("", new LinkedList<>(), new LinkedList<>(), "", "");

    @Test
    public void smtpClientWithWrongServerAddessShouldThrow() {
        ISmtpClient client = new SmtpClient("555.444.333.222", 25);
        Assertions.assertThrows(IOException.class, () -> {
            client.sendMail(emptyMail);
        });
    }

    @Test
    public void smtpClientWithWrongServerPortShouldThrow() {
        ISmtpClient client = new SmtpClient("localhost", 42);
        Assertions.assertThrows(IOException.class, () -> {
            client.sendMail(emptyMail);
        });
    }

    @Test
    public void smtpClientWithTlsConnexionShouldThrow() {
        ISmtpClient client = new SmtpClient("smtp.gmail.com", 587);
        Assertions.assertThrows(RuntimeException.class, () -> {
            client.sendMail(emptyMail);
        });
    }

    @Test
    public void smtpCliendShouldSendAWellFormedMail() {
        String sender = "john@doe.com";
        List<String> to = new ArrayList<>();
        to.add("jane@doe.com");
        List<String> cc = new ArrayList<>();
        Message m = new Message("Subject", "Content");
        Mail mail = new Mail(sender, to, cc, m.getSubject(), m.getContent());

        ISmtpClient client = new SmtpClient("localhost", 25);

        // Watches the console output
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        PrintStream sysOut = System.out;
        System.setOut(ps);

        // Sends the mail
        try {
            client.sendMail(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertEquals("Connecting to the SMTP server localhost:25" + System.lineSeparator() +
                "Sending mail: Subject" + System.lineSeparator() +
                "Mail sent successfully !" + System.lineSeparator() +
                "Connexion with SMTP server has ended" + System.lineSeparator(), os.toString());

        // Resets the system output
        System.setOut(sysOut);
    }
}