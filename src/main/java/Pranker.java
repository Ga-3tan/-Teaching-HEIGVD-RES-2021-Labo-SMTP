import config.ConfigurationManager;
import config.IConfigurationManager;
import prank.Prank;
import prank.PrankGenerator;
import smtp.ISmtpClient;
import smtp.SmtpClient;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main programm class
 * Generates the pranks and sends them though the smtp client
 *
 * Name : Pranker
 * File : Pranker.java
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 01.05.2021
 */
public class Pranker {
    private static final Logger LOG = Logger.getLogger(Pranker.class.getName());
    private ISmtpClient smtpClient;

    /**
     * Sends the generated pranks through the smtp client
     */
    public Pranker() {
        // Parses the config files and opens
        List<Prank> pranks = new LinkedList<>();
        try {
            IConfigurationManager configManager = new ConfigurationManager();
            PrankGenerator prankGenerator = new PrankGenerator(configManager);
            pranks = prankGenerator.generatePranks();
            smtpClient = new SmtpClient(configManager.getSmtpServerAddress(), configManager.getSmtpServerPort());
        } catch (IOException e) {
            System.err.println("Error when parsing config data, there should be a folder " +
                    "\"configuration\" with the following files inside : \"config.properties\", " +
                    "\"emails.utf8\", \"messages.utf8\":\n\t" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Configuration data contains wrong values, please check the files\n\t" + e.getMessage());
        }

        for (Prank p : pranks) {
            try {
                smtpClient.sendMail(p.generateMail());
            } catch (IOException e) {
                System.err.println("Mail was not sent due to an IO exception:\n\t" + e.getMessage());
            } catch (RuntimeException e) {
                System.err.println("Mail was not sent due to a SMTP unexpected response:\n\t" + e.getMessage());
            }
        }

    }

    /**
     * Entry point of the program
     * @param args The program arguments
     */
    public static void main(String[] args) {
        new Pranker();
    }
}
