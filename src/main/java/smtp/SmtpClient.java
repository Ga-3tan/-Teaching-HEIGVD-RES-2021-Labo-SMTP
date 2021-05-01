package smtp;

import model.mail.Mail;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmtpClient implements ISmtpClient {
    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private static final String CRLF = "\r\n";
    private final int port;
    private final String address;
    private SSLSocket socket = null;

    public SmtpClient(String serverAddress, int serverPort) {
        // sets the variables
        address = serverAddress;
        port = serverPort;
    }

    public void close() {
        // Closes the socket
        try {
            socket.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void sendMail(Mail mail) throws IOException, RuntimeException {

        // Creates the socket and connects to the smtp server
        socket = new Socket(InetAddress.getByName(address), port);

        // Sends the mail
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            // Creates the streams
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            // Server greetings
            readServer(reader, 220);

            // Initiates SMTP talk
            writer.write("EHLO res-lab.com" + CRLF);
            writer.flush();
            readServer(reader, 250);

            // Gives the sender (MAIL FROM)
            writer.write("MAIL FROM: <" + mail.getFrom() + ">" + CRLF);
            writer.flush();
            readServer(reader, 250);

            // Gives the recipients
            List<String> rcpts = new LinkedList<>();
            rcpts.addAll(mail.getTo());
            rcpts.addAll(mail.getCCs());
            for (String rcpt : rcpts) {
                writer.write("RCPT TO: <" + rcpt + ">" + CRLF);
                writer.flush();
                readServer(reader, 250);
            }

            // Starts the data section
            writer.write("DATA" + CRLF);
            writer.flush();
            readServer(reader, 354);

            // Specifies the charset and content type
            writer.write("Content-Type: text/plain; charset=utf-8" + CRLF);
            writer.flush();

            // Specifies the sender
            writer.write("from: " + mail.getFrom() + CRLF);
            writer.flush();

            // Specifies the rcpts
            for (String to : mail.getTo()) {
                writer.write("to: " + to + CRLF);
                writer.flush();
            }

            /*/ Writes the CC mails
            for (String cc : mail.getCCs()) {
                writer.write("cc: " + cc + CRLF);
                writer.flush();
            }*/

            // Writes the subject
            writer.write("Subject: " + mail.getSubject() + CRLF + CRLF);
            writer.flush();

            // Writes the content
            writer.write(mail.getContent() + CRLF);
            writer.flush();

            // Ends content write
            writer.write("." + CRLF);
            writer.flush();
            readServer(reader, 250);

            // Ends chat with server
            writer.write("QUIT" + CRLF);
            writer.flush();
            readServer(reader, 221);

            // Closes all
            reader.close();
            writer.close();
            socket.close();

        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    private void readServer(BufferedReader reader, int expectedSmtpCode) throws IOException, RuntimeException {
        String line = reader.readLine();
        System.out.println(line);
        while (line != null && line.charAt(3) != ' ') {
            System.out.println(line);
            line = reader.readLine();
        }

        // Checks response code
        if (line == null || Integer.parseInt(line.substring(0, 3)) != expectedSmtpCode)
            throw new RuntimeException(line);
    }
}
