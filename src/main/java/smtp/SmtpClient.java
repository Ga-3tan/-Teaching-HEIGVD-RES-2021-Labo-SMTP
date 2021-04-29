package smtp;

import model.mail.Mail;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmtpClient implements ISmtpClient {
    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private static final String CRLF = "\n\r";
    private final int port;
    private final String address;
    private Socket socket = null;

    public SmtpClient(String serverAddress, int serverPort) {
        // sets the variables
        address = serverAddress;
        port = serverPort;
    }

    public void connect() {
        if (socket != null) return;

        // Creates the socket
        try {
            socket = new Socket(InetAddress.getByName(address), port);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
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
    public void sendMail(Mail mail) {
        // Checks if the socket is still open
        if (socket == null || socket.isClosed()) {
            LOG.log(Level.SEVERE, "Cannot send mail, socket is closed");
            return;
        }

        String readLine;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            // Creates the streams
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            // Initiates SMTP talk
            reader.readLine();
            writer.write("EHLO" + CRLF);
            readLine = reader.readLine();

            // Checks if server is ready to talk
            if (!readLine.split(" ")[0].equals("251")) { // not OK response
                LOG.log(Level.SEVERE, "SMTP error : " + readLine);
                return;
            }
            System.out.println("SMTP SERVER OK : " + readLine);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

    }
}
